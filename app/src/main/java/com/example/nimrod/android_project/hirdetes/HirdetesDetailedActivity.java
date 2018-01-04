package com.example.nimrod.android_project.hirdetes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nimrod.android_project.modell.MainActivity;
import com.example.nimrod.android_project.R;
import com.squareup.picasso.Picasso;

public class HirdetesDetailedActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_CODE = 1;
    private Button backButton;

    private TextView cimView;
    private TextView hosszuLeirasView;
    private ImageView imageView;
    private TextView authorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hirdetes_detailed);

        backButton = (Button) findViewById(R.id.Back);
        backButton.setOnClickListener(this);

        cimView = (TextView) findViewById(R.id.Cim);
        hosszuLeirasView = (TextView) findViewById(R.id.hosszuLeiras);
        authorView = (TextView) findViewById(R.id.szerzo);
        imageView = (ImageView) findViewById(R.id.image1);

        Log.d("HirdetesOnCreateIntent",getIntent().getStringExtra("cim"));

        Intent intent = getIntent();
        String cim = intent.getStringExtra("cim");
        String hosszuLeiras = intent.getStringExtra("hosszuLeiras");
        String szerzo = intent.getStringExtra("author");
        String image = intent.getStringExtra("image");

        cimView.setText(cim);
        hosszuLeirasView.setText(hosszuLeiras);
        authorView.setText(szerzo);
        Picasso.with(getApplicationContext()).load(image).into(imageView);
    }

    @Override
    public void onClick(View v) {
        if(backButton == v){

            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }

}
