package com.example.sghdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sghdoc.Chemist.ChemPatDashboard;
import com.example.sghdoc.PatientAllergy.Doc_Pat_Allergy;
import com.example.sghdoc.PatientDocument.Doc_Pat_Medical_Document;
import com.example.sghdoc.PatientMedicalHistory.Doc_Pat_MH;
import com.example.sghdoc.PatientPrescription.Doc_Pat_Presc;
import com.example.sghdoc.PatientProfile.Doc_Pat_Profile;
import com.example.sghdoc.PatientReport.Doc_Pat_Report;
import com.example.sghdoc.PatientVitals.Doc_Pat_Vitals;

public class Doc_Pat_DashBoard extends AppCompatActivity {
    Button home_profile_btn;
    EditText namedash,nameid1,nameid2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc__pat__dash_board);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String username =bundle.getString("Name");
        final String userkey =bundle.getString("Key");
        final String userid =bundle.getString("ID");

        namedash=findViewById(R.id.namedash);
        nameid1=findViewById(R.id.nameID1);
        nameid2=findViewById(R.id.nameID2);
        namedash.setText(username);
        nameid1.setText(userid);
        nameid2.setText(userkey);

        home_profile_btn=(Button)findViewById(R.id.home_profile_btn);
        home_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("Name",username);
                bundle.putString("ID",userid);
                bundle.putString("Key",userkey);
                Intent intent=new Intent(Doc_Pat_DashBoard.this, Doc_Pat_Profile.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
                Button home_medical_doc_btn= findViewById(R.id.home_medical_doc_btn);
        home_medical_doc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("Name",username);
                bundle.putString("ID",userid);
                bundle.putString("Key",userkey);
                Intent intent=new Intent(Doc_Pat_DashBoard.this, Doc_Pat_Medical_Document.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
       Button home_medical_history_btn= findViewById(R.id.home_medical_history_btn);
        home_medical_history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("Name",username);
                bundle.putString("ID",userid);
                bundle.putString("Key",userkey);
                Intent intent=new Intent(Doc_Pat_DashBoard.this, Doc_Pat_MH.class);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });
        Button home_allergy_btn= findViewById(R.id.home_allergy_btn);
        home_allergy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { Bundle bundle=new Bundle();
                bundle.putString("Name",username);
                bundle.putString("ID",userid);
                bundle.putString("Key",userkey);
                Intent intent=new Intent(Doc_Pat_DashBoard.this, Doc_Pat_Allergy.class);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });
        Button home_prescription_btn= findViewById(R.id.home_prescription_btn);
        home_prescription_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("Name",username);
                bundle.putString("ID",userid);
                bundle.putString("Key",userkey);
                Intent intent=new Intent(Doc_Pat_DashBoard.this, Doc_Pat_Presc.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        Button home_lab_reports= findViewById(R.id.home_lab_reports);
        home_lab_reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("Name",username);
                bundle.putString("ID",userid);
                bundle.putString("Key",userkey);
                Intent intent=new Intent(Doc_Pat_DashBoard.this, Doc_Pat_Report.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        Button home_vitals_btn= findViewById(R.id.home_vitals_btn);
        home_vitals_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("Name",username);
                bundle.putString("ID",userid);
                bundle.putString("Key",userkey);
                Intent intent=new Intent(Doc_Pat_DashBoard.this, Doc_Pat_Vitals.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        Button home_medicine= findViewById(R.id.home_medicine_btn);
        home_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("Name",username);
                bundle.putString("ID",userid);
                bundle.putString("Key",userkey);
                Intent intent=new Intent(Doc_Pat_DashBoard.this, Doc_Pat_Medicine.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
