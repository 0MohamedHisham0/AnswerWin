package com.osama.answerwin.Utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;
import com.osama.answerwin.R;

public class Constants {

    private static FirebaseFirestore db;

    public static FirebaseFirestore GetFireStoneDb() {

        if (db == null)
            db = FirebaseFirestore.getInstance();

        return db;
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
