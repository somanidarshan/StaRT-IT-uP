package com.example.start_it_up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class activity_startup_signup extends AppCompatActivity {

    private EditText startup_name;
    private EditText startup_email;
    private EditText startup_phone;
    private EditText startup_domain;
    private EditText startup_address;
    private EditText startup_password;
    private EditText startup_Description;
    private Button startup_register;
    private RadioButton radiobutton_yes;
    private RadioButton radiobutton_no;
    private ProgressDialog loader;
    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_signup);

        startup_name=findViewById(R.id.stp_name);
        startup_phone=findViewById(R.id.stp_phoneno);
        startup_email=findViewById(R.id.stp_email);
        startup_domain=findViewById(R.id.stp_domain);
        startup_address=findViewById(R.id.stp_address);
        startup_password=findViewById(R.id.stp_password);
        startup_Description=findViewById(R.id.stp_description);
        startup_register=findViewById(R.id.stp_register);
        radiobutton_yes=findViewById(R.id.yes_checkbox);
        radiobutton_no=findViewById(R.id.no_checkbox);


        loader=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        startup_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checked="no";

                if(radiobutton_yes.isChecked()){
                    checked="yes";
                }

                final String name=startup_name.getText().toString().trim();
                final String phoneno=startup_phone.getText().toString().trim();
                final String email=startup_email.getText().toString().trim();
                final String domain=startup_domain.getText().toString().trim();
                final String address=startup_address.getText().toString().trim();
                final String password=startup_password.getText().toString().trim();
                final String description=startup_Description.getText().toString().trim();

                if(Patterns.EMAIL_ADDRESS.matcher(email).matches() && !email.isEmpty()){
                    Toast.makeText(activity_startup_signup.this,"Email Verified.",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(activity_startup_signup.this,"Email not Verified.",Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(name)){
                    startup_name.setError("Name is Required");
                    return;
                }
                if(TextUtils.isEmpty(phoneno)){
                    startup_phone.setError("Contact is Required");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    startup_email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(domain)){
                    startup_domain.setError("Domain is Required");
                    return;
                }
                if(TextUtils.isEmpty(address)){
                    startup_address.setError("Address is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    startup_password.setError("Password is Required");
                    return;
                }
                if(TextUtils.isEmpty(description)){
                    startup_Description.setError("Description is necessary");
                    return;
                }
                else {
                    loader.setMessage("Registering You..");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    String finalChecked = checked;
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                                String error=task.getException().toString();
                                Toast.makeText(activity_startup_signup.this, "Error "+error, Toast.LENGTH_SHORT).show();
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
                                userInfo.put("Patent", finalChecked);
                                userInfo.put("Description",description);

                                userDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(activity_startup_signup.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(activity_startup_signup.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
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