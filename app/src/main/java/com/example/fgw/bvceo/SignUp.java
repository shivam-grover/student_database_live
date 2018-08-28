package com.example.fgw.bvceo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;


    DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //

    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.email2)
    EditText email2;

    @BindView(R.id.password2)
    EditText password2;

    @BindView(R.id.password3)
    EditText password3;

    @BindView(R.id.signup)
    ImageView signup;

    @BindView(R.id.checkBox)
    CheckBox students;

    @BindView(R.id.checkBoxa)
    CheckBox teachers;

    @BindView(R.id.already)
    TextView already;

    @BindView(R.id.progressBar2)
    ProgressBar progressBar;



    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance ();
        mDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance ().getReference ("users");


    }


    @OnClick(R.id.signup)
    public void setSignupa()
    {
        final String email = email2.getText ().toString ().trim ();
        final String PassWord = password2.getText ().toString ().trim ();
        String PassWord2 = password3.getText ().toString ().trim ();
        final String user = username.getText().toString();

        if(email.isEmpty ())
        {
            email2.setError ("Email is required");
            email2.requestFocus ();
            return;
        }
        if(PassWord.isEmpty ())
        {
            password2.setError ("Password is required");
            password2.requestFocus ();
            return;
        }
        if(PassWord.length ()<6)
        {
            password2.setError ("Minimum length of password required is 6");
            password2.requestFocus ();
            return;
        }
        if(!(PassWord.equals(PassWord2)))
        {
            Toast.makeText(this, "Password does match can't", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility (View.VISIBLE);

        mAuth.createUserWithEmailAndPassword (email , PassWord).addOnCompleteListener (new OnCompleteListener<AuthResult> ( ) {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility (View.GONE);
                if(task.isSuccessful ())
                {   if(teachers.isChecked())
                {
                    login_details_email login = new login_details_email(email,teachers.getText().toString());
                    databaseReference.child(user).setValue (login);
//                    if(teachers.isChecked())
                    {Intent intent = new Intent (SignUp.this, TeacherProfile.class);
                    intent .addFlags (intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity (intent);}
//                    else{
//                        startActivity(new Intent(SignUp.this,Main3Activity.class));
//                    }
                    Toast.makeText (getApplicationContext (),"You registered successfully",Toast.LENGTH_SHORT).show ();}
                  else if(students.isChecked()){
                    login_details_email login = new login_details_email(email,students.getText().toString());
                    databaseReference.child(user).setValue (login);
//                    if(teachers.isChecked())
//                    {Intent intent = new Intent (SignUp.this, TeacherProfile.class);
//                        intent .addFlags (intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity (intent);}
//                    else{
                        startActivity(new Intent(SignUp.this,Main3Activity.class));
//                    }
                    Toast.makeText (getApplicationContext (),"You registered successfully",Toast.LENGTH_SHORT).show ();
                }
                }
                else {
                    if (task.getException ( ) instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText (getApplicationContext ( ), "You are already registered", Toast.LENGTH_SHORT).show ( );
                    } else
                    {
                        Toast.makeText (getApplicationContext (),task.getException ().getMessage (),Toast.LENGTH_SHORT).show ();
                    }
                }
            }

        });



    }
}
