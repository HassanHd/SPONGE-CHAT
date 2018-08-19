package com.example.daboos.mochat.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.daboos.mochat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResetpassActivity extends AppCompatActivity {

    @BindView (R.id.et_EmailR)
    EditText etEmailR;
    @BindView(R.id.btn_Reset)
    Button btnReset;
    @BindView(R.id.progres)
    ProgressBar progres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpass);
        ButterKnife.bind(this);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progres.setVisibility(View.VISIBLE);
                String email = etEmailR.getText().toString().trim();
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progres.setVisibility(View.GONE);
                                    Toast.makeText(ResetpassActivity.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    progres.setVisibility(View.GONE);
                                    YoYo.with(Techniques.RotateIn)
                                            .duration(500)
                                            .playOn(etEmailR);
                                    Toast.makeText(ResetpassActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }}
                        });
            }
        });
    }
}
