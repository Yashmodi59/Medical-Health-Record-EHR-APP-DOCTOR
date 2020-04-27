package com.example.sghdoc.PatientAllergy;

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

public class Add_Doc_Pat_Allergy extends AppCompatActivity {
    private Button AllergyButton;
    private RadioGroup radioAllergyGroup;
    private String parentDbname="Allergy";
    private Spinner typespinner,severityspinner;
    private EditText Allergy;
    private ProgressDialog loadingBar;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    final String key = user.getUid();
    final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    final Date date= new Date();
    final String date1=simpleDateFormat.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__doc__pat__allergy);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String username=bundle.getString("Name");
        final String userkey =bundle.getString("Key");
        final String userid =bundle.getString("ID");

        Allergy =(EditText) findViewById(R.id.allergy_text);
        radioAllergyGroup=(RadioGroup) findViewById(R.id.allergy_radiogroup);
         DatabaseReference rootref;
        rootref= FirebaseDatabase.getInstance().getReference().child("Users").child(userkey);


        typespinner = (Spinner) findViewById(R.id.spinner1);
        severityspinner = (Spinner) findViewById(R.id.spinner2);

        String []medhis = getResources().getStringArray(R.array.allergy_type_arrays);

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(Add_Doc_Pat_Allergy.this, android.R.layout.simple_spinner_dropdown_item,medhis);
        typespinner.setAdapter(adapter);

        String []sev =getResources().getStringArray(R.array.severity_arrays);

        ArrayAdapter<String> adapter1;
        adapter1 = new ArrayAdapter<String>(Add_Doc_Pat_Allergy.this, android.R.layout.simple_spinner_dropdown_item,sev);
        severityspinner.setAdapter(adapter1);


        AllergyButton=(Button) findViewById(R.id.submit_allergies);
        loadingBar=new ProgressDialog(this);
        AllergyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SubmitAllergy();

            }
        });
    }
    private void addListenerOnButton(){


    }

    private void SubmitAllergy()
    {

//        String phone = InputPhoneNumber.getText().toString();
        String allergy = Allergy.getText().toString();
//        String s1text = s1.getSelectedItem().toString();
//        String s2text = s2.getSelectedItem().toString();

        radioAllergyGroup =(RadioGroup) findViewById(R.id.allergy_radiogroup);
        int selectedId;

        selectedId= radioAllergyGroup.getCheckedRadioButtonId();
        final RadioButton radioButton = (RadioButton)findViewById(selectedId);

        if (radioButton.getText() == null) {
            return;
        }
        String still=radioButton.getText().toString();
        String stillhave="Still Have:-"+still;
        String type = typespinner.getSelectedItem().toString();
        String severity = severityspinner.getSelectedItem().toString();

        if(TextUtils.isEmpty(allergy))
        {
            Toast.makeText(Add_Doc_Pat_Allergy.this,"Please Enter Your Allergy",Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Submit");
            loadingBar.setMessage("Please wait,while we are Submitting");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            Validateallergy(allergy,stillhave,type,severity);


        }
    }

    private void Validateallergy(final String allergy,final String stillhave,final String type,final String severity) {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String username=bundle.getString("Name");
        final String userkey =bundle.getString("Key");
        final String userid =bundle.getString("ID");

        final DatabaseReference rootref;

        rootref= FirebaseDatabase.getInstance().getReference().child("Users").child(userkey);

        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAllergy userAllergy=new UserAllergy(allergy,stillhave,type,severity,date1);
                rootref.child("Allergy").child(date1).setValue(userAllergy)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(Add_Doc_Pat_Allergy.this, "Submitted", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                    Bundle bundle=new Bundle();
                                    bundle.putString("Name",username);
                                    bundle.putString("ID",userid);
                                    bundle.putString("Key",userkey);
                                    Intent intent=new Intent(Add_Doc_Pat_Allergy.this, Doc_Pat_Allergy.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);}
                                else {
                                    loadingBar.dismiss();
                                    Toast.makeText(Add_Doc_Pat_Allergy.this, "Error try Again", Toast.LENGTH_SHORT).show();
                                }

                            }

                        });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
