package com.example.sghdoc.PatientPrescription;

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

public class Doc_Pat_Presc extends AppCompatActivity {
    private RecyclerView GetPrescriptionRecyclervlist;
    private DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc__pat__presc);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        final String username=bundle.getString("Name");
        final String userkey =bundle.getString("Key");
        final String userid =bundle.getString("ID");

        mref= FirebaseDatabase.getInstance().getReference().child("Users").child(userkey).child("Prescription");
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("Name",username);
                bundle.putString("ID",userid);
                bundle.putString("Key",userkey);
                Intent intent=new Intent(Doc_Pat_Presc.this, Add_Doc_Pat_Activity.class);
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

        GetPrescriptionRecyclervlist = (RecyclerView) findViewById(R.id.display_pres_rec_list);
        GetPrescriptionRecyclervlist.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<UserPrescription> options= new FirebaseRecyclerOptions.Builder<UserPrescription>().setQuery(mref,UserPrescription.class).build();
        FirebaseRecyclerAdapter<UserPrescription, Doc_Pat_Presc.PrescriptionViewHolder> adapter= new FirebaseRecyclerAdapter<UserPrescription, PrescriptionViewHolder>(options) {
            @NonNull
            @Override
            public PrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_display_prescription,parent,false);
                Doc_Pat_Presc.PrescriptionViewHolder viewHolderholder=new Doc_Pat_Presc.PrescriptionViewHolder(view);
                return viewHolderholder;

            }

            @Override
            protected void onBindViewHolder(@NonNull PrescriptionViewHolder holder, int position, @NonNull final UserPrescription model) {
                holder.doctor.setText(model.getDoctor());
                holder.remark.setText(model.getRemark());
                holder.date.setText(model.getDate());
                holder.appointment.setText(model.getAppointment());
                holder.severity.setText(model.getSeverity());
                holder.report.setText(model.getReport());
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

        };
        GetPrescriptionRecyclervlist.setAdapter(adapter);
        adapter.startListening();
        GetPrescriptionRecyclervlist.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {

            }
        });

    }
    public static class PrescriptionViewHolder extends RecyclerView.ViewHolder{
        TextView doctor,remark,appointment,severity,date,report;


        public PrescriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            doctor=itemView.findViewById(R.id.user_dp1);
            remark=itemView.findViewById(R.id.user_dp2);
            appointment=itemView.findViewById(R.id.user_dp4);
            severity=itemView.findViewById(R.id.user_dp3);
            date=itemView.findViewById(R.id.user_dp5);
            report=itemView.findViewById(R.id.user_dp_rep);
        }

}
}
