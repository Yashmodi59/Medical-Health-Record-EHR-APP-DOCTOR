package com.example.sghdoc.PatientReport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sghdoc.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Doc_Pat_Report extends AppCompatActivity {
    EditText pdfname;
    Button btn;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc__pat__report);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String username=bundle.getString("Name");
        final String userkey =bundle.getString("Key");
        final String userid =bundle.getString("ID");


        pdfname=(EditText) findViewById(R.id.pdfname);
        btn=(Button) findViewById(R.id.pdfbtn);
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(userkey).child("uploads");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPdffile();
            }
        });
    }

    private void selectPdffile() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent,"Select Pdf File"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK&& data!=null)
        {
            upl(data.getData());
        }
    }

    private void upl(Uri data) {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading....");
        progressDialog.show();
        StorageReference reference=storageReference.child("upload/"+ System.currentTimeMillis()
                +".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uri=taskSnapshot.getStorage().getDownloadUrl();
                while (!uri.isComplete());
                Uri url=uri.getResult();
                uploadpdf uploadpdf=new uploadpdf(pdfname.getText().toString(),url.toString());
                databaseReference.child("task").child(databaseReference.push().getKey()).setValue(uploadpdf);
                Toast.makeText(Doc_Pat_Report.this,"File Uploaded.",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double pro=(100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded: " + (int)pro+"%");

            }
        });
    }

    public void btn_action(View view) {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String username=bundle.getString("Name");
        final String userkey =bundle.getString("Key");
        final String userid =bundle.getString("ID");

        Bundle b=new Bundle();
        b.putString("Name",username);
        b.putString("ID",userid);
        b.putString("Key",userkey);
        Intent intent1=new Intent(Doc_Pat_Report.this, View_Doc_Pat_Report.class);
        intent1.putExtras(b);
        startActivity(intent1);
    }

}
