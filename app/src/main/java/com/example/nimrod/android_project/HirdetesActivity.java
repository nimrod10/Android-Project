package com.example.nimrod.android_project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class HirdetesActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int GALLERY_RESULT_CODE = 123;
    private Button uploadButton;

    private EditText rovidLeiras;
    private EditText hosszuLeiras;
    private EditText cim;

    private Button chooseButton;

    private ImageView uploadImage;

    private Uri filepath=null;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;

    private String kep="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hirdetes);

        uploadButton = (Button) findViewById(R.id.uploadButton);

        uploadButton.setOnClickListener(this);

        chooseButton = (Button) findViewById(R.id.chooseButton);

        chooseButton.setOnClickListener(this);

        rovidLeiras = (EditText) findViewById(R.id.rovidLeiras);
        hosszuLeiras = (EditText) findViewById(R.id.hosszuLeiras);
        cim = (EditText) findViewById(R.id.cim);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        uploadImage = (ImageView) findViewById(R.id.uploadImage);
    }

    private void UploadToFirebase(String rovidLeirasSTR, String hosszuLeirasSTR, String cimSTR){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userName = user.getEmail();
        Hirdetes hirdetes = new Hirdetes(userName,cimSTR,rovidLeirasSTR,hosszuLeirasSTR,kep);

        HashMap<String,String> dataMap = new HashMap<String,String>();
        dataMap.put("author",hirdetes.author);
        dataMap.put("cim",hirdetes.cim);
        dataMap.put("rovidLeiras", hirdetes.rovidLeiras);
        dataMap.put("hosszuLeiras", hirdetes.hosszuLeiras);
        dataMap.put("kep",hirdetes.kep);

        mDatabase.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Toast.makeText(HirdetesActivity.this, "Upload successful.", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                }else{

                    Toast.makeText(HirdetesActivity.this, "Upload has failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void UploadFile() {

        final String rovidLeirasSTR = rovidLeiras.getText().toString().trim();
        final String hosszuLeirasSTR = hosszuLeiras.getText().toString().trim();
        final String cimSTR = cim.getText().toString().trim();

        if (firebaseAuth.getCurrentUser() == null) {

            Toast.makeText(this, "You're not logged in.", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            return;
        }

        if (TextUtils.isEmpty(rovidLeirasSTR)) {
            Toast.makeText(this, "Nincs rovid leiras!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(hosszuLeirasSTR)) {
            Toast.makeText(this, "Nincs hosszu leiras!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(cimSTR)) {
            Toast.makeText(this, "Nincs cim!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (filepath == null) {

            kep = "";
            UploadToFirebase(rovidLeirasSTR, hosszuLeirasSTR, cimSTR);

        } else {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            Date date = new Date();
            String dateSTR = dateFormat.format(date);
            StorageReference riversRef = mStorageRef.child("images/" + dateSTR);

            riversRef.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            kep = downloadUrl.toString();
                            UploadToFirebase(rovidLeirasSTR,hosszuLeirasSTR,cimSTR);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });

            // UploadFile();
        }
    }


    private void SelectPicture(){

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_RESULT_CODE);

    }
    @Override
    public void onClick(View v) {
        if(uploadButton == v){
            if(AppStatus.getInstance(this).isOnline())
                UploadFile();
            else
                Toast.makeText(this, "You're not online. Please try again later.", Toast.LENGTH_SHORT).show();
           // finish();
            //startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

        }
        if(chooseButton == v){

            SelectPicture();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_RESULT_CODE && resultCode == RESULT_OK){

            filepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                uploadImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
