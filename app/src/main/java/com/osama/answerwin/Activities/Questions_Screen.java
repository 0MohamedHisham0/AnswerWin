package com.osama.answerwin.Activities;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.osama.answerwin.Models.Questions_Model;
import com.osama.answerwin.R;
import com.osama.answerwin.Utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Questions_Screen extends AppCompatActivity {
    private static final String TAG = "RandN";

    //Views
    TextView TXT_QuestionNumber, TXT_QuestionMain, TXT_Answer1, TXT_Answer2, TXT_Answer3, TXT_Answer4;
    FrameLayout Frame_QuestionMain, Frame_Answer1, Frame_Answer2, Frame_Answer3, Frame_Answer4, Frame_BtnNext, Frame_BtnBackHome;
    SpinKitView spin_kit_QS;
    LinearLayout linearLayout_Answers;

    //Var
    private Map<String, Object> Question = new HashMap<>();
    private List<Questions_Model> Questions_List = new ArrayList<>();
    private TextView ClickedAnswer;
    private List<Integer> UsedQuList = new ArrayList<>();
    private Questions_Model CurrentModel;
    public int Score = 0;
    private int CurrentQuNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qustions_screen);

        initViews();

        GetQuFromFB();

    }

    private void initViews() {
        //TextViews
        TXT_QuestionNumber = findViewById(R.id.TXT_CurrentQuestionNumber);
        TXT_QuestionMain = findViewById(R.id.TXT_MainQuestion);
        TXT_Answer1 = findViewById(R.id.TXT_Answer1);
        TXT_Answer2 = findViewById(R.id.TXT_Answer2);
        TXT_Answer3 = findViewById(R.id.TXT_Answer3);
        TXT_Answer4 = findViewById(R.id.TXT_Answer4);

        //Frames
        Frame_QuestionMain = findViewById(R.id.frameLayout_Question);
        Frame_Answer1 = findViewById(R.id.frameLayoutAnswer1);
        Frame_Answer2 = findViewById(R.id.frameLayoutAnswer2);
        Frame_Answer3 = findViewById(R.id.frameLayoutAnswer3);
        Frame_Answer4 = findViewById(R.id.frameLayoutAnswer4);
        Frame_BtnNext = findViewById(R.id.frameLayout_BtnNext);
        Frame_BtnBackHome = findViewById(R.id.frameLayoutBtnBack);

        spin_kit_QS = findViewById(R.id.spin_kit_QS);

        linearLayout_Answers = findViewById(R.id.linearLayout_Answers);

        TXT_QuestionNumber.setText(CurrentQuNumber + "/" + "20");

        //ClicksListener
        Frame_Answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickedAnswer = TXT_Answer1;

                Frame_Answer1.setBackground(getDrawable(R.color.green_color));
                TXT_Answer1.setTextColor(getResources().getColor(R.color.purple_color));

                Frame_Answer2.setBackground(getDrawable(R.color.purple_color));
                Frame_Answer3.setBackground(getDrawable(R.color.purple_color));
                Frame_Answer4.setBackground(getDrawable(R.color.purple_color));

                TXT_Answer2.setTextColor(getResources().getColor(R.color.green_color));
                TXT_Answer3.setTextColor(getResources().getColor(R.color.green_color));
                TXT_Answer4.setTextColor(getResources().getColor(R.color.green_color));

            }
        });

        Frame_Answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickedAnswer = TXT_Answer2;

                Frame_Answer2.setBackground(getDrawable(R.color.green_color));
                TXT_Answer2.setTextColor(getResources().getColor(R.color.purple_color));

                Frame_Answer1.setBackground(getDrawable(R.color.purple_color));
                Frame_Answer3.setBackground(getDrawable(R.color.purple_color));
                Frame_Answer4.setBackground(getDrawable(R.color.purple_color));

                TXT_Answer1.setTextColor(getResources().getColor(R.color.green_color));
                TXT_Answer3.setTextColor(getResources().getColor(R.color.green_color));
                TXT_Answer4.setTextColor(getResources().getColor(R.color.green_color));
            }
        });

        Frame_Answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickedAnswer = TXT_Answer3;

                Frame_Answer3.setBackground(getDrawable(R.color.green_color));
                TXT_Answer3.setTextColor(getResources().getColor(R.color.purple_color));

                Frame_Answer2.setBackground(getDrawable(R.color.purple_color));
                Frame_Answer1.setBackground(getDrawable(R.color.purple_color));
                Frame_Answer4.setBackground(getDrawable(R.color.purple_color));

                TXT_Answer2.setTextColor(getResources().getColor(R.color.green_color));
                TXT_Answer1.setTextColor(getResources().getColor(R.color.green_color));
                TXT_Answer4.setTextColor(getResources().getColor(R.color.green_color));
            }
        });

        Frame_Answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickedAnswer = TXT_Answer4;

                Frame_Answer4.setBackground(getDrawable(R.color.green_color));
                TXT_Answer4.setTextColor(getResources().getColor(R.color.purple_color));

                Frame_Answer2.setBackground(getDrawable(R.color.purple_color));
                Frame_Answer3.setBackground(getDrawable(R.color.purple_color));
                Frame_Answer1.setBackground(getDrawable(R.color.purple_color));

                TXT_Answer2.setTextColor(getResources().getColor(R.color.green_color));
                TXT_Answer3.setTextColor(getResources().getColor(R.color.green_color));
                TXT_Answer1.setTextColor(getResources().getColor(R.color.green_color));
            }
        });

        Frame_BtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ClickedAnswer != null) {
                    if (CheckIFAnswerIsTrue())
                        Score++;
                    ClickedAnswer = null;
                    DataToViews();
                    DefaultColorViews();
                } else {
                    Toast.makeText(Questions_Screen.this, "اختر اجابتك اولا", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Frame_BtnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Questions_Screen.this, HomeActivity.class));
            }
        });

    }

    private void DataToViews() {

        if (Questions_List != null) {
            int randN = getRandomNumber(0, Questions_List.size());

            if (!UsedQuList.contains(randN)) {
                TXT_QuestionNumber.setText(CurrentQuNumber + "/" + "20");

                CurrentQuNumber++;
                CurrentModel = Questions_List.get(randN);
                ModelToView(Questions_List.get(randN));
                UsedQuList.add(randN);
            } else {
                //MaxQuestions
                if (Questions_List.size() == UsedQuList.size()) {
                    CurrentQuNumber = 0;
                    openDialogDetail();
                } else
                    //Go To Next Question
                    DataToViews();
            }

        }
    }

    private void GetQuFromFB() {
        Constants.GetFireStoneDb().collection("Questions")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            spin_kit_QS.setVisibility(View.GONE);
                            linearLayout_Answers.setVisibility(View.VISIBLE);
                            Frame_QuestionMain.setVisibility(View.VISIBLE);
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                                Question = document.getData();

                                Questions_Model questions_model = new Questions_Model(
                                        Question.get("main_question").toString(),
                                        Question.get("t_answer_1").toString(),
                                        Question.get("f_answer_2").toString(),
                                        Question.get("f_answer_3").toString(),
                                        Question.get("f_answer_4").toString());

                                Questions_List.add(questions_model);
                            }
                            DataToViews();

                        } else {
                            Toast.makeText(Questions_Screen.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void ModelToView(Questions_Model model) {
        int randN = getRandomNumber(1, 4);
        switch (randN) {
            case 1:
                TXT_QuestionMain.setText(model.getMain_question());
                TXT_Answer1.setText(model.getT_answer_1());
                Log.i(TAG, "ModelToView: " + randN);
                TXT_Answer2.setText(model.getF_answer_2());
                TXT_Answer3.setText(model.getF_answer_3());
                TXT_Answer4.setText(model.getF_answer_4());
                break;
            case 2:
                TXT_QuestionMain.setText(model.getMain_question());
                TXT_Answer1.setText(model.getF_answer_2());
                TXT_Answer2.setText(model.getT_answer_1());
                TXT_Answer3.setText(model.getF_answer_3());
                TXT_Answer4.setText(model.getF_answer_4());
                break;

            case 3:
                TXT_QuestionMain.setText(model.getMain_question());
                TXT_Answer1.setText(model.getF_answer_2());
                TXT_Answer2.setText(model.getF_answer_3());
                TXT_Answer3.setText(model.getT_answer_1());
                TXT_Answer4.setText(model.getF_answer_4());
                break;

            case 4:
                TXT_QuestionMain.setText(model.getMain_question());
                TXT_Answer1.setText(model.getF_answer_2());
                TXT_Answer2.setText(model.getF_answer_4());
                TXT_Answer3.setText(model.getF_answer_3());
                TXT_Answer4.setText(model.getT_answer_1());
                break;

        }

    }

    public Boolean CheckIFAnswerIsTrue() {
        return ClickedAnswer.getText().toString().equals(CurrentModel.getT_answer_1());
    }

    public void DefaultColorViews() {
        Frame_Answer1.setBackground(getDrawable(R.color.purple_color));
        Frame_Answer2.setBackground(getDrawable(R.color.purple_color));
        Frame_Answer3.setBackground(getDrawable(R.color.purple_color));
        Frame_Answer4.setBackground(getDrawable(R.color.purple_color));

        TXT_Answer1.setTextColor(getResources().getColor(R.color.green_color));
        TXT_Answer2.setTextColor(getResources().getColor(R.color.green_color));
        TXT_Answer3.setTextColor(getResources().getColor(R.color.green_color));
        TXT_Answer4.setTextColor(getResources().getColor(R.color.green_color));
    }

    private void openDialogDetail() {
        Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_points_win);

        TextView textView = dialog.findViewById(R.id.tv_score_dialogWin);
        Button button = dialog.findViewById(R.id.bu_dialogWin);

        textView.setText(Score + "");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setTitle("winPoints");
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);


    }


}