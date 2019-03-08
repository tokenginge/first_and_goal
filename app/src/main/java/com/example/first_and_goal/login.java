package com.example.first_and_goal;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;


public class login extends AppCompatActivity {

    private TextInputLayout inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null){
            startActivity(new Intent(login.this, home.class));
            finish();
        }


        setContentView(R.layout.activity_login);
        inputEmail =  findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar =  findViewById(R.id.progressBar);
        btnSignup =  findViewById(R.id.btn_signup);
        btnLogin =  findViewById(R.id.btn_login);
        btnReset = findViewById(R.id.btn_reset_password);


        auth = FirebaseAuth.getInstance();


        btnSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                startActivity (new Intent (login.this, signup.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                startActivity(new Intent (login.this, forgotpassword.class));

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                final String email = inputEmail.getEditText().getText().toString().trim();
                final String password = inputPassword.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()){
                            if (password.length()< 6){
                                inputPassword.setError("Password too short, enter minimum 6 characters");
                            } else if  (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                inputEmail.setError("PLease enter valid email"); }

                            else {


                                Toast.makeText(login.this, "Authentication failed, check your email and password", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            Intent intent = new Intent(login.this, home.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
        });

    }
}
