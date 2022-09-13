package com.yorvoration.workmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Regstaration extends AppCompatActivity {

    DocumentReference documentReference;
    FirebaseFirestore db;
    private FirebaseAuth auth;
    private SqlData MyDb;
    Intent intent;
    private ProgressBar progressBarreg;
    private Button btnsubmit,btnregrol;
    private ImageView imgregmale,imgregfamale;
    private EditText ediregname,ediregsurname,ediregold,ediregphone,ediregpass,ediregemail;
    String name,surname,old,phone,password,email,userid;
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.regstartion);
        getSupportActionBar().hide();

        progressBarreg = findViewById(R.id.progressBarreg);
        btnsubmit = findViewById(R.id.btnsubmit);
        btnregrol = findViewById(R.id.btnregrol);

        imgregmale = findViewById(R.id.imgregmale);
        imgregfamale = findViewById(R.id.imgregfamale);

        ediregname = findViewById(R.id.ediregname);
        ediregsurname = findViewById(R.id.ediregsurname);
        ediregold = findViewById(R.id.ediregold);
        ediregphone = findViewById(R.id.ediregphone);
        ediregpass = findViewById(R.id.ediregpass);
        ediregemail = findViewById(R.id.ediregemail);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        MyDb = new SqlData(this);

        btnsubmit.setOnClickListener(view -> {
            name = ediregname.getText().toString().trim();
            surname = ediregsurname.getText().toString().trim();
            old = ediregold.getText().toString().trim();
            phone = ediregphone.getText().toString().trim();
            password = ediregpass.getText().toString().trim();
            email = ediregemail.getText().toString().trim();

            if (name.isEmpty()){
                ediregname.setError("bo`sh");
                return;
            }
            if (surname.isEmpty()){
                ediregsurname.setError("bo`sh");
                return;
            }
            if (old.isEmpty()){
                ediregold.setError("bo`sh");
                return;
            }
            if (phone.isEmpty()){
                ediregphone.setError("bo`sh");
                return;
            }
            if (password.isEmpty()){
                ediregpass.setError("bo`sh");
                return;
            }
            if (email.isEmpty()){
                ediregemail.setError("bo`sh");
                return;
            }
            if (password.length()<6){
                ediregpass.setError("xato");
                return;
            }
            if (name.length()<3){
                ediregname.setError("xato");
                return;
            }
            if (phone.length()<7){
                ediregphone.setError("xato");
                return;
            }
            fun();
        });

    }
    private void fun(){
        name = ediregname.getText().toString().trim();
        surname = ediregsurname.getText().toString().trim();
        old = ediregold.getText().toString().trim();
        phone = ediregphone.getText().toString().trim();
        password = ediregpass.getText().toString().trim();
        email = ediregemail.getText().toString().trim();
        if (name.isEmpty()){
            ediregname.setError("bo`sh");
            return;
        }
        if (surname.isEmpty()){
            ediregsurname.setError("bo`sh");
            return;
        }
        if (old.isEmpty()){
            ediregold.setError("bo`sh");
            return;
        }
        if (phone.isEmpty()){
            ediregphone.setError("bo`sh");
            return;
        }
        if (password.isEmpty()){
            ediregpass.setError("bo`sh");
            return;
        }
        if (email.isEmpty()){
            ediregemail.setError("bo`sh");
            return;
        }
        if (password.length()<6){
            ediregpass.setError("xato");
            return;
        }
        if (name.length()<3){
            ediregname.setError("xato");
            return;
        }
        if (phone.length()<7){
            ediregphone.setError("xato");
            return;
        }
        progressBarreg.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Regstaration.this, task -> {
                    if (!task.isSuccessful()) {
                        ediregemail.setError("foydalanuvchi" +"mavjud");
                    } else {
                        //sendVerificationEmail();
                        userid = auth.getCurrentUser().getUid();
                        int min = 10000;
                        int max = 99999;
                        int diff = max - min;
                        Random random = new Random();
                        int i = random.nextInt(diff + 1);
                        i += min;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss", Locale.getDefault());
                        String time = sdf.format(new Date());
                        String son = String.valueOf(i);
                        String ism1 = ediregname.getText().toString();
                        String familya = ediregsurname.getText().toString();
                        String parool = ediregpass.getText().toString();
                        String tel = ediregphone.getText().toString();
                        Map<String, Object> user1 = new HashMap<>();
                        user1.put("ISMI", ism1);
                        user1.put("FAMILYA", familya);
                        user1.put("PAROL", parool);
                        user1.put("POCHTA", email);
                        user1.put("TEL", tel);
                        user1.put("YOSHI", old);
                        user1.put("ASTROCOIN", "0");
                        user1.put("DARAJA", "0");
                        user1.put("USERID", son);
                        user1.put("UID", userid);
                        user1.put("PHOTO", "0");
                        user1.put("SONGIFAOLLIK", time);
                        user1.put("KIRGANVAQT", time);
                        String til = "en";
                        String rejim = "rejim";
                        String kalit = "1";
                        Boolean result = MyDb.kiritish(userid, til, rejim, kalit, parool);
                        db.collection("User").document(userid).set(user1)
                                .addOnSuccessListener(unused -> {
                                }).addOnFailureListener(e -> progressBarreg.setVisibility(View.GONE));
                        String username = ism1 + " - " + familya;
                        Map<String, Object> mappul = new HashMap<>();
                        mappul.put("KARTA", "0");
                        mappul.put("ASTROCOIN", "O'tkazmalar");
                        mappul.put("TOLANMADI", "0");
                        mappul.put("OTKAZMA", "0");
                        mappul.put("VAQT", "0");
                        mappul.put("USER", username);
                        db.collection("otkazma").document(userid).set(mappul)
                                .addOnSuccessListener(unused -> progressBarreg.setVisibility(View.GONE)).addOnFailureListener(e -> progressBarreg.setVisibility(View.GONE));
                        progressBarreg.setVisibility(View.GONE);
                        startActivity(new Intent(Regstaration.this, Sample.class));
                        finish();
                    }
                }).addOnFailureListener(e -> progressBarreg.setVisibility(View.GONE));
    }
}
