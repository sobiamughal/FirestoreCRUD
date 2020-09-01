package com.example.firestorecrud;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ExampleDialog extends AppCompatDialogFragment {

    private EditText mFullName,mEmail,mPassword;
    private Button mRegisterBtn;
    private Button mLoadBtn;

    private ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);
        builder.setView(view).setTitle("SignUp").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("SignUp", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 String username = mFullName.getText().toString();
                 String email = mEmail.getText().toString();
                 String password = mPassword.getText().toString();
                 listener.applyTexts(username,email,password);
            }
        });

        mFullName = view.findViewById(R.id.fullName);
        mEmail = view.findViewById(R.id.Email);
        mPassword = view.findViewById(R.id.Password);
        mRegisterBtn = view.findViewById(R.id.button);
        mLoadBtn = view.findViewById(R.id.loadBtn);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString()+ "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener{
        void applyTexts(String username, String email, String password);
}

}
