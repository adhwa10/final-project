package com.example.resepikita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Register extends AppCompatActivity {
    private Button sign, register;
    private TextInputLayout fullname_reg,email_reg,phonenum_reg,password_reg;

    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    User user= new User();

    private String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        //button
        sign = findViewById(R.id.sign);
        register = findViewById(R.id.register);

        //edit text
        fullname_reg = findViewById(R.id.fullname_reg);
        email_reg = findViewById(R.id.email_reg);
        phonenum_reg = findViewById(R.id.phonenum_reg);
        password_reg = findViewById(R.id.password_reg);

        sign.setOnClickListener(view -> {
            if (validateFullname()  && validateEmail() && validatePhoneNum() && validatePassword() == true)
            {
                RegisterUser();
            }
            else{return;
            }
        });
    }
    public boolean validateFullname(){
        String val = fullname_reg.getEditText().getText().toString();
        if (val.isEmpty()){
            fullname_reg.setError("field cannot be empty");
            return false;
        }
        else {
            fullname_reg.setError(null);
            fullname_reg.setErrorEnabled(false);
            return true;
        }
    }
    public boolean validateEmail(){
        String val = email_reg.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()){
            email_reg.setError("field cannot be empty");
            return false;
        }
        else if (!val.matches(emailPattern)){
            email_reg.setError("Invalid email address");
            return false;
        }
        else {
            email_reg.setError(null);
            email_reg.setErrorEnabled(false);
            return true;
        }
    }
    public boolean validatePhoneNum(){
        String val = phonenum_reg.getEditText().getText().toString();
        if (val.isEmpty()){
            phonenum_reg.setError("field cannot be empty");
            return false;
        }
        else {
            phonenum_reg.setError(null);
            phonenum_reg.setErrorEnabled(false);
            return true;
        }
    }
    public boolean validatePassword(){
        String val = password_reg.getEditText().getText().toString();
        if (val.isEmpty()){
            password_reg.setError("field cannot be empty");
            return false;
        }
        else if (!(val.length() ==8)){
            password_reg.setError("password length is 8 character");
            return false;
        }
        else {
            password_reg.setError(null);
            password_reg.setErrorEnabled(false);
            return true;
        }
    }

    private void RegisterUser() {
        user.setFullname(fullname_reg.getEditText().getText().toString());
        user.setEmail(email_reg.getEditText().getText().toString());
        user.setPhonenum(phonenum_reg.getEditText().getText().toString());
        user.setPassword(password_reg.getEditText().getText().toString());

        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        userid = task.getResult().getUser().getUid();
                        if(task.isSuccessful()){
                            db.collection("Users").document(userid)
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(Register.this, "dah jadi",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Register.this , Dashboard.class);
                                            startActivity(intent);
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "tak jadi",Toast.LENGTH_SHORT).show();

                    }
                });


    }

}