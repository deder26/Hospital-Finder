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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity {

    private EditText name,email,password;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = (EditText) findViewById(R.id.userNameET);
        email = (EditText) findViewById(R.id.emailET);
        password = (EditText) findViewById(R.id.passET);
        progressBar = (ProgressBar) findViewById(R.id.progressPB);

        mAuth = FirebaseAuth.getInstance();


    }

    public void SignUp(View view) {
        final String uName = name.getText().toString();
        final String uEmail = email.getText().toString().trim();

        String uPass = password.getText().toString().trim();

        if(uName.isEmpty())
        {
            name.setError("This Field Can't Be Empty");
        }
        else if(uEmail.isEmpty())
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
            mAuth.createUserWithEmailAndPassword(uEmail, uPass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                final String role = "user";
                                User user = new User(uName,uEmail,role);
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Intent intent = new Intent(SignUpActivity.this,UserActivity.class);
                                            Toast.makeText(getApplicationContext(),"Successfully Sign Up",Toast.LENGTH_SHORT).show();
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });

                            } else {
                                if(task.getException() instanceof FirebaseAuthUserCollisionException)
                                {
                                    Toast.makeText(getApplicationContext(),"Already Registered",Toast.LENGTH_SHORT).show();
                                }
                                else Toast.makeText(getApplicationContext(),"Please Try Again",Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }

    }

    public void SignIn(View view) {
        Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
