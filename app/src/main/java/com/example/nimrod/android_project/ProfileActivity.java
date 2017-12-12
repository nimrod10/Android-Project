package com.example.nimrod.android_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView userEmail;
    private Button logoutButton;
    private Button uploadButton;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userEmail = (TextView)findViewById(R.id.user);

        logoutButton = (Button) findViewById(R.id.logoutButton);

        uploadButton = (Button) findViewById(R.id.uploadButton);

        logoutButton.setOnClickListener(this);
        uploadButton.setOnClickListener(this);

        firebaseAuth = firebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){

            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        FirebaseUser  user = firebaseAuth.getCurrentUser();
        userEmail.setText(user.getEmail());
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
    }
}
