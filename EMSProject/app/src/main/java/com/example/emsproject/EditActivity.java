package com.example.emsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import Controller.DataManager;

public class EditActivity extends AppCompatActivity {
    private EditText edtEdit_Name, edtEdit_Age, edtEdit_Major, edtEdit_Mobile;
    private RadioButton rdbEdit_Male, rdbEdit_Female;
    private Button btnEdit_Submit;
    private FirebaseAuth fAuth;
    private FirebaseUser fUser;
    private String name, age, gender, major, mobile;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ConstraintsGUI();
        SetUpInformation();
        ActionListener();
    }

    private void ConstraintsGUI() {
        edtEdit_Name = (EditText) findViewById(R.id.edtEditName);
        edtEdit_Age = (EditText) findViewById(R.id.edtEditAge);
        edtEdit_Major = (EditText) findViewById(R.id.edtEditMajor);
        edtEdit_Mobile = (EditText) findViewById(R.id.edtEditPhone);
        rdbEdit_Male = (RadioButton) findViewById(R.id.rdbEditMale);
        rdbEdit_Female = (RadioButton) findViewById(R.id.rdbEditFemale);
        btnEdit_Submit = (Button) findViewById(R.id.btnEditConfirm);
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        dataManager = new DataManager();
    }

    private void ActionListener() {
        btnEdit_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doUpdateProfile();
                finish();
            }
        });
    }

    private void SetUpInformation() {
        Intent intent = getIntent();
        edtEdit_Name.setText(intent.getStringExtra("Name"));
        edtEdit_Age.setText(intent.getStringExtra("Age").substring(6));
        if(intent.getStringExtra("Gender").equalsIgnoreCase("Male")) {
            rdbEdit_Male.setSelected(true);
        } else {
            rdbEdit_Female.setSelected(true);
        }
        edtEdit_Major.setText(intent.getStringExtra("Major").substring(8));
        edtEdit_Mobile.setText(intent.getStringExtra("Mobile").substring(9));
    }

    private void doUpdateProfile() {
        name = edtEdit_Name.getText().toString().trim();
        age = edtEdit_Age.getText().toString().trim();
        if (rdbEdit_Male.isChecked()) {
            gender = "Male";
        } else {
            gender = "Female";
        }
        major = edtEdit_Major.getText().toString().trim();
        mobile = edtEdit_Mobile.getText().toString().trim();
        String uid = fUser.getUid();

        if (TextUtils.isEmpty(name)) {
            edtEdit_Name.setError("Please enter your name");
            return;
        }

        if (TextUtils.isEmpty(age)) {
            edtEdit_Age.setError("Please enter your age");
            return;
        }

        if (TextUtils.isEmpty(mobile)) {
            edtEdit_Mobile.setError("Please enter your phone number");
            return;
        }

        if (TextUtils.isEmpty(major)) {
            edtEdit_Major.setError("Please enter your major");
            return;
        }

        Map<String, Object> candidate = new HashMap<>();
        candidate.put("FullName", name);
        candidate.put("Age", age);
        candidate.put("Mobile", mobile);
        candidate.put("Major", major);
        candidate.put("Gender", gender);

        dataManager.SetUpDocumentation("Candidate", uid, EditActivity.this, candidate);
    }
}