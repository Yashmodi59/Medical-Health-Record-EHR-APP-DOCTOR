package com.example.sghdoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sghdoc.Chemist.ChemLabDashBoard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Doc_Login extends AppCompatActivity implements View.OnClickListener{
    FirebaseAuth mAuth;
    EditText editTextEmail, editTextPassword;
    ProgressBar progressBar;
    DatabaseReference dr,cr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc__login);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        findViewById(R.id.textViewSignup).setOnClickListener(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);

    }

    private void userLogin() {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum lenght of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    dr = FirebaseDatabase.getInstance().getReference().child("Doctor");
                    cr = FirebaseDatabase.getInstance().getReference().child("Chemist");
                    cr.orderByChild("Email").equalTo(editTextEmail.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(Doc_Login.this, ChemLabDashBoard.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    dr.orderByChild("Email").equalTo(editTextEmail.getText().toString())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        progressBar.setVisibility(View.GONE);
                                        Intent intent = new Intent(Doc_Login.this, Doc_Drawer.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);

                                    }
                                    else{
                                                progressBar.setVisibility(View.GONE);
                                            Toast.makeText(Doc_Login.this,"You are not authorized",Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

        });
    }
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.textViewSignup:
                                finish();
                                startActivity(new Intent(this, Doc_Signup.class));
                                break;

                            case R.id.buttonLogin:
                                userLogin();
                                break;
                        }

                    }
}
//                    cr.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            for(DataSnapshot data: dataSnapshot.getChildren()) {
//                                if ((data.child(editTextEmail.getText().toString()).exists())) {
//                                    Intent intent = new Intent(Doc_Login.this, ChemLabDashBoard.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    startActivity(intent);
//                                } else  {
//                                    Toast.makeText(Doc_Login.this,"You are not Authorized"+data.child("Email").getValue().toString(),Toast.LENGTH_LONG).show();
//                                    Intent intent = new Intent(Doc_Login.this, MainActivity.class);
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    startActivity(intent);
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                                    } else {
//                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if (mAuth.getCurrentUser() != null) {
//            finish();
//            startActivity(new Intent(this, Doc_Drawer.class));
//        }
//    }


