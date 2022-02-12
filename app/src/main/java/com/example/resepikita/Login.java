package com.example.resepikita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Button login ,register;
    TextInputLayout email_log, password_log;

    User user= new User();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        //button
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        email_log = findViewById(R.id.email_log);
        password_log = findViewById(R.id.password_log);
    }
    public void register(View v){
        Intent intent = new Intent(Login.this ,Register.class);
        startActivity(intent);
        finish();
    }

    public void login(View view){
        if ( validateEmail() && validatePassword() == true)
        {
            user.setEmail(email_log.getEditText().getText().toString());
            user.setPassword(password_log.getEditText().getText().toString());

            mAuth.signInWithEmailAndPassword(user.getEmail(),user.getPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                Toast.makeText(Login.this, "dah jadi",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this , Dashboard.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(Login.this, "tak jadi",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
        else{return;
        }
    }
    public boolean validateEmail(){
        String val = email_log.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()){
            email_log.setError("field cannot be empty");
            return false;
        }
        else if (!val.matches(emailPattern)){
            email_log.setError("Invalid email address");
            return false;
        }
        else {
            email_log.setError(null);
            email_log.setErrorEnabled(false);
            return true;
        }
    }
    public boolean validatePassword(){
        String val = password_log.getEditText().getText().toString();
        if (val.isEmpty()){
            password_log.setError("field cannot be empty");
            return false;
        }
        else if (!(val.length() ==8)){
            password_log.setError("password length is 8 character");
            return false;
        }
        else {
            password_log.setError(null);
            password_log.setErrorEnabled(false);
            return true;
        }
    }

}