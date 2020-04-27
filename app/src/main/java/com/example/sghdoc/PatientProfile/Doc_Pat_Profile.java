package com.example.sghdoc.PatientProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sghdoc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Doc_Pat_Profile extends AppCompatActivity {
    private EditText InputName;
    private Button CreateAccountButton;
    private EditText InputPhoneNumber;
    private ImageView InputProfile;
    private EditText InputPassword;
    private EditText InputMiddleName;
    private EditText InputLastName;
    private EditText InputEmail;
    private ProgressDialog loadingBar;
    private RadioGroup radioSexGroup, radioAbledGroup;
    private Spinner bgspinner;
    private FirebaseAuth mAuth;
    private static final int Gp=1;
    private TextView dob, PinCode;
    private DatePickerDialog.OnDateSetListener dobselector;
    private String key;
    private StorageReference UserProfileRefer;
    DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc__pat__profile);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String username=bundle.getString("Name");
        final String userkey =bundle.getString("Key");
        final String userid =bundle.getString("ID");
        InputProfile=(CircleImageView)findViewById(R.id.mainImage);
        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        InputMiddleName = (EditText) findViewById(R.id.register_middlename_input);
        InputLastName = (EditText) findViewById(R.id.register_lastname_input);
        InputEmail = (EditText) findViewById(R.id.register_email_input);
        radioSexGroup = (RadioGroup) findViewById(R.id.register_radiogrp1);
        radioAbledGroup = (RadioGroup) findViewById(R.id.register_radiogrp2);
        dob = (EditText) findViewById(R.id.register_dob_input);
        PinCode = (EditText) findViewById(R.id.register_PINCODE_input);
        bgspinner = (Spinner) findViewById(R.id.register_blood_group);
        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.child("Users").child(userkey).child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && (dataSnapshot.hasChild("name")) && (dataSnapshot.hasChild("middlename"))
                        && (dataSnapshot.hasChild("lastname")) && (dataSnapshot.hasChild("email"))
                        && (dataSnapshot.hasChild("pincode") && (dataSnapshot.hasChild("gender"))
                        && (dataSnapshot.hasChild("abled")) && (dataSnapshot.hasChild("bloodgroup")))) {
                    String retrivedate = dataSnapshot.child("date").getValue().toString();
                   String retrieveImage=dataSnapshot.child("I").child("downloadUrl").getValue().toString();
                    String retriveName = dataSnapshot.child("name").getValue().toString();
                    String retriveMiddleName = dataSnapshot.child("middlename").getValue().toString();
                    String red = dataSnapshot.child("date").getValue().toString();
                    String retriveLastName = dataSnapshot.child("lastname").getValue().toString();
                    String retriveEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    String retrivepincode = dataSnapshot.child("pincode").getValue().toString();
                    String retriveGender = dataSnapshot.child("gender").getValue().toString();
                    String retriveAbled = dataSnapshot.child("abled").getValue().toString();
                    String retriveBloodgroup = dataSnapshot.child("bloodgroup").getValue().toString();
                    String retrivephone = dataSnapshot.child("phone").getValue().toString();
//                    Glide.with(getApplicationContext()).load(retrieveImage).into(InputProfile);
                   Picasso.get().load(retrieveImage).into(InputProfile);
                    InputName.setText(retriveName);
                    InputEmail.setText(retriveEmail);

                    InputLastName.setText(retriveLastName);
                    InputMiddleName.setText(retriveMiddleName);
                    PinCode.setText(retrivepincode);
                    dob.setText(red);
                    InputPhoneNumber.setText(retrivephone);

//                    bgspinner.setPrompt(retriveBloodgroup);
                    if(retriveAbled.equals("Yes")) {
                        radioAbledGroup.check(R.id.register_Yes);
                    }
                    else {
                        radioAbledGroup.check(R.id.register_No);
                    }
                    if(retriveGender.equals("Male")) {
                        radioSexGroup.check(R.id.register_male);
                    }
                    else {
                        radioSexGroup.check(R.id.register_female);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
