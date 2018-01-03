package com.example.nimrod.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private Button registerButton;


    private EditText editEmail;
    private EditText editPassword;

    private TextView signUpTextView;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editEmail = (EditText)findViewById(R.id.editTextMail);
        editPassword = (EditText)findViewById(R.id.editTextPassword);

        button = (Button)findViewById(R.id.submitLogin);

        button.setOnClickListener(this);

        firebaseAuth = firebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }

        registerButton=(Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);
    }

    private void LoginUser(){
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){

            Toast.makeText(this, "Email is empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){

            Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
        }

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful()){

                             Toast.makeText(LoginActivity.this, "Login succesful.", Toast.LENGTH_SHORT).show();
                             finish();
                             startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                         }else{

                             Toast.makeText(LoginActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
                         }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if(v == button)
            if(AppStatus.getInstance(this).isOnline())
                LoginUser();
            else
                Toast.makeText(this, "You're not online. Please try again later...", Toast.LENGTH_SHORT).show();
        if(v==registerButton){
            finish();
            startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
        }
    }
}
