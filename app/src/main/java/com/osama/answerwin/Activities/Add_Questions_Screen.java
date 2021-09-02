package com.osama.answerwin.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.osama.answerwin.R;
import com.osama.answerwin.Utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class Add_Questions_Screen extends AppCompatActivity {

    private static final String TAG = "RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions_screen);

        AddNewQuestion("String main_q", "String t_answer_1", "String f_answer_2", "String f_answer_3", "String f_answer_4");

    }

    private void AddNewQuestion(String main_q, String t_answer_1, String f_answer_2, String f_answer_3, String f_answer_4) {
        Map<String, Object> Question = new HashMap<>();

        Question.put("main_question", main_q);
        Question.put("t_answer_1", t_answer_1);
        Question.put("f_answer_2", f_answer_2);
        Question.put("f_answer_3", f_answer_3);
        Question.put("f_answer_4", f_answer_4);


        Constants.GetFireStoneDb().collection("Questions")
                .add(Question)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Add_Questions_Screen.this, "Your Question Has been Added", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Add_Questions_Screen.this, "Sorry, there is a problem with internet try again later", Toast.LENGTH_SHORT).show();

                    }
                });
    }

}