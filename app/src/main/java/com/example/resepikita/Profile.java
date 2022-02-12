package com.example.resepikita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    TextView fName,email,phoneNo,pssword;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    boolean clicked = false;
    private ImageView edit_btn;
    LinearLayout layoutEditProfile;
    EditText etpassword_pro, etphonenum_pro,etemail_pro,etfullname_pro;
    TextView tvEdit;
    Button save;

    String uid = mAuth.getCurrentUser().getUid();
    Map<String, Object> user = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fName = findViewById(R.id.fullname_pro);
        email = findViewById(R.id.email_pro);
        phoneNo = findViewById(R.id.phonenum_pro);
        pssword = findViewById(R.id.password_pro);
        etpassword_pro = findViewById(R.id.etpassword_pro);
        etphonenum_pro = findViewById(R.id.etphonenum_pro);
        etemail_pro = findViewById(R.id.etemail_pro);
        etfullname_pro = findViewById(R.id.etfullname_pro);


        save = findViewById(R.id.save);

        layoutEditProfile = findViewById(R.id.layoutEditProfile);
        tvEdit = findViewById(R.id.tvEdit);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutEditProfile.setVisibility(View.VISIBLE);
            }
        });

        //Toast.makeText(Profile.this, uid,Toast.LENGTH_SHORT).show();

//        edit_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editBtn(clicked);
//            }
//        });
        displayUserProfile();
    }

    public boolean editBtn(boolean clicked){
        clicked = true;
        return clicked;
    }

    public void displayUserProfile(){
        DocumentReference docRef = db.collection("Users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        fName.setText(document.getData().get("fullname").toString());
                        email.setText(document.getData().get("email").toString());
                        phoneNo.setText(document.getData().get("phonenum").toString());
                        pssword.setText(document.getData().get("password").toString());
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }

    public void updateUserProfile(){
        user.put("fullname", etfullname_pro.getText().toString());
        user.put("email", etemail_pro.getText().toString());
        user.put("password", etpassword_pro.getText().toString());
        user.put("phonenum", etphonenum_pro.getText().toString());


        DocumentReference washingtonRef = db.collection("Users").document(uid);

// Set the "isCapital" field of the city 'DC'
        washingtonRef
                .update("fullname", user.get("fullname"),"email",user.get("email"),"password",user.get("password"),"phonenum", user.get("phonenum"))

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Log.d(TAG, "DocumentSnapshot successfully updated!");
                        Toast.makeText(Profile.this, "Profile successfully updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Profile.this,Profile.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "Error updating document", e);
                    }
                });
    }




    public void Home(View v){
        Intent intent = new Intent(Profile.this ,Dashboard.class);
        startActivity(intent);
        finish();
    }
    public void Search(View v){
        Intent intent = new Intent(Profile.this ,Search.class);
        startActivity(intent);
        finish();
    }

}