package com.yorvoration.workmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import java.util.concurrent.Executor;

public class Password extends AppCompatActivity {

    DocumentReference documentReference;
    FirebaseFirestore db;
    private FirebaseAuth auth;
    private SqlData MyDb;
    Intent intent;
    Handler handler;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private TextView txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt0;
    private View pas1,pas2,pas3,pas4,pas5,pas6;
    ImageView imgpasback,imgfingerptint;
    private EditText edipasswordall;
    String password = "0", textparol, a;
    String UID, TIL, REJIM, KALIT, PAROL;
    int soni = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.password);

        executor = ContextCompat.getMainExecutor(this);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        MyDb = new SqlData(this);

        txt0 = findViewById(R.id.txt0);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);
        txt6 = findViewById(R.id.txt6);
        txt7 = findViewById(R.id.txt7);
        txt8 = findViewById(R.id.txt8);
        txt9 = findViewById(R.id.txt9);

        pas1 = findViewById(R.id.pas1);
        pas2 = findViewById(R.id.pas2);
        pas3 = findViewById(R.id.pas3);
        pas4 = findViewById(R.id.pas4);
        pas5 = findViewById(R.id.pas5);
        pas6 = findViewById(R.id.pas6);

        edipasswordall = findViewById(R.id.edipasswordall);
        imgfingerptint = findViewById(R.id.imgfingerptint);
        imgpasback = findViewById(R.id.imgpasback);
        readdatasql();

        biometricPrompt = new BiometricPrompt(Password.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                //imgparfinger.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                pas1.setBackgroundResource(R.drawable.shadov_soucsess);
                pas2.setBackgroundResource(R.drawable.shadov_soucsess);
                pas3.setBackgroundResource(R.drawable.shadov_soucsess);
                pas4.setBackgroundResource(R.drawable.shadov_soucsess);
                pas5.setBackgroundResource(R.drawable.shadov_soucsess);
                pas6.setBackgroundResource(R.drawable.shadov_soucsess);
                intent = new Intent(Password.this, Sample.class);
                startActivity(intent);
                finish();
            }
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                pas1.setBackgroundResource(R.drawable.shadovfaild);
                pas2.setBackgroundResource(R.drawable.shadovfaild);
                pas3.setBackgroundResource(R.drawable.shadovfaild);
                pas4.setBackgroundResource(R.drawable.shadovfaild);
                pas5.setBackgroundResource(R.drawable.shadovfaild);
                pas6.setBackgroundResource(R.drawable.shadovfaild);
                Toast.makeText(Password.this, "Authentication failed...!", Toast.LENGTH_SHORT).show();
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication")
                .setSubtitle("Login using fingerprint authentication")
                .setNegativeButtonText("User App Password")
                .build();
        handler = new Handler();
        handler.postDelayed(() -> biometricPrompt.authenticate(promptInfo), 700);
        imgfingerptint.setOnClickListener(v -> biometricPrompt.authenticate(promptInfo));

        InputFilter filter = (source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                if (!Character.toString(source.charAt(i)).matches("[a-zA-Z0-9-_.]+")) {
                    return "";
                }
            }
            return null;
        };

        edipasswordall.setFilters(new InputFilter[]{filter});
        edipasswordall.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                textparol = edipasswordall.getText().toString().trim();
                if (soni == 1){
                    pas1.setBackgroundResource(R.drawable.shadov1);
                }
                if (soni == 2){
                    pas2.setBackgroundResource(R.drawable.shadov1);
                }
                if (soni == 3){
                    pas3.setBackgroundResource(R.drawable.shadov1);
                }
                if (soni == 4){
                    pas4.setBackgroundResource(R.drawable.shadov1);
                }
                if (soni == 5){
                    pas5.setBackgroundResource(R.drawable.shadov1);
                }
                if (soni == 6){
                    pas6.setBackgroundResource(R.drawable.shadov1);
                }
                int i = textparol.length();
                if (i == 6) {
                    if (Objects.equals(PAROL, textparol)) {
                        pas1.setBackgroundResource(R.drawable.shadov_soucsess);
                        pas2.setBackgroundResource(R.drawable.shadov_soucsess);
                        pas3.setBackgroundResource(R.drawable.shadov_soucsess);
                        pas4.setBackgroundResource(R.drawable.shadov_soucsess);
                        pas5.setBackgroundResource(R.drawable.shadov_soucsess);
                        pas6.setBackgroundResource(R.drawable.shadov_soucsess);
                        handler = new Handler();
                        handler.postDelayed(() -> {
                            edipasswordall.setText("");
                            intent = new Intent(Password.this, Sample.class);
                            startActivity(intent);
                            finish();
                        }, 1000);
                    } else {
                        pas1.setBackgroundResource(R.drawable.shadovfaild);
                        pas2.setBackgroundResource(R.drawable.shadovfaild);
                        pas3.setBackgroundResource(R.drawable.shadovfaild);
                        pas4.setBackgroundResource(R.drawable.shadovfaild);
                        pas5.setBackgroundResource(R.drawable.shadovfaild);
                        pas6.setBackgroundResource(R.drawable.shadovfaild);
                        edipasswordall.setText("");
                        handler = new Handler();
                        handler.postDelayed(() -> {
                            soni = 0;
                            edipasswordall.setText("");
                            pas1.setBackgroundResource(R.drawable.shadov);
                            pas2.setBackgroundResource(R.drawable.shadov);
                            pas3.setBackgroundResource(R.drawable.shadov);
                            pas4.setBackgroundResource(R.drawable.shadov);
                            pas5.setBackgroundResource(R.drawable.shadov);
                            pas6.setBackgroundResource(R.drawable.shadov);
                        }, 1000);
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        txt0.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                soni++;
                a = edipasswordall.getText().toString().trim();
                edipasswordall.setText(a + getString(R.string._0));
            }
        });
        txt1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                soni++;
                a = edipasswordall.getText().toString().trim();
                edipasswordall.setText(a + getString(R.string._1));
            }
        });
        txt2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                soni++;
                a = edipasswordall.getText().toString().trim();
                edipasswordall.setText(a + getString(R.string._2));
            }
        });
        txt3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                soni++;
                a = edipasswordall.getText().toString().trim();
                edipasswordall.setText(a + getString(R.string._3));
            }
        });
        txt4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                soni++;
                a = edipasswordall.getText().toString().trim();
                edipasswordall.setText(a + getString(R.string._4));
            }
        });
        txt5.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                soni++;
                a = edipasswordall.getText().toString().trim();
                edipasswordall.setText(a + getString(R.string._5));
            }
        });
        txt6.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                soni++;
                a = edipasswordall.getText().toString().trim();
                edipasswordall.setText(a + getString(R.string._6));
            }
        });
        txt7.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                soni++;
                a = edipasswordall.getText().toString().trim();
                edipasswordall.setText(a + getString(R.string._7));
            }
        });
        txt8.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                soni++;
                a = edipasswordall.getText().toString().trim();
                edipasswordall.setText(a + getString(R.string._8));
            }
        });
        txt9.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                soni++;
                a = edipasswordall.getText().toString().trim();
                edipasswordall.setText(a + getString(R.string._9));
            }
        });
    }
    private void readdatasql() {
        Cursor res = MyDb.oqish();
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        StringBuffer stringBuffer3 = new StringBuffer();
        StringBuffer stringBuffer4 = new StringBuffer();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer.append(res.getString(1));
                stringBuffer1.append(res.getString(2));
                stringBuffer2.append(res.getString(3));
                stringBuffer3.append(res.getString(4));
                stringBuffer4.append(res.getString(5));
            }
            UID = stringBuffer.toString();
            TIL = stringBuffer1.toString();
            REJIM = stringBuffer2.toString();
            KALIT = stringBuffer3.toString();
            PAROL = stringBuffer4.toString();
        }
    }
}
