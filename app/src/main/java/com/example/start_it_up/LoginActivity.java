package com.example.start_it_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    EditText UserName, Password;
    Button Login, Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserName = findViewById(R.id.IdUserName);
        Password = findViewById(R.id.IdPassword);
        Login = findViewById(R.id.IdLogin);
        Register = findViewById(R.id.IdRegister);

        Intent intent = new Intent();
        String StartUp = getIntent().getStringExtra("StartUp");
        String Investor = getIntent().getStringExtra("Investor");

       Register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(StartUp!=null){
                  // Intent intent1=new Intent(LoginActivity.this,)
               }
           }
       });
    }
}