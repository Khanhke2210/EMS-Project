package com.example.emsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Controller.DataManager;
import Model.Question;
import Model.Tests;

public class TestActivity extends AppCompatActivity {
    private TextView tvwTest_Name, tvwTest_ID, tvwTest_Company, tvwTest_Question, tvwTest_Description, tvwTest_Result;
    private EditText edtTest_Answer;
    private Button btnTest_Start, btnTest_OptionA, btnTest_OptionB, btnTest_OptionC, btnTest_OptionD, btnTest_Previous, btnTest_Next, btnTest_Submit;
    private DataManager dataManager;
    private Tests selectedTest;
    private RelativeLayout rltTest_Start, rltTest_In;
    private ImageView imvTestLogo;
    private List<Question> listOfQuestion;
    private Map<Integer, String> answerMap;
    private int currentPosition;
    private double total;
    private boolean containF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ConstraintGUI();
        SetUpInformation();
        ActionListener();
    }

    private void ConstraintGUI() {
        tvwTest_Name = (TextView) findViewById(R.id.tvwDetailName);
        tvwTest_ID = (TextView) findViewById(R.id.tvwDetailID);
        tvwTest_Company = (TextView) findViewById(R.id.tvwDetailCompany);
        tvwTest_Question = (TextView) findViewById(R.id.tvwTestQuestion);
        tvwTest_Description = (TextView) findViewById(R.id.tvwTestDescription);
        tvwTest_Result = (TextView) findViewById(R.id.tvwTestResult);
        edtTest_Answer = (EditText) findViewById(R.id.edtTestAnswer);
        btnTest_Start = (Button) findViewById(R.id.btnTestStart);
        btnTest_Previous = (Button) findViewById(R.id.btnTestPrevious);
        btnTest_Next = (Button) findViewById(R.id.btnTestNext);
        btnTest_OptionA = (Button) findViewById(R.id.btnTestA);
        btnTest_OptionB = (Button) findViewById(R.id.btnTestB);
        btnTest_OptionC = (Button) findViewById(R.id.btnTestC);
        btnTest_OptionD = (Button) findViewById(R.id.btnTestD);
        btnTest_Submit = (Button) findViewById(R.id.btnTestFinish);
        rltTest_Start = (RelativeLayout) findViewById(R.id.rltTestStart);
        rltTest_In = (RelativeLayout) findViewById(R.id.rltTestIn);
        imvTestLogo = (ImageView) findViewById(R.id.imvTestLogo);
        dataManager = new DataManager();
        answerMap = new HashMap<>();
        containF = false;
        rltTest_In.setVisibility(View.GONE);
        imvTestLogo.setVisibility(View.VISIBLE);
        rltTest_Start.setVisibility(View.VISIBLE);
    }

    private void SetUpInformation() {
        Intent intent = getIntent();
        int index = Integer.valueOf(intent.getStringExtra("TestId"));
        List<Tests> listOfTest = dataManager.GetListOfTest();
        selectedTest = listOfTest.get(index);
        listOfQuestion = selectedTest.getListOfQuestion();
        tvwTest_Name.setText(selectedTest.getName());
        tvwTest_ID.setText("ID : " + selectedTest.getId());
        tvwTest_Company.setText("Company : " + selectedTest.getCompany());
        currentPosition = 0;
        total = 0;
        answerLoadout();
        LoadQuestion();
    }

    private void answerLoadout() {
        for(int i = 0; i < listOfQuestion.size(); i++) {
            answerMap.put(i, "No");
        }
    }

    private void ActionListener() {
        btnTest_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rltTest_In.setVisibility(View.VISIBLE);
                imvTestLogo.setVisibility(View.GONE);
                rltTest_Start.setVisibility(View.GONE);
            }
        });

        btnTest_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPosition++;
                LoadQuestion();
            }
        });

        btnTest_Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPosition--;
                LoadQuestion();
            }
        });

        btnTest_OptionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTest_OptionA.setBackgroundColor(Color.parseColor("#ADD8E6"));
                answerMap.put(currentPosition, "A");
            }
        });

        btnTest_OptionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTest_OptionB.setBackgroundColor(Color.parseColor("#ADD8E6"));
                answerMap.put(currentPosition, "B");
            }
        });

        btnTest_OptionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTest_OptionC.setBackgroundColor(Color.parseColor("#ADD8E6"));
                answerMap.put(currentPosition, "C");
            }
        });

        btnTest_OptionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTest_OptionD.setBackgroundColor(Color.parseColor("#ADD8E6"));
                answerMap.put(currentPosition, "D");
            }
        });

        btnTest_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadResult();
            }
        });
    }

    private void LoadResult() {
        double percentage = 0;
        for(int i = 0; i < listOfQuestion.size(); i++) {
            percentage += listOfQuestion.get(i).getPoint();
            if(answerMap.get(i).equalsIgnoreCase(listOfQuestion.get(i).getAnswer())){
                total += listOfQuestion.get(i).getPoint();
            }
        }

        rltTest_In.setVisibility(View.GONE);
        btnTest_Start.setVisibility(View.GONE);
        rltTest_Start.setVisibility(View.VISIBLE);
        imvTestLogo.setVisibility(View.VISIBLE);
        if(containF) {
            tvwTest_Result.setTextColor(Color.BLUE);
            tvwTest_Result.setText("Result will be sending to your email");
        } else {
            tvwTest_Result.setText(total + "/" + percentage);

            if (total < percentage / 2) {
                tvwTest_Result.setTextColor(Color.RED);
            }

            if (total == percentage / 2) {
                tvwTest_Result.setTextColor(Color.BLUE);
            }

            if (total > percentage / 2) {
                tvwTest_Result.setTextColor(Color.GREEN);
            }
        }
    }

    private void LoadQuestion() {
        OptionBackgroundLoadout();
        btnTest_Previous.setVisibility(View.VISIBLE);
        btnTest_Next.setVisibility(View.VISIBLE);
        btnTest_Next.setText("Next");

        if(currentPosition == 0) {
            btnTest_Previous.setVisibility(View.GONE);
        }

        if(currentPosition == selectedTest.getListOfQuestion().size() - 1) {
            btnTest_Next.setVisibility(View.GONE);
        }


        if(selectedTest.getListOfQuestion().get(currentPosition).getType().equals("M")) {
           SetVisibleOption();
            edtTest_Answer.setVisibility(View.GONE);
            btnTest_OptionA.setText(listOfQuestion.get(currentPosition).getOptionA());
            btnTest_OptionB.setText(listOfQuestion.get(currentPosition).getOptionB());
            btnTest_OptionC.setText(listOfQuestion.get(currentPosition).getOptionC());
            btnTest_OptionD.setText(listOfQuestion.get(currentPosition).getOptionD());
        } else {
            containF = true;
            SetGoneOption();
            edtTest_Answer.setVisibility(View.VISIBLE);
        }
        tvwTest_Description.setText(selectedTest.getListOfQuestion().get(currentPosition).getQuestContent());
        tvwTest_Question.setText("Question : " + (currentPosition + 1));
    }

    private void OptionBackgroundLoadout() {
        String answer = answerMap.get(currentPosition);
        if(answer.equals("No")) {
            ResetOptionBackground();
        }
        if(answer.equals("A")) {
            btnTest_OptionA.setBackgroundColor(Color.parseColor("#ADD8E6"));
            btnTest_OptionB.setBackgroundColor(Color.BLACK);
            btnTest_OptionC.setBackgroundColor(Color.BLACK);
            btnTest_OptionD.setBackgroundColor(Color.BLACK);
        }
        if(answer.equals("B")) {
            btnTest_OptionB.setBackgroundColor(Color.parseColor("#ADD8E6"));
            btnTest_OptionA.setBackgroundColor(Color.BLACK);
            btnTest_OptionC.setBackgroundColor(Color.BLACK);
            btnTest_OptionD.setBackgroundColor(Color.BLACK);
        }
        if(answer.equals("C")) {
            btnTest_OptionC.setBackgroundColor(Color.parseColor("#ADD8E6"));
            btnTest_OptionB.setBackgroundColor(Color.BLACK);
            btnTest_OptionA.setBackgroundColor(Color.BLACK);
            btnTest_OptionD.setBackgroundColor(Color.BLACK);
        }
        if(answer.equals("D")) {
            btnTest_OptionD.setBackgroundColor(Color.parseColor("#ADD8E6"));
            btnTest_OptionB.setBackgroundColor(Color.BLACK);
            btnTest_OptionC.setBackgroundColor(Color.BLACK);
            btnTest_OptionA.setBackgroundColor(Color.BLACK);
        }
    }

    private void ResetOptionBackground() {
        btnTest_OptionA.setBackgroundColor(Color.BLACK);
        btnTest_OptionB.setBackgroundColor(Color.BLACK);
        btnTest_OptionC.setBackgroundColor(Color.BLACK);
        btnTest_OptionD.setBackgroundColor(Color.BLACK);
    }

    private void SetVisibleOption() {
        btnTest_OptionA.setVisibility(View.VISIBLE);
        btnTest_OptionB.setVisibility(View.VISIBLE);
        btnTest_OptionC.setVisibility(View.VISIBLE);
        btnTest_OptionD.setVisibility(View.VISIBLE);
    }

    private void SetGoneOption() {
        btnTest_OptionA.setVisibility(View.GONE);
        btnTest_OptionB.setVisibility(View.GONE);
        btnTest_OptionC.setVisibility(View.GONE);
        btnTest_OptionD.setVisibility(View.GONE);
    }

    private int GetResult() {
        return 1;
    }
}

