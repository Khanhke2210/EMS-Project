package com.example.emsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import Controller.DataManager;

public class RCandidateActivity extends AppCompatActivity {
    private EditText edtRC_Name, edtRC_Age, edtRC_Major, edtRC_Mobile;
    private Button btnRC_Submit;
    private RadioButton rdbRC_Male, rdbRC_Female;
    private FirebaseAuth fAuth;
    private FirebaseUser fUser;
    private String name, age, gender, major, mobile;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rcandidate);
        ConstraintsGUI();
        ActionListener();
    }

    private void ConstraintsGUI() {
        edtRC_Name = (EditText) findViewById(R.id.edtRCName);
        edtRC_Age = (EditText) findViewById(R.id.edtRCAge);
        edtRC_Major = (EditText) findViewById(R.id.edtRCMajor);
        edtRC_Mobile = (EditText) findViewById(R.id.edtRCMobile);
        btnRC_Submit = (Button) findViewById(R.id.btnRCSubmit);
        rdbRC_Female = (RadioButton) findViewById(R.id.rdbRCFemale);
        rdbRC_Male = (RadioButton) findViewById(R.id.rdbRCMale);
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        dataManager = new DataManager();
    }

    private void ActionListener() {
        btnRC_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    doSubmitDetails();
            }
        });
    }

    private void doSubmitDetails() {
        name = edtRC_Name.getText().toString().trim();
        age = edtRC_Age.getText().toString().trim();
        if (rdbRC_Male.isChecked()) {
            gender = "Male";
        } else {
            gender = "Female";
        }
        major = edtRC_Major.getText().toString().trim();
        mobile = edtRC_Mobile.getText().toString().trim();
        String uid = fUser.getUid();

        if (TextUtils.isEmpty(name)) {
            edtRC_Name.setError("Please enter your name");
            return;
        }

        if (TextUtils.isEmpty(age)) {
            edtRC_Age.setError("Please enter your age");
            return;
        }

        if (TextUtils.isEmpty(mobile)) {
            edtRC_Mobile.setError("Please enter your phone number");
            return;
        }

        if (TextUtils.isEmpty(major)) {
            edtRC_Major.setError("Please enter your major");
            return;
        }

        Map<String, Object> candidate = new HashMap<>();
        candidate.put("FullName", name);
        candidate.put("Age", age);
        candidate.put("Mobile", mobile);
        candidate.put("Major", major);
        candidate.put("Gender", gender);

        dataManager.SetUpDocumentation("Candidate", uid, RCandidateActivity.this, candidate);
    }
}