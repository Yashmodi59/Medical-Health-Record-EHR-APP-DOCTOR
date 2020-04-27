package com.example.sghdoc.PatientVitals;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Doc_Pat_Vitals extends AppCompatActivity {
    private RecyclerView GetVitalsRecyclervlist;
    private DatabaseReference mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc__pat__vitals);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String username=bundle.getString("Name");
        final String userkey =bundle.getString("Key");
        final String userid =bundle.getString("ID");


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("Name",username);
                bundle.putString("ID",userid);
                bundle.putString("Key",userkey);
                Intent intent=new Intent(Doc_Pat_Vitals.this, Add_Doc_Pat_Vitals.class);
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
        mref= FirebaseDatabase.getInstance().getReference().child("Users").child(userkey).child("Vitals");
        GetVitalsRecyclervlist = (RecyclerView) findViewById(R.id.display_vit_rec_list);
        GetVitalsRecyclervlist.setLayoutManager(new LinearLayoutManager(this));


    }
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<UserVitals> options = new FirebaseRecyclerOptions.Builder<UserVitals>().setQuery(mref, UserVitals.class).build();
        FirebaseRecyclerAdapter<UserVitals, Doc_Pat_Vitals.VitalstActivityViewHolder> adapter = new FirebaseRecyclerAdapter<UserVitals, VitalstActivityViewHolder>(options) {
            @NonNull
            @Override
            public VitalstActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_display_vitals,parent,false);
                Doc_Pat_Vitals.VitalstActivityViewHolder viewHolderholder=new Doc_Pat_Vitals.VitalstActivityViewHolder(view);
                return viewHolderholder;

            }

            @Override
            protected void onBindViewHolder(@NonNull VitalstActivityViewHolder holder, int position, @NonNull UserVitals model) {
                holder.bloodpressure.setText(model.getBloodpressure());
                holder.bloodtemperature.setText(model.getBloodtemperature());
                holder.date.setText(model.getDate());
                holder.respirationrate.setText(model.getRespirationrate());
                holder.heartrate.setText(model.getHeartrate());

            }

        };
        GetVitalsRecyclervlist.setAdapter(adapter);
        adapter.startListening();

    }
    public static class VitalstActivityViewHolder extends RecyclerView.ViewHolder{
        TextView bloodpressure,bloodtemperature,heartrate,respirationrate,date;


        public VitalstActivityViewHolder(@NonNull View itemView) {
            super(itemView);

            bloodpressure=itemView.findViewById(R.id.dv5);
            bloodtemperature=itemView.findViewById(R.id.dv6);
            heartrate=itemView.findViewById(R.id.dv7);
            respirationrate=itemView.findViewById(R.id.dv8);
            date=itemView.findViewById(R.id.dv9);

        }
    }}

