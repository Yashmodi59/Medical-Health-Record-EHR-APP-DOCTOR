package com.example.sghdoc.PatientPrescription;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sghdoc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Add_Doc_Pat_Activity extends AppCompatActivity {
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    final Date date = new Date();
    private TextView dobe;
    private DatePickerDialog.OnDateSetListener dobselector;
    private Uri uri;
    final String date1 = simpleDateFormat.format(date);
    private EditText doctorname, remarks,report;
    private Button submit, pdf;
    private Spinner severityspinner;
    private ProgressDialog loadingBar;
    private DatabaseReference rootref;
    private FirebaseAuth mAuth;

    private StorageReference storage;
    private String uurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__doc__pat_);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String username=bundle.getString("Name");
        final String userkey =bundle.getString("Key");
        final String userid =bundle.getString("ID");
        doctorname = (EditText) findViewById(R.id.presc_doctor_name);
        remarks = (EditText) findViewById(R.id.presc_remarks);
        submit = (Button) findViewById(R.id.presc_Submit);
        pdf = (Button) findViewById(R.id.presc_btn);
        dobe = (TextView) findViewById(R.id.presc_next_app);
        severityspinner = (Spinner) findViewById(R.id.presc_spinner);
        report=(EditText)findViewById(R.id.presc_report);
        storage = FirebaseStorage.getInstance().getReference();
        rootref = FirebaseDatabase.getInstance().getReference("Prescriptions");

        mAuth = FirebaseAuth.getInstance();
        doctorname.setText(mAuth.getCurrentUser().getEmail());

        String[] sev = getResources().getStringArray(R.array.severity_arrays);

        ArrayAdapter<String> adapter1;
        adapter1 = new ArrayAdapter<String>(Add_Doc_Pat_Activity.this, android.R.layout.simple_spinner_dropdown_item, sev);
        severityspinner.setAdapter(adapter1);
        dobe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int date = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Add_Doc_Pat_Activity.this,
                        android.R.style.Theme_Material_Dialog_NoActionBar, dobselector, year, month, date);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLUE));
                datePickerDialog.show();


            }
        });

        dobselector = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                dobe.setText(date);
            }
        };

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select();
            }

            private void select() {

                Intent intent = new Intent();
                intent.setType("application/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(intent,"select Document "),234);

            }
        });




        loadingBar = new ProgressDialog(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SubmitPresc();

            }
        });
    }


    private void SubmitPresc() {
        String doctor = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String remark = remarks.getText().toString();
        String appointment = dobe.getText().toString();
        String severity = severityspinner.getSelectedItem().toString();
        String rep=report.getText().toString();


        if (TextUtils.isEmpty(doctor)) {
            Toast.makeText(Add_Doc_Pat_Activity.this, "Please Enter Your DoctorName", Toast.LENGTH_SHORT).show();
        }
//        else if(TextUtils.isEmpty(remark))
//        {
//            Toast.makeText(PrescriptionActivity.this,"Please Enter Your Rmarks",Toast.LENGTH_SHORT).show();
//        }
        else if (TextUtils.isEmpty(remark)) {
            Toast.makeText(Add_Doc_Pat_Activity.this, "Please Enter Remark", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Submit");
            loadingBar.setMessage("Please wait,while we are Submitting");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            ValidatePresc(doctor, remark, appointment, severity,rep);


        }
    }

    private void ValidatePresc(final String doctor, final String remark, final String appointment, final String severity,final String report) {
        final DatabaseReference rootref;
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String username=bundle.getString("Name");
        final String userkey =bundle.getString("Key");
        final String userid =bundle.getString("ID");

        rootref = FirebaseDatabase.getInstance().getReference().child("Users").child(userkey).child("Prescription");
        Upload();
        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rootref.child(date1).setValue(new UserPrescription(doctor, remark, appointment, severity, date1,uurl,report))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Add_Doc_Pat_Activity.this, "Submitted", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                    Bundle bundle=new Bundle();
                                    bundle.putString("Name",username);
                                    bundle.putString("ID",userid);
                                    bundle.putString("Key",userkey);
                                    Intent intent=new Intent(Add_Doc_Pat_Activity.this, Doc_Pat_Presc.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                } else {
                                    loadingBar.dismiss();
                                    Toast.makeText(Add_Doc_Pat_Activity.this, "Error try Again", Toast.LENGTH_SHORT).show();
                                }

                            }

                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Upload() {
        final DatabaseReference rootref;
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String username=bundle.getString("Name");
        final String userkey =bundle.getString("Key");
        final String userid =bundle.getString("ID");

        rootref = FirebaseDatabase.getInstance().getReference().child("Users").child(userkey).child("Prescription");
        if (uri != null )
        {
            final  ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("uploading..");
            progressDialog.show();
            StorageReference riversRef = storage.child("Prescription").child(userkey).child(date1).child(doctorname.getText().toString());

            riversRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
//                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                            while (!uri.isComplete());
                            Uri url=uri.getResult();
                            uurl=url.toString();
                            rootref.child(date1).setValue(new UserPrescription(uurl));
                            progressDialog.dismiss();
                            Toast.makeText(Add_Doc_Pat_Activity.this,"file uploaded ",Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            progressDialog.dismiss();
                            Toast.makeText(Add_Doc_Pat_Activity.this,exception.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress  = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("uploaded : "+(int)progress+ " % ");
                        }
                    });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 234 && resultCode == RESULT_OK && data!=null  && data.getData()!=null)
        {
            uri = data.getData();
//            Log.i("DATA : " , data.getData().toString()) ;
//            (data.getData());

        }
    }

}
