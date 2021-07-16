package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.whatsappclone.databinding.ActivitySignInBinding;
import com.example.whatsappclone.databinding.ActivitySignUpFileBinding;
import com.example.whatsappclone.models.Users;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
//#TODO
//here we create the acccount using firebase(sign up)
//also here we create realtime database of the firebase
//and we get the id of the database using getUid() method
//and we also save the data of email,password and username in Users data class
//also this is user class save in realtime database by the code
//database.getReference().child("Users").child(id).setValue(user);
//binding concept is very new here we add the buildfeature and states viewbinding as true in build gradle
//so using the binding we get all the ids of the activity
//intialize the binding like
//binding=ActivitySignUpFileBinding.inflate(getLayoutInflater());
//setContentView(binding.getRoot())
//so we can use whenever we need like
//binding.emailSignup.getText().toString
public class SignUpActivityFile extends AppCompatActivity {
 ActivitySignUpFileBinding binding;
private FirebaseAuth auth;
FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpFileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        binding.mainSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                auth.createUserWithEmailAndPassword(binding.emailSignup.getText().toString(), binding.PassSignup.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Users user = new Users(binding.UserSignup.getText().toString(), binding.PassSignup.getText().toString(), binding.emailSignup.getText().toString());
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(user);
                            Toast.makeText(SignUpActivityFile.this, "USER CREATED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SignUpActivityFile.this, "Error Occured", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
        binding.googleSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.textSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i4 = new Intent(SignUpActivityFile.this, Sign_in.class);
                startActivity(i4);
            }
        });


    }}