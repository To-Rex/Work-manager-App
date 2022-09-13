package com.yorvoration.workmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    FirebaseFirestore db;
    private FirebaseAuth auth;
    private SqlData MyDb;

    Intent intent;
    private Button btnlogregister,btnsubmit,btnlogrol;
    private EditText ediregemail,edilogpass;
    private TextView txtresetpassword;
    private ProgressBar progressBarlog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);


        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        MyDb = new SqlData(this);

        btnlogregister = findViewById(R.id.btnlogregister);
        btnsubmit = findViewById(R.id.btnsubmit);
        btnlogrol = findViewById(R.id.btnregrol);
        progressBarlog = findViewById(R.id.progressBarlog);

        ediregemail = findViewById(R.id.ediregemail);
        edilogpass = findViewById(R.id.ediregpass);

        txtresetpassword = findViewById(R.id.txtresetpassword);

        progressBarlog.setVisibility(View.GONE);
        btnlogregister.setOnClickListener(view -> {
            intent = new Intent(Login.this,Regstaration.class);
            startActivity(intent);
        });
        btnsubmit.setOnClickListener(view -> {
            progressBarlog.setVisibility(View.VISIBLE);
            String email = ediregemail.getText().toString();
            final String password = edilogpass.getText().toString();

            if (TextUtils.isEmpty(email)) {
                ediregemail.setError(getString(R.string.bosh));
                return;
            }

            if (TextUtils.isEmpty(password)) {
                edilogpass.setError(getString(R.string.bosh));
                return;
            }
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Login.this, task -> {
                        if (!task.isSuccessful()) {
                            if (password.length() < 6) {
                                progressBarlog.setVisibility(View.GONE);
                                edilogpass.setError("kam");
                            } else {
                                progressBarlog.setVisibility(View.GONE);
                                edilogpass.setError(getString(R.string.xatolik));
                                ediregemail.setError(getString(R.string.xatolik));
                                Toast.makeText(Login.this, getString(R.string.xatolik), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            if (password.length() < 6) {
                                edilogpass.setError("kam");
                            } else {
                                String userid1 = auth.getCurrentUser().getUid();
                                //textView2.setText(userid1);
                                String til = "en";
                                String rejim = "rejim";
                                String kalit = "1";
                                String parol = edilogpass.getText().toString();
                                Boolean result = MyDb.kiritish(userid1, til, rejim, kalit, parol);
                                progressBarlog.setVisibility(View.GONE);
                                if (result == true) {
                                    progressBarlog.setVisibility(View.GONE);
                                    intent = new Intent(Login.this, Sample.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    progressBarlog.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), getString(R.string.xatolik), Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });
        });
    }
}
