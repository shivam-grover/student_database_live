package com.example.fgw.bvceo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main3Activity extends AppCompatActivity {
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.editText)
    EditText name;
    @BindView(R.id.editText2)
    EditText branch;
    @BindView(R.id.editText3)
    EditText sh;
    @BindView(R.id.imageView)
    ImageView set;
    //STUDENT

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        String  uid = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance ().getReference ("student").child(uid);

    }

    @OnClick(R.id.imageView)
    public void onViewClicked() {

        String name1 = name.getText().toString();
        String braanch = branch.getText().toString();
        String shi = sh.getText().toString();

        student_profile student = new student_profile(name1,braanch,shi);

        databaseReference.child("profile").setValue (student);
        Toast.makeText (this, "Appliance added",Toast.LENGTH_LONG).show ();




    }
}
