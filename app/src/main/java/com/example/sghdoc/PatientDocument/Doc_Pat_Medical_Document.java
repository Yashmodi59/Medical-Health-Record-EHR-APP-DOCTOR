package com.example.sghdoc.PatientDocument;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sghdoc.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Doc_Pat_Medical_Document extends AppCompatActivity {
    private RecyclerView GetMedicalDocumentRecyclervlist;
    private DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc__pat__medical__document);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String username=bundle.getString("Name");
        final String userkey =bundle.getString("Key");
        final String userid =bundle.getString("ID");
        mref= FirebaseDatabase.getInstance().getReference().child("Users").child(userkey).child("Documents");
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("Name",username);
                bundle.putString("ID",userid);
                bundle.putString("Key",userkey);
                Intent intent=new Intent(Doc_Pat_Medical_Document.this, Add_Doc_Pat_Doc.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
//            addallergy.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent=new Intent(AllergyActivity.this,AddAllergies.class);
//                    startActivity(intent);
//                }
//            });
        });

        GetMedicalDocumentRecyclervlist = (RecyclerView) findViewById(R.id.display_med_rec_list);
        GetMedicalDocumentRecyclervlist.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<UserMedicalDocument> options= new FirebaseRecyclerOptions.Builder<UserMedicalDocument>().setQuery(mref,UserMedicalDocument.class).build();
        FirebaseRecyclerAdapter<UserMedicalDocument, Doc_Pat_Medical_Document.MedicalDocumentViewHolder> adapter= new FirebaseRecyclerAdapter<UserMedicalDocument, Doc_Pat_Medical_Document.MedicalDocumentViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MedicalDocumentViewHolder holder, int position, @NonNull final UserMedicalDocument model) {
                holder.doctor.setText(model.getDoctor());
                holder.remark.setText(model.getRemark());
                holder.date.setText(model.getDate1());
                holder.type.setText(model.getType());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent();
                        intent.setType(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(model.getUurl()));
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public Doc_Pat_Medical_Document.MedicalDocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usermedicaldocument,parent,false);
                Doc_Pat_Medical_Document.MedicalDocumentViewHolder viewHolderholder=new Doc_Pat_Medical_Document.MedicalDocumentViewHolder(view);
                return viewHolderholder;

            }
        };
        GetMedicalDocumentRecyclervlist.setAdapter(adapter);
        adapter.startListening();
    }
    public static class MedicalDocumentViewHolder extends RecyclerView.ViewHolder{
        TextView labname,remark,doctor,date,type;


        public MedicalDocumentViewHolder(@NonNull View itemView) {
            super(itemView);
            doctor=itemView.findViewById(R.id.user_dm3);
            remark=itemView.findViewById(R.id.user_dm2);
            labname=itemView.findViewById(R.id.user_dm1);
            type=itemView.findViewById(R.id.user_dm4);
            date=itemView.findViewById(R.id.user_dm5);

        }
    }

}
