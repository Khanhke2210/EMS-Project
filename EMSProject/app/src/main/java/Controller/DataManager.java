package Controller;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.emsproject.RCandidateActivity;
import com.example.emsproject.ROganizationActivity;
import com.example.emsproject.RegisterActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import Model.Question;
import Model.Tests;

public class DataManager {
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private FirebaseUser fUser;

    public DataManager() {
        this.fAuth = FirebaseAuth.getInstance();
        this.fStore = FirebaseFirestore.getInstance();
        this.fUser = fAuth.getCurrentUser();
    }

    public void SignNewUpAccount(String email, String password, Activity baseActivity, String direction) {
        fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent intent;

                if(direction.equalsIgnoreCase("toRCandidate")) {
                    intent = new Intent(baseActivity, RCandidateActivity.class);
                } else {
                    intent = new Intent(baseActivity, ROganizationActivity.class);
                }
                baseActivity.startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(baseActivity, "Failed : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void SetUpDocumentation(String collection, String document, Activity baseActivity, Map<String, Object> docMap) {
        DocumentReference documentReference = fStore.collection(collection).document(document);
        documentReference.set(docMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                baseActivity.finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(baseActivity, "Failed : " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public ArrayList<Tests> GetListOfTest() {
        ArrayList<Tests> listOfTest = new ArrayList<>();
        Question q1 = new Question(
                "This is option A",
                "This is option B",
                "This is option C",
                "This is option D",
                "A",
                "What is your option",
                "M"
                );
        q1.setPoint(25);

        Question q2 = new Question(
                "This is option A",
                "This is option B",
                "This is option C",
                "This is option D",
                "B",
                "What is your option",
                "M"
        );
        q2.setPoint(25);

        Question q3 = new Question(
                "This is option A",
                "This is option B",
                "This is option C",
                "This is option D",
                "C",
                "What is your option",
                "M"
        );
        q3.setPoint(25);

        Question q4 = new Question(
                "This is option A",
                "This is option B",
                "This is option C",
                "This is option D",
                "D",
                "What is your option",
                "M"
        );
        q4.setPoint(25);

        Question q5 = new Question(
                "This is option A",
                "This is option B",
                "This is option C",
                "This is option D",
                "A",
                "What is your answer",
                "F"
        );
        q5.setPoint(25);


        Tests test01 = new Tests("RHS001", "Subject Number 001", "RHS", "4");
        test01.addQuestion(q1);
        test01.addQuestion(q2);
        test01.addQuestion(q3);
        test01.addQuestion(q4);

        Tests test02 = new Tests("RHS002", "Subject Number 002", "RHS", "4");
        test02.addQuestion(q1);
        test02.addQuestion(q5);
        test02.addQuestion(q3);
        test02.addQuestion(q4);

        Tests test03 = new Tests("IMC003", "Subject Number 003", "IMC", "4");
        test03.addQuestion(q1);
        test03.addQuestion(q2);
        test03.addQuestion(q5);
        test03.addQuestion(q4);

        Tests test04 = new Tests("IMC004", "Subject Number 004", "IMC", "4");
        test04.addQuestion(q1);
        test04.addQuestion(q2);
        test04.addQuestion(q5);
        test04.addQuestion(q4);

        listOfTest.add(test01);
        listOfTest.add(test02);
        listOfTest.add(test03);
        listOfTest.add(test04);

        return listOfTest;
    }
}
