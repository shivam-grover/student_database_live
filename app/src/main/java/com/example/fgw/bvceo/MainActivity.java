package com.example.fgw.bvceo;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    AnimationDrawable animationDrawable;
    LottieAnimationView animationView;
    FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;


    DatabaseReference databaseReference;

    // add here
    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.signup)
    TextView signup;

//    @BindView(R.id.progressBar)
//    ProgressBar progressBar;

    @BindView(R.id.signin)
    ImageView signin;

    //end here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        animationView = (LottieAnimationView) findViewById(R.id.animation_view);

        mAuth = FirebaseAuth.getInstance ();
        animationView.setVisibility(View.GONE);
//        progressBar.setVisibility(View.GONE);
        mDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance ().getReference ("users");



    }
    @OnClick(R.id.signin)
    public void count()
    {
        final String emailstring = email.getText ().toString ().trim ();

        // to get Password from user and store it in variable called Password
        final String PassWord = password.getText ().toString ().trim ();

        //to check editText of email should not be empty
        //if it is empty then
        if(emailstring.isEmpty ())
        {
            //set an error
            email.setError ("Email is required");
            //and highlight that box
            email.requestFocus ();
            return;
        }
        if(PassWord.isEmpty ())
        {
            //set an error
            password.setError ("Password is required");
            //it will focus on password
            password.requestFocus ();
            return;
        }
        if(PassWord.length ()<6)
        {
            password.setError ("Minimum length of password required is 6");
            password.requestFocus ();
            return;
        }
//        progressBar.setVisibility (View.VISIBLE);
        animationView.setVisibility(View.VISIBLE);
        animationView.playAnimation();
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(emailstring).matches())
        {
            mAuth.signInWithEmailAndPassword (emailstring,PassWord).addOnCompleteListener (new OnCompleteListener<AuthResult>( ) {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
//                    progressBar.setVisibility (View.GONE);
                    if (task.isSuccessful ())
                    {
                        //if login is successful then

                        Intent intent = new Intent (MainActivity.this, Main3Activity.class);
                        intent .addFlags (intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity (intent);
                        Toast.makeText (getApplicationContext (),"log in",Toast.LENGTH_SHORT).show ();

                    }else
                    {
                        //else
                        Toast.makeText (getApplicationContext (), task.getException ().getMessage (),Toast.LENGTH_SHORT).show ();
                    }
                }
            });

        }
        else{

            databaseReference.child(emailstring).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot!=null){
                        String userID= dataSnapshot.child("email").getValue().toString();
                        final String typr= dataSnapshot.child("typel").getValue().toString();



                        Toast.makeText (getApplicationContext (), typr,Toast.LENGTH_SHORT).show ();

                        mAuth.signInWithEmailAndPassword (userID,PassWord).addOnCompleteListener (new OnCompleteListener<AuthResult>( ) {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                progressBar.setVisibility (View.GONE);
                                animationView.setVisibility(View.GONE);
                                animationView.pauseAnimation();
                                if (task.isSuccessful ())
                                {
                                    //if login is successful then
                                    if(typr.equals("Teacher")) {
                                        Intent intent = new Intent(MainActivity.this, TeacherProfile.class);
                                        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(), "log in", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        startActivity(new Intent(MainActivity.this, Main3Activity.class));
                                    }
                                }else
                                {
                                    //else
                                    Toast.makeText (getApplicationContext (), task.getException ().getMessage (),Toast.LENGTH_SHORT).show ();
                                }
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        //for sign in with valid email address and Password

    }

    @OnClick(R.id.signup)
    public void setSignup(){

        startActivity (new Intent (this, SignUp.class));

    }



}
