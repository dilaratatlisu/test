package com.dilaratatlisu.javamaps.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dilaratatlisu.javamaps.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {


    private EditText username;
    private EditText name;
    private EditText email;
    private EditText password;
    private Button register;
    private TextView signUpText;

    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;

    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.username);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signUpText = findViewById(R.id.signupText);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        alertDialog = new AlertDialog.Builder(this);



    }

    public void signup(View view){

        startActivity(new Intent(SignupActivity.this , LoginActivity.class));

    }
    public void signupButton(View view) {
        String txtUsername = username.getText().toString();
        String txtName = name.getText().toString();
        String txtEmail = email.getText().toString();
        String txtPassword = password.getText().toString();

        if (TextUtils.isEmpty(txtUsername) || TextUtils.isEmpty(txtName)
                || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)){
            Toast.makeText(SignupActivity.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
        } else if (txtPassword.length() < 6){
            Toast.makeText(SignupActivity.this, "Password too short!", Toast.LENGTH_SHORT).show();
        } else {
            registerUser(txtUsername , txtName , txtEmail , txtPassword);
        }
    }

    private void registerUser(final String username, final String name, final String email, String password) {

        //alertDialog.setMessage("Please Wait!");
        //alertDialog.show();

        mAuth.createUserWithEmailAndPassword(email , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                HashMap<String , Object> map = new HashMap<>();
                map.put("name" , name);
                map.put("email", email);
                map.put("username" , username);
                map.put("id" , mAuth.getCurrentUser().getUid());
                map.put("bio" , "");
                map.put("imageurl" , "default");

                mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            //alertDialog.setCancelable(true);
                            Toast.makeText(SignupActivity.this, "Sign Up Successfully " +
                                    "for better expereince", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupActivity.this , MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //alertDialog.setCancelable(true);
                Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*private ActivitySignupBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = new User();



    }
    public void signupButton(View view){

        user.setEmail(binding.email.getText().toString());
        user.setPassword(binding.password.getText().toString());


        if (user.getEmail().equals("")|| user.getPassword().equals("")){

            Toast.makeText(SignupActivity.this, "Please enter email and password", Toast.LENGTH_LONG).show();

        } else {

            auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    firebaseFirestore.collection("User").document(auth.getCurrentUser().getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(SignupActivity.this, "User created successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(SignupActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                }
            });
        }


    }

     */


}