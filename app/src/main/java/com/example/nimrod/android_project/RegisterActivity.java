package com.example.nimrod.android_project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private Button backButton;

    private EditText editEmail;
    private EditText editPassword;

    private TextView signUpTextView;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        button =(Button)findViewById(R.id.submitRegister);

        editEmail = (EditText)findViewById(R.id.editTextMail);
        editPassword = (EditText)findViewById(R.id.editTextPassword);

      //  signUpTextView = (TextView)findViewById(R.id.registerWarning);

        button.setOnClickListener(this);
        signUpTextView.setVisibility(View.INVISIBLE);

        firebaseAuth = FirebaseAuth.getInstance();

        backButton = (Button) findViewById(R.id.Back);
        backButton.setOnClickListener(this);
    }

    private void RegisterUser(){

        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(TextUtils.isEmpty((email))){
            Toast.makeText(this, "Email is empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty((password))){
            Toast.makeText(this, "Password is empty.",Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Registration has succeded.",Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this," Failed to register.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if(v == button){
            if(AppStatus.getInstance(this).isOnline()){
                RegisterUser();
            }else{
                Toast.makeText(this, "You're not online. Please try again later...", Toast.LENGTH_SHORT).show();
            }
        }

        if(backButton == v){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));

        }
    }
}
