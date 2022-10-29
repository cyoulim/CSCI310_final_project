package com.example.csci310_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Accepted extends AppCompatActivity {
    int myUserId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted);
        init();
    }
    private void init(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("invitation")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //View view= LayoutInflater.from(getContext()).inflate(R.layout.post_layout,null);
                                LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.post_layout, null);
                                TextView username_field = (TextView) layout.findViewById(R.id.username);
                                if(username_field == null) Log.d("test","error.....");
                                Object username_ = document.getData().get("username");
                                if(username_ == null) continue;
                                String username = username_.toString();
                                username_field.setText(username);

                                TextView bio_field = (TextView) layout.findViewById(R.id.bio);
                                Object bio_ = document.getData().get("bio");
                                if(bio_ == null) continue;
                                String bio = bio_.toString();
                                bio_field.setText(bio);

                                TextView deadline_field = (TextView) layout.findViewById(R.id.deadline);
                                Object deadline_ = document.getData().get("deadline");
                                if(deadline_ == null) continue;
                                String deadline = deadline_.toString();
                                deadline_field.setText(deadline);

                                TextView rent_field = (TextView) layout.findViewById(R.id.rent);
                                Object rent_ = document.getData().get("rent");
                                if(rent_ == null) continue;
                                String rent = rent_.toString();
                                rent_field.setText(rent);

                                TextView address_field = (TextView) layout.findViewById(R.id.address);
                                Object address_ = document.getData().get("address");
                                if(address_ == null) continue;
                                String address = address_.toString();
                                address_field.setText(address);

                                TextView utilities_field = (TextView) layout.findViewById(R.id.utilities);
                                Object utilities_ = document.getData().get("utilities");
                                if(utilities_ == null) continue;
                                String utilities = utilities_.toString();
                                utilities_field.setText(utilities);

                                Button btn_accept = (Button) layout.findViewById(R.id.buttonAccept);
                                btn_accept.setOnClickListener(new View.OnClickListener(){
                                    @Override
                                    public void onClick(View view) {
                                        String id = document.getData().get("id").toString();
                                        DocumentReference washingtonRef = db.collection("invitation").document(id);
                                        washingtonRef.update("accept", FieldValue.arrayUnion(myUserId));
                                    }
                                });

                                LinearLayout container = (LinearLayout) findViewById(R.id.linearlayout02);
                                container.addView(layout);
                                //Log.d("test", document.getId() + " => " + document.getData().get("username"));
                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}