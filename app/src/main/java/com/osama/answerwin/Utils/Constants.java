package com.osama.answerwin.Utils;

import com.google.firebase.firestore.FirebaseFirestore;

public class Constants {

    private static FirebaseFirestore db;

    public static FirebaseFirestore GetFireStoneDb() {

        if (db == null)
            db = FirebaseFirestore.getInstance();

        return db;
    }

}
