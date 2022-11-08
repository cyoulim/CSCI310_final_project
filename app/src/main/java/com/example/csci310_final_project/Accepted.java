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

import java.util.*;
public class Accepted extends AppCompatActivity {
    private String yourUserId;
    List<UserInfo> arr = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted);
        yourUserId = getIntent().getStringExtra("yourUserId");
        init();
    }
    private void init(){
        Button sort_by_rent = new Button(this);
        sort_by_rent.setText("sort by descending rent");
        Button sort_by_utilities = new Button(this);
        sort_by_utilities.setText("sort by descending utilities");

        LinearLayout container = (LinearLayout) findViewById(R.id.linearlayout02);
        container.addView(sort_by_rent);
        sort_by_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                container.removeAllViews();
                container.addView(sort_by_rent);
                container.addView(sort_by_utilities);
                sortByRent(arr);
                defaultDisplay(arr);
            }
        });

        container.addView(sort_by_utilities);
        sort_by_utilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                container.removeAllViews();
                container.addView(sort_by_rent);
                container.addView(sort_by_utilities);
                sortByUtilities(arr);
                defaultDisplay(arr);
            }
        });

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
        // arr.clear();
        db.collection("invitation")
                .whereNotEqualTo("userId", yourUserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("test", "this for loop?");
                                String id = document.getData().get("id").toString();
                                String username = document.getData().get("username").toString();
                                String bio = document.getData().get("bio").toString();
                                String deadline = document.getData().get("deadline").toString();
                                String rent = document.getData().get("rent").toString();
                                String address = document.getData().get("address").toString();
                                String utilities = document.getData().get("utilities").toString();
                                UserInfo user = new UserInfo(id, username, bio, deadline, address, utilities, rent);
                                arr.add(user);
                                Log.d("size", arr.size() + " ");
                                count ++;
                                if (count == task.getResult().size()) {
                                    defaultDisplay(arr);
                                }
                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }
                });
        Log.d("size", arr.size() + " ");
    }
    private void defaultDisplay(List<UserInfo> arr){
        for(UserInfo user: arr){
            LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.post_layout, null);
            String id = user.id;

            TextView username_field = (TextView) layout.findViewById(R.id.username);
            username_field.setText(user.username);

            TextView bio_field = (TextView) layout.findViewById(R.id.bio);
            bio_field.setText(user.bio);

            TextView deadline_field = (TextView) layout.findViewById(R.id.deadline);
            deadline_field.setText(user.deadline);

            TextView rent_field = (TextView) layout.findViewById(R.id.rent);
            rent_field.setText(user.rent);

            TextView address_field = (TextView) layout.findViewById(R.id.address);
            address_field.setText(user.address);

            TextView utilities_field = (TextView) layout.findViewById(R.id.utilities);
            utilities_field.setText(user.utilities);

            Button btn_accept = (Button) layout.findViewById(R.id.buttonAccept);
            btn_accept.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    DocumentReference ref = db.collection("invitation").document(id);
                    ref.update("accept", FieldValue.arrayUnion(yourUserId));
                }
            });
            Button btn_reject = (Button) layout.findViewById(R.id.buttonReject);
            btn_reject.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    DocumentReference ref = db.collection("invitation").document(id);
                    ref.update("reject", FieldValue.arrayUnion(yourUserId));
                }
            });
            LinearLayout container = (LinearLayout) findViewById(R.id.linearlayout02);
            container.addView(layout);

        }
    }
    private void sortByRent(List<UserInfo> arr){
        Collections.sort(arr, (a, b)->(Integer.valueOf(b.rent) - Integer.valueOf(a.rent)));
    }
    private void sortByUtilities(List<UserInfo> arr){
        Collections.sort(arr, (a,b)->(Integer.valueOf(b.utilities) - Integer.valueOf(a.utilities)));
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
    public class UserInfo {
        public String id;
        public String username;
        public String bio;
        public String deadline;
        public String address;
        public String utilities;
        public String rent;
        public ArrayList<String> accept = new ArrayList<>();
        public ArrayList<String> reject = new ArrayList<>();

        public UserInfo() {
        }

        public UserInfo(String id, String username, String bio, String deadline,
                        String address, String utilities, String rent) {
            this.id = id;
            this.username = username;
            this.bio = bio;
            this.deadline = deadline;
            this.address = address;
            this.utilities = utilities;
            this.rent = rent;
        }
    }
}