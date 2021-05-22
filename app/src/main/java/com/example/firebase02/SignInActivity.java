package com.example.firebase02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    EditText edtEmail, edtPassword;
    Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_01);
        btnSignIn=this.findViewById(R.id.btnSignInIN);
        auth = FirebaseAuth.getInstance();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fmSignIn(v);
            }
        });
    }
    public void fmSignIn(View v){
        edtEmail = this.findViewById(R.id.edtMailSI);
        edtPassword = this.findViewById(R.id.edtPasswordSI);
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                String name = user.getDisplayName();
                                String email = user.getEmail();
                                String uid = user.getUid();
                                Intent intent = new Intent(SignInActivity.this, FaceActivity.class);
                                intent.putExtra("uid", uid);
                                startActivity(intent);
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
