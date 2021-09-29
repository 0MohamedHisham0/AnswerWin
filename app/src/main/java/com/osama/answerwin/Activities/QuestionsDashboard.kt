package com.osama.answerwin.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.osama.answerwin.Models.Questions_Model
import com.osama.answerwin.R
import com.osama.answerwin.Utils.Constants
import kotlinx.android.synthetic.main.activity_questions_dashboard.*
import java.util.*

class QuestionsDashboard : AppCompatActivity() {
    private val questionsList: MutableList<Questions_Model> = ArrayList()
    private val questionsUidList: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_dashboard)

        bu_fa_QuestionDashboard.setOnClickListener {
            startActivity(Intent(this, AddQuestions_Dashboard::class.java))
        }
        getQuestions()
    }

    private fun getQuestions() {
        Constants.GetFireStoneDb().collection("Questions")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (documentSnapshot in Objects.requireNonNull(task.result)!!) {
                        val questionModel = documentSnapshot.toObject(Questions_Model::class.java)

                        questionsUidList.add(documentSnapshot.id)
                        questionsList.add(questionModel)

                    }
                    Toast.makeText(
                        this,
                        "" + questionsList.size + "//" + questionsUidList.size,
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(this, "" + task.exception!!.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun deleteQuestion(position: Int) {
        Constants.GetFireStoneDb().collection("Questions").document(questionsUidList[position])
            .delete()
    }
}