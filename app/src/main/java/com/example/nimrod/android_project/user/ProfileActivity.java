package com.example.nimrod.android_project.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nimrod.android_project.hirdetes.HirdetesActivity;
import com.example.nimrod.android_project.modell.MainActivity;
import com.example.nimrod.android_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView userEmail;
    private Button logoutButton;
    private Button uploadButton;
    private Button backButton;
    private Button passwordButton;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userEmail = (TextView)findViewById(R.id.user);

        logoutButton = (Button) findViewById(R.id.logoutButton);

        uploadButton = (Button) findViewById(R.id.uploadButton);

        backButton = (Button) findViewById(R.id.Back);

        logoutButton.setOnClickListener(this);
        uploadButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){

            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        FirebaseUser  user = firebaseAuth.getCurrentUser();
        userEmail.setText(user.getEmail());

        passwordButton = (Button) findViewById(R.id.changePassword);
        passwordButton.setOnClickListener(this);
    }

    private void LogOutUser(){

        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }

    @Override
    public void onClick(View v) {
        if(v == logoutButton){

            LogOutUser();

        }
        if(v == uploadButton){
            finish();
            startActivity(new Intent(getApplicationContext(),HirdetesActivity.class));
        }

        if(v==backButton){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        if(v == passwordButton){
            finish();
            startActivity(new Intent(getApplicationContext(), PasswordChangeActivity.class));

        }
    }
}
