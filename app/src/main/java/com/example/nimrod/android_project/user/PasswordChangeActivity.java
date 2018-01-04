package com.example.nimrod.android_project.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nimrod.android_project.R;
import com.example.nimrod.android_project.internet.AppStatus;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordChangeActivity extends AppCompatActivity implements View.OnClickListener{
    private Button submitButton;
    private Button backButton;

    private EditText password1EditText;
    private EditText password2EditText;

    private TextView warningMessageView;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        submitButton = (Button) findViewById(R.id.Submit);
        backButton = (Button) findViewById(R.id.Back);

        warningMessageView = (TextView) findViewById(R.id.warningMessage);

        password1EditText = (EditText) findViewById(R.id.password1);
        password2EditText = (EditText) findViewById(R.id.password2);

        firebaseAuth = FirebaseAuth.getInstance();

        submitButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    private void ChangePassword(){

        String password1 = password1EditText.getText().toString();
        String password2 = password2EditText.getText().toString();

        if(password1.length() < 6)
        {
            warningMessageView.setVisibility(View.VISIBLE);
            warningMessageView.setText("Password is too short.");
            return;
        }

        if(!password1.equals(password2)){
            warningMessageView.setVisibility(View.VISIBLE);
            warningMessageView.setText("Passwords are not the same.");
            return;
        }
        if(!AppStatus.getInstance(getApplicationContext()).isOnline()){
            warningMessageView.setVisibility(View.VISIBLE);
            warningMessageView.setText("You're offline.");
            return;

        }
        firebaseAuth.getCurrentUser().updatePassword(password1);
        Toast.makeText(this, "Password has been updated.", Toast.LENGTH_SHORT).show();
        warningMessageView.setVisibility(View.INVISIBLE);
        finish();
        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
    }

    @Override
    public void onClick(View v) {
        if(v==submitButton){
            ChangePassword();
        }
        if(backButton==v)
        {
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            finish();
        }
    }
}
