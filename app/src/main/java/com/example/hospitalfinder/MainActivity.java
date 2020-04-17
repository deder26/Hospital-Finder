package com.example.hospitalfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText email,password;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (EditText) findViewById(R.id.emailET);
        password = (EditText) findViewById(R.id.passET);
        progressBar = (ProgressBar) findViewById(R.id.progressPB);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        if(mAuth.getCurrentUser() != null){
            finish();
            Intent intent = new Intent(MainActivity.this,UserActivity.class);
            startActivity(intent);
        }
        super.onStart();

    }

    public void LogIn(View view) {
        String uEmail = email.getText().toString().trim();
        String uPass = password.getText().toString().trim();

        if(uEmail.isEmpty())
        {
            email.setError("This Field Can't Be Empty");
        }
        else if(uPass.isEmpty())
        {
            password.setError("This Field Can't Be Empty");
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(uEmail).matches())
        {
            email.setError("Not A Valid Email");
        }
        else if(uPass.length()<6)
        {
            password.setError("Password should be at least 6 characters");
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(uEmail,uPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);

                    if(task.isSuccessful()){
                        finish();
                        Intent intent = new Intent(MainActivity.this,UserActivity.class);
                        startActivity(intent);
                    }
                    else Toast.makeText(getApplicationContext(),"Please try again",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void SignUp(View view) {
        Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
        startActivity(intent);
    }

    public void ResetPass(View view) {
        Intent intent = new Intent(MainActivity.this,ResetPassActivity.class);
        startActivity(intent);
    }
}
