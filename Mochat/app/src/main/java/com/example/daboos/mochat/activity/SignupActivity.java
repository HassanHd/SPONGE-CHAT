package com.example.daboos.mochat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.daboos.mochat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {


    @BindView(R.id.email_Signup_ETL)
    EditText emailSignupETL;
    @BindView(R.id.pass_Signup_ETL)
    EditText passSignupETL;
    @BindView(R.id.sign_Btn)
    Button signBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailSignupETL.getText().toString().trim();
                String password=passSignupETL.getText().toString().trim();
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                startActivity(new Intent(SignupActivity.this,MainActivity.class));
                                else
                                    emailSignupETL.setError("Required");
                                emailSignupETL.requestFocus();
                                YoYo.with(Techniques.RotateIn)
                                        .duration(500)
                                        .playOn(emailSignupETL);

                                passSignupETL.setError("Required");
                                passSignupETL.requestFocus();
                                YoYo.with(Techniques.RotateIn)
                                        .duration(500)
                                        .playOn(passSignupETL);

                            }
                        });

            }
        });

    }

}
