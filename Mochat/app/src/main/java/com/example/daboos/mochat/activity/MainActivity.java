package com.example.daboos.mochat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.daboos.mochat.R;
import com.example.daboos.mochat.adabtor.ChatAdabtor;
import com.example.daboos.mochat.model.Massage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rececl)
    RecyclerView rececl;
    @BindView(R.id.masage_ET)
    EditText masageET;
    @BindView(R.id.fab_botn)
    FloatingActionButton fabBotn;
    private ChatAdabtor adabtor;
    private RecyclerView.LayoutManager mlayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mlayoutManager = new LinearLayoutManager(this);
        rececl.setLayoutManager(mlayoutManager);


                String massage = masageET.getText().toString();
                FirebaseDatabase.getInstance().getReference()
                        .child("chat")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                List<Massage> massageslist = new ArrayList<>();
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    Massage massage = child.getValue(Massage.class);
                                    massageslist.add(massage);
                                }
                                adabtor = new ChatAdabtor(MainActivity.this, massageslist);
                                rececl.setAdapter(adabtor);
                                rececl.scrollToPosition(massageslist.size() - 1);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
        fabBotn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Massage massage = new Massage();
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                massage.setMassage(masageET.getText().toString());
                masageET.setText(" ");
                massage.setEmail(email);
                String Key = FirebaseDatabase.getInstance().getReference().child("chat")
                        .push().getKey();
                FirebaseDatabase.getInstance().getReference()
                        .child("chat")
                        .child(Key)
                        .setValue(massage)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //
                                } else {
                                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;
        }
        return false;
    }
}
