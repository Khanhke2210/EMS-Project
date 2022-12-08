package com.example.emsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ROganizationActivity extends AppCompatActivity {
    private EditText edtRO_Name, edtRO_Major, edtRO_Address, edtRO_Mobile;
    private Button btnRO_Submit;
    private FirebaseAuth fAuth;
    private FirebaseUser fUser;
    private String name, major, address, mobile;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roganization);
        ConstraintsGUI();
        ActionListener();
    }

    private void ConstraintsGUI() {
        edtRO_Name = (EditText) findViewById(R.id.edtROName);
        edtRO_Major = (EditText) findViewById(R.id.edtRCMajor);
        edtRO_Address = (EditText) findViewById(R.id.edtROAddress);
        edtRO_Mobile = (EditText) findViewById(R.id.edtROPhone);
        btnRO_Submit = (Button) findViewById(R.id.btnROSubmit);
        fAuth = FirebaseAuth.getInstance();
        dataManager = new DataManager();
        fUser = fAuth.getCurrentUser();
    }

    private void ActionListener() {
        btnRO_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSubmitDetails();
            }
        });
    }

    private void doSubmitDetails() {
        name = edtRO_Name.getText().toString().trim();
        major = edtRO_Major.getText().toString().trim();
        address = edtRO_Address.getText().toString().trim();
        mobile = edtRO_Mobile.getText().toString().trim();
        String uid = fUser.getUid();

        if(TextUtils.isEmpty(name)) {
            edtRO_Name.setError("Please enter organization name");
            return;
        }

        if(TextUtils.isEmpty(address)) {
            edtRO_Address.setError("Please enter the address");
            return;
        }

        if(TextUtils.isEmpty(mobile)) {
            edtRO_Mobile.setError("Please enter the office mobile");
            return;
        }

        if(TextUtils.isEmpty(major)) {
            edtRO_Major.setError("Please enter the major");
            return;
        }

        Map<String, Object> organization = new HashMap<>();
        organization.put("Organization", name);
        organization.put("Address", address);
        organization.put("Mobile", mobile);
        organization.put("Major", major);

        dataManager.SetUpDocumentation("Organization", uid, ROganizationActivity.this, organization);
    }
}