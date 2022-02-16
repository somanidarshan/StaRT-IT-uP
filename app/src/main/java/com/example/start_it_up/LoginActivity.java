package com.example.start_it_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText EdtEmail, EdtPassword;
    Button Login, Register;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EdtEmail = findViewById(R.id.IdEmail);
        EdtPassword = findViewById(R.id.IdPassword);
        Login = findViewById(R.id.IdLogin);
        Register = findViewById(R.id.IdRegister);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserAccount();
            }
        });

        String StartUp = getIntent().getStringExtra("StartUp");
        String Investor = getIntent().getStringExtra("Investor");

       Register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(StartUp.equals("1")){
                  Intent intent1=new Intent(LoginActivity.this,activity_startup_signup.class);
                  startActivity(intent1);
               }
               else if(Investor.equals("2")){
                   Intent intent2=new Intent(LoginActivity.this,activity_invester_signup.class);
                   startActivity(intent2);
               }
           }
       });
    }

    private void loginUserAccount(){
        String Email = EdtEmail.getText().toString();
        String Password = EdtPassword.getText().toString();

//        Log.e(Email, "email");
//        Log.e(Password,"password");

        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(getApplicationContext(),"Please enter Email!!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(Password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!!",Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Login failed!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}