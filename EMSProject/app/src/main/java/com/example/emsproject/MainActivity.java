package com.example.emsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnMain_Register, btnMain_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void ConstraintsGUI() {
        btnMain_Login = (Button) findViewById(R.id.btnMainLogin);
        btnMain_Register = (Button) findViewById(R.id.btnMainRegister);
    }

    private void ActionListener() {
        btnMain_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLogin();
            }
        });

        btnMain_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRegister();
            }
        });
    }

    private void toLogin() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    private void toRegister() {
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
    }
}