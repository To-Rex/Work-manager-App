package com.yorvoration.workmanager;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Sample extends AppCompatActivity {

    //https://www.youtube.com/watch?v=mHLgE3wooW4
    DocumentReference documentReference;
    FirebaseFirestore db;
    private SqlData MyDb;
    Intent intent;

    ImageView tstart;
    DrawerLayout drawerLayout;

    String id, uid, til, rejim, kalit, parol, parol1;
    String UID, TIL, REJIM, KALIT, PAROL;

    TextView txtnavid, txtnavcoin, txtnavuser, txtnavtel;
    ImageView imgsozdeuser;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample);
        Objects.requireNonNull(getSupportActionBar()).hide();

        db = FirebaseFirestore.getInstance();
        MyDb = new SqlData(this);

        tstart = findViewById(R.id.navstart);
        drawerLayout = findViewById(R.id.mylayout);
        View viewchiq = findViewById(R.id.viewchiq);
        View viewsettings = findViewById(R.id.viewsettings);
        View viewhot = findViewById(R.id.viewhot);
        View viewtop = findViewById(R.id.viewtop);
        View viewtelegram = findViewById(R.id.viewtelegram);
        View viewinstagram = findViewById(R.id.viewinstagram);
        ImageView imgdashboard = findViewById(R.id.imgdashboard);
        ImageView imgnewss = findViewById(R.id.imgfingerptint);
        ImageView imgkubik = findViewById(R.id.imgkubik);
        ImageView imguserr = findViewById(R.id.imguserr);
        txtnavid = findViewById(R.id.txtnavid);
        txtnavcoin = findViewById(R.id.txtnavcoin);
        txtnavuser = findViewById(R.id.txtnavuser);
        txtnavtel = findViewById(R.id.txtnavtel);
        imgsozdeuser = findViewById(R.id.imgsozdeuser);
        readdatasql();

        documentReference = db.document("User/" + UID);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String ism = documentSnapshot.getString("ISMI"),
                        id = documentSnapshot.getString("USERID"),
                        coin = documentSnapshot.getString("ASTROCOIN"),
                        tel1 = documentSnapshot.getString("TEL");
                txtnavuser.setText(ism);
                txtnavcoin.setText("AstroCoin " + coin);
                txtnavid.setText("id " + id);
                txtnavtel.setText(tel1);
            }
        });
        viewinstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.instagram.com/astrumuz/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                likeIng.setPackage("com.instagram.android");
                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/astrumuz/")));
                }
            }
        });
        viewtelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://t.me/astrumuz");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                likeIng.setPackage("com.telegram.android");
                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://t.me/astrumuz")));
                }
            }
        });
        viewhot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentHot fragmentHot = new FragmentHot();
                fragmentTransaction.replace(R.id.fragmentlayout, fragmentHot);
                fragmentTransaction.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        viewtop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragmenttop fragmenttop = new Fragmenttop();
                fragmentTransaction.replace(R.id.fragmentlayout, fragmenttop);
                fragmentTransaction.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        viewsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentSettings fragmentSettings = new FragmentSettings();
                fragmentTransaction.replace(R.id.fragmentlayout, fragmentSettings);
                fragmentTransaction.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        imgdashboard.setOnClickListener(view -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmentSample fragmentSample = new FragmentSample();
            fragmentTransaction.replace(R.id.fragmentlayout, fragmentSample);
            fragmentTransaction.commit();
        });
        imgnewss.setOnClickListener(view -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmentNews fragmentNews = new FragmentNews();
            fragmentTransaction.replace(R.id.fragmentlayout, fragmentNews);
            fragmentTransaction.commit();
        });
        imgkubik.setOnClickListener(view -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmentKubik fragmentKubik = new FragmentKubik();
            fragmentTransaction.replace(R.id.fragmentlayout, fragmentKubik);
            fragmentTransaction.commit();
        });
        imguserr.setOnClickListener(view -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmentUser fragmentUser = new FragmentUser();
            fragmentTransaction.replace(R.id.fragmentlayout, fragmentUser);
            fragmentTransaction.commit();
        });

        tstart.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentSample fragmentSample = new FragmentSample();
        fragmentTransaction.replace(R.id.fragmentlayout, fragmentSample);
        fragmentTransaction.commit();

        viewchiq.setOnClickListener(view -> logout());
    }

    private void readdatasql() {
        Cursor res = MyDb.oqish();
        StringBuilder stringBuffer = new StringBuilder();
        StringBuilder stringBuffer1 = new StringBuilder();
        StringBuilder stringBuffer2 = new StringBuilder();
        StringBuilder stringBuffer3 = new StringBuilder();
        StringBuilder stringBuffer4 = new StringBuilder();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBuffer.append(res.getString(1));
                stringBuffer1.append(res.getString(2));
                stringBuffer2.append(res.getString(3));
                stringBuffer3.append(res.getString(4));
                stringBuffer4.append(res.getString(4));
            }
            UID = stringBuffer.toString();
            TIL = stringBuffer1.toString();
            REJIM = stringBuffer2.toString();
            KALIT = stringBuffer3.toString();
            PAROL = stringBuffer4.toString();
        }
    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("diqqat").setMessage("accauntingizdan chiqib ketmoqchimisiz")
                .setPositiveButton(getString(R.string._ha), (dialog, which) -> {
                    Cursor res = MyDb.oqish();
                    StringBuilder stringBuffer = new StringBuilder();
                    StringBuilder stringBuffer1 = new StringBuilder();
                    StringBuilder stringBuffer2 = new StringBuilder();
                    StringBuilder stringBuffer3 = new StringBuilder();
                    StringBuilder stringBuffer4 = new StringBuilder();
                    StringBuilder stringBuffer5 = new StringBuilder();
                    if (res != null && res.getCount() > 0) {
                        while (res.moveToNext()) {
                            stringBuffer.append(res.getString(0));
                            stringBuffer1.append(res.getString(1));
                            stringBuffer2.append(res.getString(2));
                            stringBuffer3.append(res.getString(3));
                            stringBuffer4.append(res.getString(4));
                            stringBuffer5.append(res.getString(5));
                        }
                        id = stringBuffer.toString();
                        uid = stringBuffer1.toString();
                        til = stringBuffer2.toString();
                        rejim = stringBuffer3.toString();
                        kalit = stringBuffer4.toString();
                        parol = stringBuffer5.toString();
                        parol1 = stringBuffer5.toString();
                    }
                    til = "en";
                    kalit = "0";
                    rejim = "0";
                    Boolean result2 = MyDb.ozgartir(id, uid, til, rejim, kalit, parol);
                    intent = new Intent(Sample.this, Login.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton(getString(R.string._yoq), null)
                .show();
    }
}
