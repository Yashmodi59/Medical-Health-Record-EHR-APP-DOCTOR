package com.example.sghdoc.PatientAllergy;

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

public class Doc_Pat_Allergy extends AppCompatActivity {
    private RecyclerView GetAllergyRecyclervlist;
    private DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc__pat__allergy);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String username=bundle.getString("Name");
        final String userkey =bundle.getString("Key");
        final String userid =bundle.getString("ID");
        mref= FirebaseDatabase.getInstance().getReference().child("Users").child(userkey).child("Allergy");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("Name",username);
                bundle.putString("ID",userid);
                bundle.putString("Key",userkey);
                Intent intent=new Intent(Doc_Pat_Allergy.this, Add_Doc_Pat_Allergy.class);
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
        GetAllergyRecyclervlist = (RecyclerView) findViewById(R.id.display_allergy_rec_list);
        GetAllergyRecyclervlist.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<UserAllergy> options= new FirebaseRecyclerOptions.Builder<UserAllergy>().setQuery(mref,UserAllergy.class).build();
        FirebaseRecyclerAdapter<UserAllergy,AllergyActivityViewHolder> adapter=new FirebaseRecyclerAdapter<UserAllergy, AllergyActivityViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AllergyActivityViewHolder holder, int position, @NonNull UserAllergy model) {
                holder.date.setText(model.getDate());
                holder.severity.setText(model.getSeverity());
                holder.type.setText(model.getType());
                holder.stillhave.setText(model.getStillhave());
                holder.allergy.setText(model.getAllergy());
            }

            @NonNull
            @Override
            public AllergyActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_display_allergy,parent,false);
                Doc_Pat_Allergy.AllergyActivityViewHolder viewHolderholder=new Doc_Pat_Allergy.AllergyActivityViewHolder(view);
                return viewHolderholder;
            }
        };
        GetAllergyRecyclervlist.setAdapter(adapter);
        adapter.startListening();
    }
    public static class AllergyActivityViewHolder extends RecyclerView.ViewHolder{
        TextView allergy,stillhave,type,severity,date;


        public AllergyActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            allergy=itemView.findViewById(R.id.user_allergy_name);
            stillhave=itemView.findViewById(R.id.user_allergy_still_have);
            type=itemView.findViewById(R.id.user_allergy_type);
            severity=itemView.findViewById(R.id.user_allergy_severity);
            date=itemView.findViewById(R.id.user_date);
        }
    }}
