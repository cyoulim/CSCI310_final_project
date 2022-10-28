package com.example.csci310_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase root;
    private DatabaseReference mDatabase;
    int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init(){
        Button button= (Button)findViewById(R.id.buttonPost);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText username_ = findViewById(R.id.username);
                String username = username_.getText().toString();
                EditText bio_ = findViewById(R.id.bio);
                String bio = bio_.getText().toString();
                EditText ddl_ = findViewById(R.id.deadline);
                String ddl = ddl_.getText().toString();
                EditText address_ = findViewById(R.id.address);
                String address = address_.getText().toString();
                EditText u_ = findViewById(R.id.utilities);
                String u = u_.getText().toString();
                EditText rent_ = findViewById(R.id.rent);
                String rent = rent_.getText().toString();
                Invitation invite = new Invitation(username, bio, ddl, address, u, rent);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("invitation")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                int counter = task.getResult().size();
                                CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("invitation");
                                collectionReference.document(String.valueOf(counter)).set(invite);

                            } else {
                                Log.d("test", "Error getting documents: ", task.getException());
                            }
                        });
            }
        });
    }

    public class User {

        public String username;
        public String email;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String username, String email) {
            this.username = username;
            this.email = email;
        }

    }

    public class Invitation {

        public String username;
        public String bio;
        public String deadline;
        public String address;
        public String utilities;
        public String rent;
        public ArrayList<Integer> accept = new ArrayList<>();
        public ArrayList<Integer> reject = new ArrayList<>();

        public Invitation() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Invitation(String username, String bio, String deadline,
                         String address, String utilities, String rent) {
            this.accept = new ArrayList<>();
            this.reject = new ArrayList<>();
            this.username = username;
            this.bio = bio;
            this.deadline = deadline;
            this.address = address;
            this.utilities = utilities;
            this.rent = rent;
        }
        public void update_accept(int userId){
            accept.add(userId);
        }
        public void update_reject(int userId){
            reject.add(userId);
        }
    }




}