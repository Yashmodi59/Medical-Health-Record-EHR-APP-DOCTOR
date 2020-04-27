package com.example.sghdoc.PatientReport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sghdoc.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class View_Doc_Pat_Report extends AppCompatActivity {
    ListView listView;

    StorageReference storageReference;
    DatabaseReference databaseReference;
    List<uploadpdf> uploadpdfList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__doc__pat__report);
        listView=(ListView)findViewById(R.id.pdfview);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String username=bundle.getString("Name");
        final String userkey =bundle.getString("Key");
        final String userid =bundle.getString("ID");

        uploadpdfList=new ArrayList<>();
        viewAllfiles();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                uploadpdf uploadpdf =uploadpdfList.get(position);
                Intent intent=new Intent();
                intent.setType(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uploadpdf.getText()));
                startActivity(intent);
            }
        });
    }

    private void viewAllfiles() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String username=bundle.getString("Name");
        final String userkey =bundle.getString("Key");
        final String userid =bundle.getString("ID");

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(userkey).child("uploads");
        databaseReference.child("task").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
                    uploadpdf uploadpdf = postsnapshot.getValue(com.example.sghdoc.PatientReport.uploadpdf.class);
                    uploadpdfList.add(uploadpdf);
                }
                String[] uploads = new String[uploadpdfList.size()];
                for (int i = 0; i < uploads.length; i++) {
                    uploads[i] = uploadpdfList.get(i).getUrl();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads){


                    @Override
                    public View getView(int position,  View convertView,  ViewGroup parent) {
                        View view=super.getView(position, convertView, parent);
                        TextView my=(TextView)view.findViewById(android.R.id.text1);
                        my.setTextColor(Color.BLUE);
                        return super.getView(position, convertView, parent);

                    }
                };

                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
