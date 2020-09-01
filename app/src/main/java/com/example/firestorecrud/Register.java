package com.example.firestorecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class Register extends AppCompatActivity {
    public static final String FIRE_LOG = "Fire_log";
    private TextView mWelcomeText;
    private EditText mFullName,mEmail,mPassword;
    private Button mRegisterBtn;
    private Button mLoadBtn;

    private FirebaseFirestore mFirestore;

    DocumentReference documentReference;
    @Override
    protected void onStart() {
        super.onStart();


        mFirestore.collection("users").document("one").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
           String username = documentSnapshot.getString("name");
           mWelcomeText.setText("Welcome "+ username);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirestore = FirebaseFirestore.getInstance();

        mWelcomeText = findViewById(R.id.welcomeText);
        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mRegisterBtn = findViewById(R.id.button);
        mLoadBtn = findViewById(R.id.loadBtn);

        mLoadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirestore.collection("users").document("one").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            StringBuilder data = new StringBuilder("");
                            data.append("Welcome").append(documentSnapshot.getString("name"));
                            if (documentSnapshot.exists() && documentSnapshot !=null) {

                                String username = documentSnapshot.getString("name");
                                String email = documentSnapshot.getString("email");
                                String password = documentSnapshot.getString("password");

                                mWelcomeText.setText(data.toString());
                            }
                        }
                        else {

                            Log.d(FIRE_LOG, "Error: "+ task.getException().getMessage());
                        }
                    }
                });
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mFullName.getText().toString();
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                Map<String, String> userMap = new HashMap<>();
                userMap.put("name",username);
                userMap.put("email",email);
                userMap.put("password",password);

                mFirestore.collection("users").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Register.this, "Username Added to Firestore", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                   String error = e.getMessage();
                        Toast.makeText(Register.this, "Error: "+e, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }
}
