package com.example.start_it_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityCheck extends AppCompatActivity {

    private Button startUpLogin, investorLogin, guestLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        startUpLogin = findViewById(R.id.IdStartUpLogin);
        investorLogin = findViewById(R.id.IdInvestorLogin);
        guestLogin = findViewById(R.id.IdGuestLogin);

        startUpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCheck.this, LoginActivity.class);
                intent.putExtra("StartUp", "1");
                startActivity(intent);
            }
        });

        investorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCheck.this, LoginActivity.class);
                intent.putExtra("Investor", "2");
                startActivity(intent);
            }
        });
    }
}