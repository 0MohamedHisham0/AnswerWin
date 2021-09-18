package com.osama.answerwin.Utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.osama.answerwin.Models.UserModel;
import com.osama.answerwin.R;

import java.util.Objects;

public class Constants {

    private static FirebaseFirestore db;
    private static FirebaseDatabase dbReal;
    private static DatabaseReference databaseReference;
    private static FirebaseAuth mAut;
    private static final String userId = Objects.requireNonNull(Objects.requireNonNull(Constants.GetAuth()).getCurrentUser()).getUid();

    public static FirebaseAuth GetAuth() {

        if (mAut == null)
            mAut = FirebaseAuth.getInstance();

        return mAut;
    }

    public static void GetUserData(String userId) {
        UserModel userModel;
        GetRef().child("Users").child(userId).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);
            }
        });
    }


    public static FirebaseFirestore GetFireStoneDb() {

        if (db == null)
            db = FirebaseFirestore.getInstance();

        return db;
    }

    public static DatabaseReference GetRef() {

        if (databaseReference == null || dbReal == null)
            dbReal = FirebaseDatabase.getInstance();
        databaseReference = dbReal.getReference();


        return databaseReference;
    }

    public static void AddValueTOPoints(Integer value, Context context) {

        GetRef().child("Users").child(Objects.requireNonNull(GetAuth().getCurrentUser()).getUid()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                String points = dataSnapshot.child("points").getValue(String.class);
                GetRef().child("Users").child(userId).child("points").setValue((value + Integer.parseInt(points)) + "");
            }
        });
    }

    public static void SubValueFromJewel(Integer value, Context context) {

        GetRef().child("Users").child(Objects.requireNonNull(GetAuth().getCurrentUser()).getUid()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                String jewels = dataSnapshot.child("jewels").getValue(String.class);
                GetRef().child("Users").child(userId).child("jewels").setValue(( Integer.parseInt(jewels) - 1 ) + "");
            }
        });
    }

    public static void openWelcomeDialog(Context context) {
        Dialog dialog = new Dialog(context); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_welcome);

        Button button = dialog.findViewById(R.id.bu_dialog_welcome);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setTitle("Welcome");
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);

    }


}
