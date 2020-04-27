package com.example.sghdoc.PatientMedicalHistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sghdoc.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Doc_Pat_MH extends AppCompatActivity {
    private RecyclerView GetHistoryRecyclervlist;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc__pat__mh);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String username=bundle.getString("Name");
        final String userkey =bundle.getString("Key");
        final String userid =bundle.getString("ID");
        mref= FirebaseDatabase.getInstance().getReference().child("Users").child(userkey).child("MedicalHistory");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("Name",username);
                bundle.putString("ID",userid);
                bundle.putString("Key",userkey);
                Intent intent = new Intent(Doc_Pat_MH.this, Add_Doc_Pat_MH.class);
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
        GetHistoryRecyclervlist = (RecyclerView) findViewById(R.id.display_mh_rec_list);
        GetHistoryRecyclervlist.setLayoutManager(new LinearLayoutManager(this));

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<UserMedicalHistory> options= new FirebaseRecyclerOptions.Builder<UserMedicalHistory>().setQuery(mref,UserMedicalHistory.class).build();
        FirebaseRecyclerAdapter<UserMedicalHistory, MedicalHistoryViewHolder > adapter=new FirebaseRecyclerAdapter<UserMedicalHistory, MedicalHistoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MedicalHistoryViewHolder holder, int position, @NonNull UserMedicalHistory model) {
                holder.date.setText(model.getDate());
                holder.severity.setText(model.getSeverity());
                holder.type.setText(model.getType());
                holder.stillhave.setText(model.getStillhave());
                holder.haveAny.setText(model.getHaveAny());
                holder.isuploaded.setText(model.getIsiploaded());
                holder.history.setText(model.getHistory());
                holder.docname.setText(model.getDocname());
            }
            @NonNull
            @Override
            public MedicalHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_mh,parent,false);
                Doc_Pat_MH.MedicalHistoryViewHolder viewHolderholder=new Doc_Pat_MH.MedicalHistoryViewHolder(view);
                return viewHolderholder;
            }
        };

        GetHistoryRecyclervlist.setAdapter(adapter);
        adapter.startListening();
    }
    public static class MedicalHistoryViewHolder extends RecyclerView.ViewHolder{
        TextView history,haveAny,docname,stillhave,isuploaded,type,severity,date;


        public MedicalHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            history=itemView.findViewById(R.id.user_mh_name);
            haveAny=itemView.findViewById(R.id.user_mh_have_any);
            docname=itemView.findViewById(R.id.user_mh_doc_name);
            date=itemView.findViewById(R.id.user_mh_date);;
            stillhave=itemView.findViewById(R.id.user_mh_still_have);
            isuploaded=itemView.findViewById(R.id.user_mh_is_uploaded);
            type=itemView.findViewById(R.id.user_mh_type);
            severity=itemView.findViewById(R.id.user_mh_severity);
        }
    }

}
