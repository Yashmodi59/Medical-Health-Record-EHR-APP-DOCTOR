package com.example.sghdoc.PatientMedicalHistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sghdoc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Add_Doc_Pat_MH extends AppCompatActivity {
    private EditText History,DocName;
    private RadioGroup radiogroup1,radiogroup2,radiogroup3;
    private ProgressDialog loadingBar;
    private Button SubmitHistory;
    private Spinner typespinner,severityspinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__doc__pat__mh);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String username=bundle.getString("Name");
        final String userkey =bundle.getString("Key");
        final String userid =bundle.getString("ID");
        DocName = (EditText) findViewById(R.id.medical_history_Doc_Name2) ;
        History =(EditText) findViewById(R.id.medical_history_text2) ;
        radiogroup1 = (RadioGroup) findViewById(R.id.medical_history_radiogroup1);
        radiogroup2 = (RadioGroup) findViewById(R.id.medical_history_radiogroup2);
        radiogroup3 = (RadioGroup) findViewById(R.id.medical_history_radiogroup3);
        typespinner = (Spinner) findViewById(R.id.medical_history_spinner1);
        severityspinner = (Spinner) findViewById(R.id.medical_history_spinner2);

        String []medhis = getResources().getStringArray(R.array.Medical_History);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(Add_Doc_Pat_MH.this, android.R.layout.simple_spinner_dropdown_item,medhis);
        typespinner.setAdapter(adapter);
        DocName.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        String []sev =getResources().getStringArray(R.array.severity_arrays);

        ArrayAdapter<String> adapter1;
        adapter1 = new ArrayAdapter<String>(Add_Doc_Pat_MH.this, android.R.layout.simple_spinner_dropdown_item,sev);
        severityspinner.setAdapter(adapter1);


        loadingBar = new ProgressDialog(this);
        SubmitHistory=(Button) findViewById(R.id.medical_history_submit);
        loadingBar=new ProgressDialog(this);
        SubmitHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("Name",username);
                bundle.putString("ID",userid);
                bundle.putString("Key",userkey);
                Intent intent = new Intent(Add_Doc_Pat_MH.this, Doc_Pat_MH.class);
                intent.putExtras(bundle);
                startActivity(intent);
                SubmitMedHistory();

            }

            private void SubmitMedHistory() {

                String history = History.getText().toString();
                String docname = FirebaseAuth.getInstance().getCurrentUser().getEmail();


                int selectedId,selectedId1,selectedId2;
                radiogroup1 = (RadioGroup) findViewById(R.id.medical_history_radiogroup1);
                selectedId= radiogroup1.getCheckedRadioButtonId();
                final RadioButton radioButton = (RadioButton)findViewById(selectedId);

                if (radioButton.getText() == null) {
                    return;
                }

                String yesorno =radioButton.getText().toString();
                String StillHave="Still Have Disease:- "+yesorno;
                radiogroup2 = (RadioGroup) findViewById(R.id.medical_history_radiogroup2);
                selectedId1= radiogroup2.getCheckedRadioButtonId();
                final RadioButton radioButton1 = (RadioButton)findViewById(selectedId1);

                if (radioButton.getText() == null) {
                    return;
                }

                String yesorno1 =radioButton.getText().toString();
                String HaveAny  ="Have Any Report:- "+yesorno1;
                radiogroup3 = (RadioGroup) findViewById(R.id.medical_history_radiogroup3);
                selectedId2= radiogroup3.getCheckedRadioButtonId();
                final RadioButton radioButton2 = (RadioButton)findViewById(selectedId2);

                if (radioButton.getText() == null) {
                    return;
                }

                String yesorno2 =radioButton.getText().toString();
                String IsUploaded ="Is Uploaded:- "+yesorno2;
                String type = typespinner.getSelectedItem().toString();
                String severity = severityspinner.getSelectedItem().toString();




                if (TextUtils.isEmpty(history)) {
                    Toast.makeText(Add_Doc_Pat_MH.this, "Please Enter Your History", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setTitle("Submit");
                    loadingBar.setMessage("Please wait,while we are Submitting");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();


                    ValidateHistory(history,HaveAny,docname,StillHave,IsUploaded,type,severity);
                }
            }

            private void ValidateHistory(final String history,final String HaveAny,final String docname,final String StillHave,final String IsUploaded
                    ,final String type,final String severity) {
                final DatabaseReference rootref;
                rootref = FirebaseDatabase.getInstance().getReference().child("Users");
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String key = user.getUid();
                rootref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        final Date date= new Date();
                        String date1=simpleDateFormat.format(date);
                        UserMedicalHistory userMedicalHistory = new UserMedicalHistory(history,HaveAny,docname,StillHave,IsUploaded,type,severity,date1);


                        rootref.child(userkey).child("MedicalHistory").child(date1).setValue(userMedicalHistory)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Add_Doc_Pat_MH.this, "Submitted", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();
                                            Bundle bundle=new Bundle();
                                            bundle.putString("Name",username);
                                            bundle.putString("ID",userid);
                                            bundle.putString("Key",userkey);
                                            Intent intent = new Intent(Add_Doc_Pat_MH.this, Doc_Pat_MH.class);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        } else {
                                            loadingBar.dismiss();
                                            Toast.makeText(Add_Doc_Pat_MH.this, "Error try Again", Toast.LENGTH_SHORT).show();
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
