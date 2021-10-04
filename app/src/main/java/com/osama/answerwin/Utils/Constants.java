package com.osama.answerwin.Utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.Keyboard;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
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

    public static void makeIntent(Context context1 , Class<?> context2){
        context1.startActivity(new Intent(context1, context2));
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //Un Used Fun
    public static void GetUserData(String userId) {
        UserModel userModel;
        GetRef().child("Users").child(userId).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);


            }
        });
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

    public void readExcelFileFromAssets(Context context) {
        try {
            InputStream myInput;
            // initialize asset manager
            AssetManager assetManager = context.getAssets();
            //  open excel sheet
            myInput = assetManager.open("myexcelsheet.xls");

            // Create a POI File System object

            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            // We now need something to iterate through the cells.
            Iterator<Row> rowIter = mySheet.rowIterator();

            int rowno =0;
            while (rowIter.hasNext()) {
                HSSFRow myRow = (HSSFRow) rowIter.next();
                if(rowno !=0) {
                    Iterator<Cell> cellIter = myRow.cellIterator();
                    int colno =0;
                    String sno="", date="", det="";
                    while (cellIter.hasNext()) {
                        HSSFCell myCell = (HSSFCell) cellIter.next();
                        if (colno==0){
                            sno = myCell.toString();
                        }else if (colno==1){
                            date = myCell.toString();
                        }else if (colno==2){
                            det = myCell.toString();
                        }
                        colno++;
                    }
                }
                rowno++;
            }
        } catch (Exception e) {

        }
    }



}
