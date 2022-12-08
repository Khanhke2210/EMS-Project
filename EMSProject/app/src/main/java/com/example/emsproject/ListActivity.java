package com.example.emsproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Controller.DataManager;
import Model.Tests;

public class ListActivity extends AppCompatActivity {
    private TextView tvwList_Name, tvwList_Id, tvwList_Age, tvwList_Gender, tvwList_Major, tvwList_Mobile;
    private EditText edtList_Description;
    private ListView lsvList;
    private RelativeLayout rltInfo, rltList;
    private Button btnList, btnList_Profile, btnList_Logout, btnList_Save, btnList_Edit;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private FirebaseUser fUser;
    private DataManager dataManager;
    private ArrayList<Tests> listOfTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ConstraintsGUI();
        SetUpProfile();
        ActionListener();
    }

    private void ConstraintsGUI() {
        tvwList_Name = (TextView) findViewById(R.id.tvwListName);
        tvwList_Id = (TextView) findViewById(R.id.tvwListId);
        tvwList_Age = (TextView) findViewById(R.id.tvwListAge);
        tvwList_Gender = (TextView) findViewById(R.id.tvwListGender);
        tvwList_Major = (TextView) findViewById(R.id.tvwListMajor);
        tvwList_Mobile = (TextView) findViewById(R.id.tvwListPhone);
        lsvList = (ListView) findViewById(R.id.lsvList);
        edtList_Description = (EditText) findViewById(R.id.edtListIntroduction);
        rltList = (RelativeLayout) findViewById(R.id.rltList);
        rltInfo = (RelativeLayout) findViewById(R.id.rltInfo);
        btnList = (Button) findViewById(R.id.btnList);
        btnList_Profile = (Button) findViewById(R.id.btnListProfile);
        btnList_Save = (Button) findViewById(R.id.btnListSave);
        btnList_Logout = (Button) findViewById(R.id.btnListLogout);
        btnList_Edit = (Button) findViewById(R.id.btnListEdit);
        rltList.setVisibility(View.VISIBLE);
        rltInfo.setVisibility(View.GONE);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fUser = fAuth.getCurrentUser();
        dataManager = new DataManager();
        listOfTest = dataManager.GetListOfTest();
        ItemAdapter adapter = new ItemAdapter(ListActivity.this, listOfTest);
        lsvList.setAdapter(adapter);
    }

    private void ActionListener() {
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rltList.setVisibility(View.VISIBLE);
                rltInfo.setVisibility(View.GONE);
            }
        });
        btnList_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rltList.setVisibility(View.GONE);
                rltInfo.setVisibility(View.VISIBLE);
            }
        });
        btnList_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doPublishDescription();
            }
        });
        btnList_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                finish();
            }
        });
        btnList_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, EditActivity.class);
                intent.putExtra("Name", tvwList_Name.getText().toString().trim());
                intent.putExtra("Age", tvwList_Age.getText().toString().trim());
                intent.putExtra("Gender", tvwList_Gender.getText().toString().trim());
                intent.putExtra("Major", tvwList_Major.getText().toString().trim());
                intent.putExtra("Mobile", tvwList_Mobile.getText().toString().trim());
                ListActivity.this.startActivity(intent);
            }
        });
    }

    private void doPublishDescription() {
        String description = edtList_Description.getText().toString().trim();
        DocumentReference documentReference = fStore.collection("Description").document(fUser.getUid());
        Map<String, Object> candidate = new HashMap<>();
        candidate.put("Content", description);
        documentReference.set(candidate).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ListActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                edtList_Description.setClickable(false);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ListActivity.this, "Failed : " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SetUpProfile() {
        DocumentReference docRef01 = fStore.collection("Candidate").document(fUser.getUid());
        docRef01.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                tvwList_Name.setText(value.getString("FullName"));
                tvwList_Id.setText(fUser.getUid());
                tvwList_Gender.setText("Gender : " + value.getString("Gender"));
                tvwList_Age.setText("Age : " + value.getString("Age"));
                tvwList_Major.setText("Major : " + value.getString("Major"));
                tvwList_Mobile.setText("Mobile : " + value.getString("Mobile"));
            }
        });

        DocumentReference docRef02 = fStore.collection("Description").document(fUser.getUid());
        docRef02.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                edtList_Description.setText(value.getString("Content"));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SetUpProfile();
    }
}