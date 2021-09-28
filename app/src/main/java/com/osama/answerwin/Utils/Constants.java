package com.osama.answerwin.Utils;

import android.app.Dialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.osama.answerwin.Models.BooledModel;
import com.osama.answerwin.Models.UserModel;
import com.osama.answerwin.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Constants {

    private static FirebaseFirestore db;
    private static FirebaseDatabase dbReal;
    private static DatabaseReference databaseReference;
    private static FirebaseAuth mAut;

    //Text

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

    public static void GetSortedQ(String startAt, String endAt, Context context) {
        GetFireStoneDb().collection("BoolUsers").orderBy("date", com.google.firebase.firestore.Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<BooledModel> list = new ArrayList<>();
                ;

                if (task.isSuccessful()) {
                    list.clear();


                    for (DocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult())) {
                        BooledModel user = documentSnapshot.toObject(BooledModel.class);
                        list.add(user);
                    }
                    Toast.makeText(context, "" + list.size(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });


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

                if (dataSnapshot != null) {
                    String jewels = dataSnapshot.child("jewels").getValue(String.class);
                    GetRef().child("Users").child(userId).child("jewels").setValue(Integer.parseInt(jewels) - 1 + "");
                }

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

    public static String convertToDate(String time) {
        long timeInt = Long.parseLong(time);
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timeInt * 1000);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

    public static Long convertToTimestamp(String timeInDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        long ts = dateFormat.parse(timeInDate).getTime() / 1000;
        return ts;
    }

    public static Long getCurrentTimestamp() {
        Long tsLong = System.currentTimeMillis() / 1000;
        return tsLong;
    }
}
