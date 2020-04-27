package com.example.sghdoc.Chemist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sghdoc.PatientProfile.LastLog;
import com.example.sghdoc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChemLabDashBoard extends AppCompatActivity {
    private TextView textViewUsername;
    private EditText textViewPassword, Ed, Ed1, Ed2;
    private Button button, button1;
    private StorageReference UserProfileRefer;
    DatabaseReference RootRef;
    DatabaseReference mRootRef;
    private ProgressDialog loadingBar;
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    final Date date = new Date();
    final String logdate = simpleDateFormat.format(date);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chem_lab_dash_board);
        textViewUsername = (TextView) findViewById(R.id.textview_username);
        textViewPassword = (EditText) findViewById(R.id.textview_password);
        Ed = (EditText) findViewById(R.id.ed);
        Ed1 = (EditText) findViewById(R.id.ed1);
        Ed2 = (EditText) findViewById(R.id.ed2);
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button2);
        button1.setVisibility(View.GONE);
        Ed.setVisibility(View.GONE);
        RootRef = FirebaseDatabase.getInstance().getReference("UniqueId");
        loadingBar = new ProgressDialog(this);
        final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.setTitle("Submit");
                loadingBar.setMessage("Please wait,while we are Submitting");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                scan();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRootRef = FirebaseDatabase.getInstance().getReference("Users").child(Ed.getText().toString()).child("LastLogin");
                mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mRootRef.child(logdate).setValue(new LastLog(email, logdate)).
                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ChemLabDashBoard.this, "Submitted", Toast.LENGTH_SHORT).show();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("Name", Ed1.getText().toString());
                                            bundle.putString("ID", textViewPassword.getText().toString());
                                            bundle.putString("Key", Ed.getText().toString());
                                            Intent intent = new Intent(ChemLabDashBoard.this, ChemPatDashboard.class);
                                            intent.putExtras(bundle);
                                            startActivity(intent);

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

    private void scan() {
        final String id;
        id=textViewPassword.getText().toString();
        RootRef = FirebaseDatabase.getInstance().getReference("UniqueId");
        mRootRef=FirebaseDatabase.getInstance().getReference("Users");
        RootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && (dataSnapshot.hasChild(id))) {
                    RootRef.child(id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists() && (dataSnapshot.hasChild("ID"))) {
                                String name = dataSnapshot.child("ID").getValue().toString();
                                Ed.setText(name);
                                mRootRef.child(Ed.getText().toString()).child("Profile").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists() && dataSnapshot.hasChild("name")) {
                                            String n = dataSnapshot.child("name").getValue().toString();
                                            String n1 = dataSnapshot.child("email").getValue().toString();
                                            Ed1.setText(n);
                                            Ed2.setText(n1);
                                            loadingBar.dismiss();
                                            button1.setVisibility(View.VISIBLE);
                                        }
                                        loadingBar.dismiss();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            } else {
                                loadingBar.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    loadingBar.dismiss();
                    textViewPassword.setError("Please Check Id");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
