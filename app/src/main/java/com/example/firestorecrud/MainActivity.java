package com.example.firestorecrud;

import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//public class MainActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {
//
//    private EditText mFullName,mEmail,mPassword;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openDialog();
//            }
//        });
//    }
//
//    private void openDialog() {
//        ExampleDialog exampleDialog= new ExampleDialog();
//        exampleDialog.show(getSupportFragmentManager(), "example dialog");
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void applyTexts(String username, String email, String password) {
//        mFullName.setText(username);
//        mEmail.setText(email);
//        mPassword.setText(password);
//    }
//}

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String KEY_TITLE = "email";
    private static final String KEY_DESCRIPTION = "password";
    private EditText editTextTitle;
    private EditText editTextDescription;
    private TextView textViewData;
//    private Button mAdd;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Users");
    private DocumentReference noteRef = db.document("Users/Users Collection");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openDialog();
//            }
//        });

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        textViewData = findViewById(R.id.text_view_data);
//        mAdd = findViewById(R.id.add);

    }
    @Override
    protected void onStart() {
        super.onStart();
        notebookRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                String data = "";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Note note = documentSnapshot.toObject(Note.class);
                    note.setDocumentId(documentSnapshot.getId());
                    String documentId = note.getDocumentId();
                    String email = note.getEmail();
                    String password = note.getPassword();
                    data += "ID: " + documentId
                            + "\nEmail: " + email + "\nPassword: " + password + "\n\n";
                }
                textViewData.setText(data);
            }
        });
    }
    public void addNote(View v) {
        String email = editTextTitle.getText().toString();
        String password = editTextDescription.getText().toString();
        Note note = new Note(email, password);
        notebookRef.add(note);
    }
    public void loadNotes(View v) {
        notebookRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Note note = documentSnapshot.toObject(Note.class);
                            note.setDocumentId(documentSnapshot.getId());
                            String documentId = note.getDocumentId();
                            String email = note.getEmail();
                            String password = note.getPassword();
                            data += "ID: " + documentId
                                    + "\nEmail: " + email + "\nPassword: " + password + "\n\n";
                        }
                        textViewData.setText(data);
                    }
                });
    }
//    private void openDialog() {
//        AlertDialog.Builder alert;
//        alert= new AlertDialog.Builder(this,android.R.style.Theme_Material_Dialog);
//        alert = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater();
//        View view = inflater.inflate(R.layout.activity_main,null);
//
//        editTextTitle = view.findViewById(R.id.edit_text_title);
//        editTextDescription = view.findViewById(R.id.edit_text_description);
//        mAdd = view.findViewById(R.id.add);
//
//        alert.setView(view);
//        alert.setCancelable(false);
//
//        mAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String title = editTextTitle.getText().toString();
//                String description = editTextDescription.getText().toString();
//                Note note = new Note(title, description);
//                notebookRef.add(note);
//                String titles = editTextTitle.getText().toString();
//                Toast.makeText(getApplicationContext(), "Title: "+titles, Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        AlertDialog dialog = alert.create();
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        dialog.show();
//
//    }
}