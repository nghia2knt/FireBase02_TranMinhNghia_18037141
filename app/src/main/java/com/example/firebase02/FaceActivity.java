package com.example.firebase02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class FaceActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private String face;
    private String id1 = "";
    private int temp=0;
    public int a=0;
    public int b=0;
    public int s=0;
    public User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.funct_01);
        Intent intent = getIntent();
        String uid = intent.getExtras().getString("uid");
        Toast.makeText(FaceActivity.this, "Hi, "+uid,
                    Toast.LENGTH_SHORT).show();
        id1=uid;
        writeFrist(uid);
        TextView tvStatus = findViewById(R.id.tvStatus);
        ImageView imgSmile = (ImageView) findViewById(R.id.ivSmile);
        imgSmile.setClickable(true);
        face="";
        imgSmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FaceActivity.this,
                        "You chose: Smile!!",
                        Toast.LENGTH_LONG).show();
                tvStatus.setText("Smile!!");
                face="smile";

            }
        });

        ImageView imgAngry = (ImageView) findViewById(R.id.ivAngry);
        imgAngry.setClickable(true);
        imgAngry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FaceActivity.this,
                        "You chose: Angry!!",
                        Toast.LENGTH_LONG).show();
                tvStatus.setText("Angry!!");
                face="angry";

            }
        });

        ImageView imgBored = (ImageView) findViewById(R.id.ivBored);
        imgBored.setClickable(true);
        imgBored.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FaceActivity.this,
                        "You chose: Bored!!",
                        Toast.LENGTH_LONG).show();
                tvStatus.setText("Bored!!");
                face="bored";

            }
        });
        Button btnFinish = (Button) findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (face.equalsIgnoreCase(""))
                {
                    Toast.makeText(FaceActivity.this,
                            "Error",
                            Toast.LENGTH_LONG).show();
                }
                else
                    new2(id1, face);

            }

        });
    }
    public void writeFrist(String userId){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // temp = dataSnapshot.getValue(Integer.class);
               user =  dataSnapshot.getValue(User.class);
               Toast.makeText(FaceActivity.this,
                        user.toString(),
                        Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void new2(String userId, String face){
        int sl=0;
        if (face.equalsIgnoreCase("angry")){
            sl=user.getAngry();
            user.setAngry(sl+1);
        }else
            if (face.equalsIgnoreCase("smile")){
                sl=user.getSmile();
                user.setSmile(sl+1);
            } else if (face.equalsIgnoreCase("bored")){
                    sl=user.getBored();
                    user.setBored(sl+1);
        }
            sl=sl+1;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(userId).child(face).setValue(sl);
        Toast.makeText(FaceActivity.this,
                "Increase Your Face: <" + face +"> in Database",
                Toast.LENGTH_LONG).show();



    }

}
