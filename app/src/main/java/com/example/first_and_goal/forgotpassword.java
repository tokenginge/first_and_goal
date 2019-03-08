package com.example.first_and_goal;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import android.os.Bundle;
import java.util.regex.Pattern;

public class forgotpassword extends AppCompatActivity {

    private TextInputLayout inputEmail;
    private Button btnReset, btnBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);



        inputEmail = (TextInputLayout)findViewById(R.id.email);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnBack = (Button) findViewById(R.id.btn_back);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        auth=FirebaseAuth.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                final String email = inputEmail.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(forgotpassword.this, "We have sent you how to reset your password", Toast.LENGTH_SHORT).show();
                        } else if  (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            inputEmail.setError("PLease enter valid email"); }
                        else {
                            Toast.makeText(forgotpassword.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);

                    }
                });
            }
        });



    }

}