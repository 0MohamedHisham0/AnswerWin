package com.osama.answerwin.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import carbon.widget.ImageView;

public class AddQuestions_Dashboard extends AppCompatActivity {
    private EditText etQ, etTrue, etFalse1, etFalse2, etFalse3;
    private Button bu_save;
    private ImageView bu_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions_screen);


        initViews();

    }

    private void initViews() {
        etQ = findViewById(R.id.etQ);
        etTrue = findViewById(R.id.etTrueQ);
        etFalse1 = findViewById(R.id.etFalseQ1);
        etFalse2 = findViewById(R.id.etFalseQ2);
        etFalse3 = findViewById(R.id.etFalseQ3);
        bu_save = findViewById(R.id.buSaveQs);
        bu_back = findViewById(R.id.backJewelQ_AddQAc);

        bu_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveQu();
            }
        });
    }

    private void saveQu() {

        String Q = etQ.getText().toString();
        String sTrue = etTrue.getText().toString();
        String sFalse1 = etFalse1.getText().toString();
        String sFalse2 = etFalse2.getText().toString();
        String sFalse3 = etFalse3.getText().toString();

        if (!Q.equals("") && !sTrue.equals("") && !sFalse1.equals("") && !sFalse2.equals("") && !sFalse3.equals("")) {
            AddNewQuestion(Q, sTrue, sFalse1, sFalse2, sFalse3);

        } else {
            Toast.makeText(this, "املاء اليينات اولا", Toast.LENGTH_SHORT).show();

        }
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
                        Toast.makeText(AddQuestions_Dashboard.this, "تم حفظ سؤالك", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddQuestions_Dashboard.this, "هناك مشكله في البينات", Toast.LENGTH_SHORT).show();

                    }
                });

    }

}