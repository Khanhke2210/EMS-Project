package com.example.emsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText edtLogin_Email, edtLogin_Password;
    private TextView tvwLogin_Forgot;
    private Button btnLogin;
    private FirebaseAuth fAuth;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ConstraintsGUI();
        ActionListener();
    }

    private void ConstraintsGUI() {
        edtLogin_Email = (EditText) findViewById(R.id.edtLoginEmail);
        edtLogin_Password = (EditText) findViewById(R.id.edtLoginPassword);
        tvwLogin_Forgot = (TextView) findViewById(R.id.tvwLoginReset);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        fAuth = FirebaseAuth.getInstance();
    }

    private void ActionListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });

        tvwLogin_Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doReset();
            }
        });
    }

    private void doLogin() {
        email = edtLogin_Email.getText().toString().trim();
        password = edtLogin_Password.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            edtLogin_Email.setError("Please enter your email");
            return;
        }

        if(TextUtils.isEmpty(password)) {
            edtLogin_Password.setError("Please enter your password");
            return;
        }

        fAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                toList();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Failed : " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doReset() {

        if(TextUtils.isEmpty(email)) {
            edtLogin_Email.setError("Please enter your email");
            return;
        }

        fAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(LoginActivity.this, "Reset Link Sent", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Failed : " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toList() {
        startActivity(new Intent(LoginActivity.this, ListActivity.class));
    }
}