package com.example.daboos.mochat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.daboos.mochat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email_Login_ET)
    EditText emailLoginET;
    @BindView(R.id.pass_Login_ET)
    EditText passLoginET;
    @BindView(R.id.login_Btn)
    Button loginBtn;
    @BindView(R.id.forgetpass_Et)
    TextView forgetpassEt;
    @BindView(R.id.singup_Et)
    TextView singupEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
singupEt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(LoginActivity.this,SignupActivity.class));
    }
});
    loginBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String emal=emailLoginET.getText().toString().trim();
            String pass =passLoginET.getText().toString();
            FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(emal,pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();}
                            else
                                emailLoginET.setError("Required");
                            emailLoginET.requestFocus();
                            YoYo.with(Techniques.RotateIn)
                                    .duration(500)
                                    .playOn(emailLoginET);

                            passLoginET.setError("Required");
                            passLoginET.requestFocus();
                            YoYo.with(Techniques.RotateIn)
                                    .duration(500)
                                    .playOn(passLoginET);

                        }
                    });

        }
    });
    forgetpassEt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(LoginActivity.this, ResetpassActivity.class));
        }
    });


    }


}
