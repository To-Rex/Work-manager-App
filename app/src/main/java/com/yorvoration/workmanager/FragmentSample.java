package com.yorvoration.workmanager;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
public class FragmentSample extends Fragment {
    private SqlData MyDb;
    private FirebaseFirestore db;
    private DocumentReference documentReference;
    private FirebaseAuth auth;
    private TextView txtsamusername,txtsamphone,txtsamid,txtsamcoin;
    private final int PICK_IMAGE_REQUEST = 71;
    String UID, TIL, REJIM, KALIT, PAROL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample,container,false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        MyDb = new SqlData(getContext());
        db = FirebaseFirestore.getInstance();
        txtsamusername = view.findViewById(R.id.txtsamusername);
        txtsamphone = view.findViewById(R.id.txtsamphone);
        txtsamid = view.findViewById(R.id.txtsamid);
        txtsamcoin = view.findViewById(R.id.txtsamcoin);
        readdatasql();
        readfirebasedata();


    }
    @SuppressLint("SetTextI18n")
    private void readfirebasedata(){
        documentReference = db.document("User/" + UID);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String astrocoin = documentSnapshot.getString("ASTROCOIN");
                String     ism = documentSnapshot.getString("ISMI");
                String   userid0 = documentSnapshot.getString("USERID");
                String    photo = documentSnapshot.getString("PHOTO");
                String    telefon = documentSnapshot.getString("TEL");
                txtsamusername.setText(ism);
                txtsamcoin.setText(astrocoin);
                txtsamid.setText("id "+ userid0);
                txtsamphone.setText(telefon);
            }
        });
    }
    private void readdatasql(){
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
            readfirebasedata();
        }
    }
}
