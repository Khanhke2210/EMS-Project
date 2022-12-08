package com.example.emsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import Controller.DataManager;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtRegister_Email, edtRegister_Password, edtRegister_Confirm;
    private Button btnRegister;
    private RadioButton rdbRegister_Organization, rdbRegister_Candidate;
    private String email, password, confirm;
    private DataManager dataFetching;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ConstraintsGUI();
        ActionListener();
    }

    private void ConstraintsGUI() {
        edtRegister_Email = (EditText) findViewById(R.id.edtRegisterEmail);
        edtRegister_Password = (EditText) findViewById(R.id.edtRegisterPassword);
        edtRegister_Confirm = (EditText) findViewById(R.id.edtRegisterConfirm);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        rdbRegister_Candidate = (RadioButton) findViewById(R.id.rdbCandidate);
        rdbRegister_Organization = (RadioButton) findViewById(R.id.rdbOrganization);
        dataFetching = new DataManager();
    }

    private void ActionListener() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSignUp();
            }
        });
    }

    private void doSignUp() {
        email = edtRegister_Email.getText().toString().trim();
        password = edtRegister_Password.getText().toString().trim();
        confirm = edtRegister_Confirm.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            edtRegister_Email.setError("Please enter an email");
            return;
        }

        if(TextUtils.isEmpty(password)) {
            edtRegister_Password.setError("Please enter a password");
            return;
        }

        if(password.length() < 6) {
            edtRegister_Password.setError("Password must be no less than 6 characters");
            return;
        }

        if(!password.equals(confirm)) {
            edtRegister_Confirm.setError("Wrong password please try again");
            return;
        }

        String direction = "toROrganization";

        if(rdbRegister_Candidate.isChecked()) {
            direction = "toRCandidate";
        }

        dataFetching.SignNewUpAccount(email, password, RegisterActivity.this, direction);
    }

    public void toRCandidate() {
        startActivity(new Intent(RegisterActivity.this, RCandidateActivity.class));
    }

    public void toROrganization() {
        startActivity(new Intent(RegisterActivity.this, ROganizationActivity.class));
    }
}