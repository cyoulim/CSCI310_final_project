package com.example.csci310_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase root;
    private DatabaseReference mDatabase;
    private String yourUserId;
    private String userEmail;
    int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        yourUserId = getIntent().getStringExtra("yourUserId");
        setTag(yourUserId);
        TextView tagView = (TextView)findViewById(R.id.tag);
        //tagView.setText("Welcome " + doc.get("email") + " !");
        if(userEmail == null) tagView.setText("Welcome");
        init();
    }
    private void init(){
        Button profile_btn = (Button)findViewById(R.id.button01);
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchToProfile(yourUserId); }
        });
        Button post_btn = (Button)findViewById(R.id.button02);
        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchToPost(yourUserId); }
        });
        Button accept_btn = (Button)findViewById(R.id.button03);
        accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchToAccept(yourUserId); }
        });
        Button match_btn = (Button)findViewById(R.id.button04);
        match_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchToMatch(yourUserId); }
        });
        Button logout_btn = (Button)findViewById(R.id.logout_Button);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { switchToLogin(); }
        });

        Button button= (Button)findViewById(R.id.buttonPost);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText username_ = findViewById(R.id.username);
                String username = username_.getText().toString();
                username_.setText("");
                EditText bio_ = findViewById(R.id.bio);
                String bio = bio_.getText().toString();
                bio_.setText("");
                EditText ddl_ = findViewById(R.id.deadline);
                String ddl = ddl_.getText().toString();
                ddl_.setText("");
                EditText address_ = findViewById(R.id.address);
                String address = address_.getText().toString();
                address_.setText("");
                EditText u_ = findViewById(R.id.utilities);
                String u = u_.getText().toString();
                u_.setText("");
                EditText rent_ = findViewById(R.id.rent);
                String rent = rent_.getText().toString();
                rent_.setText("");
                postValidator checker = new postValidator();
                if(!checker.isValidName(username) || !checker.isValidBio(bio) || !checker.isValidDdl(ddl) ||
                !checker.isValidAddress(address) || !checker.isValidRent(rent) || !checker.isValidUt(u)) return;
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("invitation")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                int counter = task.getResult().size();
                                Invitation invite = new Invitation(counter, username, bio, ddl, address, u, rent);
                                CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("invitation");
                                collectionReference.document(String.valueOf(counter)).set(invite);
                                // collectionReference.document(String.valueOf(counter)).update("userId", yourUserId);

                            } else {
                                Log.d("test", "Error getting documents: ", task.getException());
                            }
                        });
            }
        });
    }

    public class Invitation {
        public int id;
        public String userId;
        public String username;
        public String bio;
        public String deadline;
        public String address;
        public String utilities;
        public String rent;
        public ArrayList<String> accept = new ArrayList<>();
        public ArrayList<String> reject = new ArrayList<>();

        public Invitation() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Invitation(int id, String username, String bio, String deadline,
                         String address, String utilities, String rent) {
            this.id = id;
            this.userId = yourUserId;
            this.accept = new ArrayList<>();
            this.reject = new ArrayList<>();
            this.username = username;
            this.bio = bio;
            this.deadline = deadline;
            this.address = address;
            this.utilities = utilities;
            this.rent = rent;
        }
        public void update_accept(String userId){
            accept.add(userId);
        }
        public void update_reject(String userId){
            reject.add(userId);
        }
    }
    private void setTag(String userId){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                if (doc.getId().equals(userId)) {
                                    userEmail = doc.get("email").toString();
                                    TextView tagView = (TextView)findViewById(R.id.tag);
                                    tagView.setText("Welcome " + doc.get("email") + " !");
                                    //tagView.setText("Welcome");
                                }
                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void switchToProfile(String yourUserId) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("yourUserId", yourUserId);
        startActivity(intent);
    }

    private void switchToPost(String yourUserId) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("yourUserId", yourUserId);
        startActivity(intent);
    }

    private void switchToAccept(String yourUserId) {
        Intent intent = new Intent(this, Accepted.class);
        intent.putExtra("yourUserId", yourUserId);
        startActivity(intent);
    }

    private void switchToMatch(String yourUserId) {
        Intent intent = new Intent(this, MatchActivity.class);
        intent.putExtra("yourUserId", yourUserId);
        startActivity(intent);
    }

    private void switchToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}