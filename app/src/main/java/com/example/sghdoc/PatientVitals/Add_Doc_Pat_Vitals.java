package com.example.sghdoc.PatientVitals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sghdoc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Add_Doc_Pat_Vitals extends AppCompatActivity {
    final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    final Date date= new Date();
    final String date1=simpleDateFormat.format(date);
    private EditText bloodpressure,bloodtemperature,heartrate,respirationrate;
    private Button Submit;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__doc__pat__vitals);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String username=bundle.getString("Name");
        final String userkey =bundle.getString("Key");
        final String userid =bundle.getString("ID");

        bloodpressure = (EditText) findViewById(R.id.vitaltext1);
        bloodtemperature = (EditText) findViewById(R.id.vitaltext2);
        heartrate = (EditText) findViewById(R.id.vitaltext3);
        respirationrate = (EditText) findViewById(R.id.vitaltext4);

        Submit = (Button) findViewById(R.id.vital_submit);
        loadingBar=new ProgressDialog(this);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitVitals();
            }

            private void SubmitVitals()
            {
                String bloodpressure1 = bloodpressure.getText().toString();
                String bloodtemperature1 = bloodtemperature.getText().toString();
                String heartrate1 = heartrate.getText().toString();
                String respirationrate1 = respirationrate.getText().toString();

                if(TextUtils.isEmpty(bloodpressure1))
                {
                    Toast.makeText(Add_Doc_Pat_Vitals.this,"Please Enter Your Blood Pressure",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(bloodtemperature1))
                {
                    Toast.makeText(Add_Doc_Pat_Vitals.this,"Please Enter Your Body Temperature",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(heartrate1))
                {
                    Toast.makeText(Add_Doc_Pat_Vitals.this,"Please Enter Your Heart Rate",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(respirationrate1))
                {
                    Toast.makeText(Add_Doc_Pat_Vitals.this,"Please Enter Your Respiration Rate",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loadingBar.setTitle("Submit");
                    loadingBar.setMessage("Please wait,while we are Submitting");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();


                    ValidateVitals(bloodpressure1,bloodtemperature1,heartrate1,respirationrate1);
                }
            }

            private void ValidateVitals(final String bloodpressure,final String bloodtemperature,final String heartrate,final String respirationrate)
            {
                final DatabaseReference rootref;
                rootref= FirebaseDatabase.getInstance().getReference().child("Users");
                final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                final Date date=new Date();
                Intent intent=getIntent();
                Bundle bundle=intent.getExtras();
                final String username=bundle.getString("Name");
                final String userkey =bundle.getString("Key");
                final String userid =bundle.getString("ID");

                rootref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        rootref.child(userkey).child("Vitals").child(date1).setValue(new UserVitals(bloodpressure,bloodtemperature,heartrate,respirationrate,date1))
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(Add_Doc_Pat_Vitals.this, "Submitted", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();
                                            Bundle bundle=new Bundle();
                                            bundle.putString("Name",username);
                                            bundle.putString("ID",userid);
                                            bundle.putString("Key",userkey);
                                            Intent intent=new Intent(Add_Doc_Pat_Vitals.this, Doc_Pat_Vitals.class);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        } else {
                                            loadingBar.dismiss();
                                            Toast.makeText(Add_Doc_Pat_Vitals .this, "Error try Again", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

}
