package com.example.firebase02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    EditText edtName, edtEmail, edtPassword, edtPassword02;
    Button btnRegister;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_01);
        btnRegister=this.findViewById(R.id.btnRegisterIN);
        auth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    fmRegister(v);
            }
        });
    }
    public void fmRegister(View v){

        edtName = this.findViewById(R.id.edtNameRE);
        edtEmail = this.findViewById(R.id.edtMailRE);
        edtPassword = this.findViewById(R.id.edtPassRE01);
        edtPassword02 = this.findViewById(R.id.edtPassRE02);

        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String password02 = edtPassword02.getText().toString().trim();

        if (password.equalsIgnoreCase(password02)) {
            Toast.makeText(RegisterActivity.this, "loading...",
                    Toast.LENGTH_SHORT).show();

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String id1 = auth.getCurrentUser().getUid();
                                Toast.makeText(RegisterActivity.this, id1+"  -  createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                addToDatabase(id1,new User(name,email,0,0,0));
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);


                            } else
                                Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                        }
                    });


        }else{
            Toast.makeText(RegisterActivity.this, "Check Field!!!",
                    Toast.LENGTH_SHORT).show();
        }

    }
    public void addToDatabase(String userId, User user){
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("users").child(userId).setValue(user);
        mDatabase.child("users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Error getting data", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Add new user: "+userId, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
