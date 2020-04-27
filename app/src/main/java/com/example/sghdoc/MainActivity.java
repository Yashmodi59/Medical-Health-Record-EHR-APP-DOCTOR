package com.example.sghdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private Button joinNowButton,loginButton,signup;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signup=(Button)findViewById(R.id.SignUpAChemist);
        joinNowButton = (Button) findViewById(R.id.main_join_now_btn);
        loginButton = (Button) findViewById(R.id.main_login_btn);
        loadingBar=new ProgressDialog(this);

        Paper.init(this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Doc_Login.class);
                startActivity(intent);
            }

        });
        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Doc_Signup.class);
                startActivity(intent);

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ChemSignUp.class);
                startActivity(intent);
            }

        });

    }
}
