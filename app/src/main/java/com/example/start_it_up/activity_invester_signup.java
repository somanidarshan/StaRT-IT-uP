package com.example.start_it_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class activity_invester_signup extends AppCompatActivity {

    private EditText invester_name;
    private EditText invester_email;
    private EditText invester_phone;
    private EditText invester_domain;
    private EditText invester_address;
    private EditText invester_password;
    private Uri resultUri;
    private ProgressDialog loader;
    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invester_signup);

        invester_name=findViewById(R.id.inv_name);
        invester_phone=findViewById(R.id.inv_phoneno);
        invester_email=findViewById(R.id.inv_email);
        invester_domain=findViewById(R.id.inv_domain);
        invester_address=findViewById(R.id.inv_address);
        invester_password=findViewById(R.id.inv_password);
        Button invester_register = findViewById(R.id.inv_register);
        loader=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        invester_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name=invester_name.getText().toString().trim();
                final String phoneno=invester_phone.getText().toString().trim();
                final String email=invester_email.getText().toString().trim();
                final String domain=invester_domain.getText().toString().trim();
                final String address=invester_address.getText().toString().trim();
                final String password=invester_password.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    invester_name.setError("Name is Required");
                    return;
                }
                if(TextUtils.isEmpty(phoneno)){
                    invester_phone.setError("Contact is Required");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    invester_email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(domain)){
                    invester_domain.setError("Domain is Required");
                    return;
                }
                if(TextUtils.isEmpty(address)){
                    invester_address.setError("Address is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    invester_address.setError("Password is Required");
                    return;
                }
                else {
                    loader.setMessage("Registering You..");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                String error=task.getException().toString();
                                Toast.makeText(activity_invester_signup.this, "Error "+error, Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String currentUserId=mAuth.getCurrentUser().getUid();
                                userDatabaseRef= FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
                                HashMap userInfo = new HashMap();
                                userInfo.put("id",currentUserId);
                                userInfo.put("Name",name);
                                userInfo.put("Phoneno",phoneno);
                                userInfo.put("Email",email);
                                userInfo.put("Domain",domain);
                                userInfo.put("Address",address);
                                userInfo.put("Password",password);

                                userDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(activity_invester_signup.this, "Data set Successfully", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(activity_invester_signup.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }

                                        finish();
                                        loader.dismiss();
                                    }
                                });



                            }
                        }
                    });
                }
            }
        });
    }
}