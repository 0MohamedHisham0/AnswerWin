package com.osama.answerwin.Activities;

import static com.osama.answerwin.Utils.Constants.GetRef;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.osama.answerwin.Models.UserModel;
import com.osama.answerwin.R;
import com.osama.answerwin.Utils.Constants;

import java.util.Objects;


public class SplashScreen extends AppCompatActivity {
    private static FirebaseFirestore db;
    private static FirebaseDatabase dbReal;
    private static DatabaseReference databaseReference;
    private static FirebaseAuth mAut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (Constants.checkInternetConnection(this)) {

            new Handler().postDelayed(new Runnable() {

                public void run() {
                    //This method will be executed once the timer is over
                    // Start your app main activity
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        GetUserData(Objects.requireNonNull(Constants.GetAuth().getCurrentUser()).getUid());
                    } else {
                        Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                        startActivity(i);
                        // close this activity
                        finishAffinity();
                    }
                }
            }, 2000);
        } else {
            Toast.makeText(this, "لا يوجد اتصال بالانترنت, تأكد من اتصالك", Toast.LENGTH_SHORT).show();
        }

    }

    public void GetUserData(String userId) {
        UserModel userModel;
        GetRef().child("Users").child(userId).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (Objects.requireNonNull(dataSnapshot.child("role").getValue()).toString().equals("admin")) {
                    Intent i = new Intent(SplashScreen.this, AdminHomeActivity.class);
                    startActivity(i);
                    // close this activity
                    finishAffinity();
                } else {
                    Intent i = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(i);
                    // close this activity
                    finishAffinity();
                }
            }
        });
    }

}