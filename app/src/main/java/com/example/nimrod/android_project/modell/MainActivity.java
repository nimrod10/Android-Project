    package com.example.nimrod.android_project.modell;

    import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
    import android.util.Log;
    import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

    import com.example.nimrod.android_project.R;
    import com.example.nimrod.android_project.hirdetes.Hirdetes;
    import com.example.nimrod.android_project.hirdetes.HirdetesActivity;
    import com.example.nimrod.android_project.hirdetes.HirdetesDetailedActivity;
    import com.example.nimrod.android_project.internet.AppStatus;
    import com.example.nimrod.android_project.user.LoginActivity;
    import com.example.nimrod.android_project.user.ProfileActivity;
    import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

    public class MainActivity extends AppCompatActivity implements View.OnClickListener {

        private static final int REQUEST_CODE = 1 ;
        private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;

    private TextView offlineText;
    private TextView loggedInUser;

    private Button uploadButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.hirdetes_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseAuth = firebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
     // finish();

      // startActivity(new Intent(getApplicationContext(),LoginActivity.class));

        uploadButton = (Button) findViewById(R.id.upload);
        uploadButton.setOnClickListener(this);

        offlineText = (TextView) findViewById(R.id.offlineText);

        loggedInUser = (TextView) findViewById(R.id.user);

        if(firebaseAuth.getCurrentUser()!=null){
            String user = firebaseAuth.getCurrentUser().getEmail();
            user = "Logged in as: " + user.substring(0, user.indexOf('@'));
            loggedInUser.setText(user);
        }else{
            loggedInUser.setText("You're not logged in.");
        }
        loggedInUser.setOnClickListener(this);
    }

        @Override
        public void onClick(View v) {
            if(v == uploadButton){
                if(firebaseAuth.getCurrentUser()==null){
                    finish();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                }else{
                    finish();
                    startActivity(new Intent(getApplicationContext(),HirdetesActivity.class));
                }

            }
            if(v== loggedInUser){
                if(firebaseAuth.getCurrentUser()==null) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }else{
                    finish();
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                }
            }
        }

        @Override
        protected void onStart() {
            super.onStart();
            if(AppStatus.getInstance(this).isOnline()){
                offlineText.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
                FirebaseRecyclerAdapter<Hirdetes, HirdetesViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Hirdetes, HirdetesViewHolder>(
                        Hirdetes.class,
                        R.layout.hirdetes_row,
                        HirdetesViewHolder.class,
                        mDatabase
                ){

                    @Override
                    protected void populateViewHolder(HirdetesViewHolder viewHolder, Hirdetes model, int position) {
                        final String cim = model.getCim();
                        final String author = model.getAuthor();
                        final String rovidLeiras = model.getRovidLeiras();
                        final String hosszuLeiras = model.getHosszuLeiras();
                        final String image = model.getKep();

                        viewHolder.SetCim(model.getCim());
                        viewHolder.SetAuthor(model.getAuthor());
                        viewHolder.SetRovidLeiras(model.getRovidLeiras());
                        viewHolder.SetImage(getApplicationContext(), model.getKep());
                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this,HirdetesDetailedActivity.class);
                              // v.getContext().startActivity(intent);
                               intent.putExtra("cim",cim);
                               intent.putExtra("author",author);
                               //intent.putExtra("rovidLeiras",rovidLeiras);
                               intent.putExtra("hosszuLeiras", hosszuLeiras);
                               intent.putExtra("image",image);
                                Log.d("OnClickView",cim);
                               // startActivityForResult(intent,REQUEST_CODE);
                                startActivity(intent);
                                finish();

                            }
                        });
                    }
                };
                mRecyclerView.setAdapter(firebaseRecyclerAdapter);
            }else{
                Toast.makeText(this, "You're not online.", Toast.LENGTH_SHORT).show();
                offlineText.setVisibility(View.VISIBLE);
            }
        }

        public static class HirdetesViewHolder extends RecyclerView.ViewHolder{
            View mView;
            public HirdetesViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
            }

            public void SetCim(String cim){
                TextView cimView = (TextView) itemView.findViewById(R.id.title);
                cimView.setText(cim);
            }
            public void SetRovidLeiras(String rovidLeiras){
                TextView rovidLeirasView = (TextView) itemView.findViewById(R.id.rovidLeiras);
                rovidLeirasView.setText(rovidLeiras);
            }
            public void SetAuthor(String author){
                TextView authorView = (TextView) itemView.findViewById(R.id.Szerzo);
                authorView.setText(author);

            }
            public void SetImage(Context ctx, String Image) {
                ImageView imageView = (ImageView) itemView.findViewById(R.id.image);
                Picasso.with(ctx).load(Image).into(imageView);

            }
        }
    }
