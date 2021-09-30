package com.osama.answerwin.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.osama.answerwin.Adapters.QuestionsAdapter
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
            finishAffinity()
        }
        backJewelQ_QDash.setOnClickListener {
            finish()
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
                    //Rv init
                    rv_QsDash.adapter = QuestionsAdapter(questionsList, questionsUidList, this)
                    spin_kit_QSD.visibility = View.INVISIBLE
                } else {
                    Toast.makeText(this, "" + task.exception!!.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }


}