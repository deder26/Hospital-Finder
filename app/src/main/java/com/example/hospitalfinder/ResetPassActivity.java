package com.example.hospitalfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassActivity extends AppCompatActivity {

    private EditText resetEmail;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        resetEmail = (EditText) findViewById(R.id.resetET);
        progressBar = (ProgressBar) findViewById(R.id.pbID);
        mAuth = FirebaseAuth.getInstance();
    }

    public void ResetPass(View view) {
        String email = resetEmail.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            resetEmail.setError("Write a valid email");
        } else {

            progressBar.setVisibility(View.VISIBLE);

            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {

                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        finish();
                        Toast.makeText(getApplicationContext(), "Password is sent to your email", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getApplicationContext(), "Please Try Again", Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}
