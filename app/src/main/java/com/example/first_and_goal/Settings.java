package com.example.first_and_goal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class Settings extends Fragment {
    private Button signout, remove;
    private TextView email;
    private EditText oldEmail, password, newpassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_settings, container, false);

        auth =FirebaseAuth.getInstance();
        signout = (Button) RootView.findViewById(R.id.sign_out);
        signout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                FirebaseAuth.getInstance().signOut();
            }
        });

        auth=FirebaseAuth.getInstance();
        email = (TextView) RootView.findViewById(R.id.useremail);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(user);



        authListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Intent intent = new Intent(getActivity(), login.class);
                    startActivity(intent);
                }
            }
        };


        //btnChangePassword = (Button) findViewById(R.id.change_password_button);
        // btnRemoveUser = (Button) findViewById(R.id.remove_user_button);
        //changePassword = (Button) findViewById(R.id.changepass);
        remove = (Button) RootView.findViewById(R.id.remove);
        signout = (Button) RootView.findViewById(R.id.sign_out);
        oldEmail = (EditText) RootView.findViewById(R.id.old_email);
        password = (EditText) RootView.findViewById(R.id.password);
        newpassword = (EditText) RootView.findViewById(R.id.newPassword);

        oldEmail.setVisibility(View.GONE);
        password.setVisibility(View.GONE);
        newpassword.setVisibility(View.GONE);
        // changePassword.setVisibility(View.GONE);
        remove.setVisibility(View.GONE);

        progressBar = (ProgressBar) RootView.findViewById(R.id.progressBar);

        if (progressBar != null){
            progressBar.setVisibility(View.GONE);
        }


        //  btnChangePassword.setOnClickListener(new View.OnClickListener(){
        //    @Override
        //   public void onClick (View v){
        //     oldEmail.setVisibility(View.GONE);
        //   password.setVisibility(View.VISIBLE);
        // changePassword.setVisibility(View.VISIBLE);
        // remove.setVisibility(View.GONE);
        // }
        // });

        //changePassword.setOnClickListener(new View.OnClickListener(){
        //  @Override
        //public void onClick (View v) {
        //  progressBar.setVisibility(View.VISIBLE);
        // if (user != null && !newpassword.getText().toString().trim().equals("")) {
        //   if (newpassword.getText().toString().trim().length() < 6) {
        //     newpassword.setError("Passwrod too short, enter minimum 6 characters");
        //   progressBar.setVisibility(View.GONE);
        // } else {
        //   user.updatePassword(newpassword.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
        //     @Override
        //   public void onComplete(@NonNull Task<Void> task) {
        //     if (task.isSuccessful()) {
        //       Toast.makeText(getActivity(), "Password has been updated! Please sign in with new password", Toast.LENGTH_SHORT).show();
        //     signout();
        //   progressBar.setVisibility(View.GONE);
        // } else {
        //   Toast.makeText(getActivity(), "Failed to update", Toast.LENGTH_SHORT).show();
        //  }

        // }
        //});
        // }
        // } else if (newpassword.getText().toString().trim().equals("")){
        //   newpassword.setError("Enter Password");
        // progressBar.setVisibility(View.GONE);
        //    }
        //  }
        //});

        // btnRemoveUser.setOnClickListener(new View.OnClickListener(){
        //   @Override
        //   public void onClick (View v) {
        //     progressBar.setVisibility(View.VISIBLE);
        //   if (user != null) {
        //      user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
        //        @Override
        //      public void onComplete(@NonNull Task<Void> task) {
        //        if (task.isSuccessful()) {
        //          Toast.makeText(getActivity(), "Your profile has been deleted", Toast.LENGTH_SHORT).show();
        //        Intent intent = new Intent(getActivity(), splashscreen.class);
        //      startActivity(intent);
        //    progressBar.setVisibility(View.GONE);
        //  } else {
        //    Toast.makeText(getActivity(), "Failed to delete account", Toast.LENGTH_SHORT).show();
        //  progressBar.setVisibility(View.GONE);
        //  }

        //   }
        //});
        // }
        // }
        //});
        signout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                signout();
            }
        });
        return RootView;
    }

    private void setDataToView (@NonNull FirebaseUser user){
        email.setText("User Email: " + user.getEmail());
    }

    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null){
                Intent intent = new Intent(getActivity(), login.class);
                startActivity(intent);
            } else {
                setDataToView(user);
            }
        }
    };
    public void signout (){
        auth.signOut();

        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Intent intent = new Intent(getActivity(), login.class);

                    startActivity(intent);
                    Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }



    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop (){
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}



