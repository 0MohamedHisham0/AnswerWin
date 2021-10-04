package com.osama.answerwin.Activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import com.osama.answerwin.Models.Questions_Model
import android.os.Bundle
import com.osama.answerwin.R
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.osama.answerwin.Models.UserModel
import com.osama.answerwin.Utils.Constants
import kotlinx.android.synthetic.main.activity_qustions_screen.*
import java.util.*

class Questions_Screen : BaseActivity() {
    //Var
    private var mInterstitialAd: InterstitialAd? = null
    private var Question: Map<String, Any> = HashMap()
    private val Questions_List: MutableList<Questions_Model> = ArrayList()
    private var ClickedAnswer: TextView? = null
    private val UsedQuList: MutableList<Int> = ArrayList()
    private var CurrentModel: Questions_Model? = null
    var Score = 0
    private var CurrentQuNumber = 0
    private var toggle = false
    private var IntentResult: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qustions_screen)
        initViews()
        GetQuFromFB()
    }

    private fun initViews() {
        val extras = intent.extras
        if (extras != null) {
            IntentResult = extras.getString("path")
            //The key argument here must match that used in the other activity
        } else {
            Constants.openWelcomeDialog(this)
        }
        //ClicksListener
        frameLayoutAnswer1.setOnClickListener(View.OnClickListener {
            makeButtonsUnClickable()

            ClickedAnswer = TXT_Answer1
            if (CheckIFAnswerIsTrue()) {
                Score++
                tv_TrueAnswersNumber.text = "اجاباتك الصحيحة :" + Score + "/20"
                frameLayoutAnswer1.setBackground(getDrawable(R.color.GreenColor))

            } else {
                TrueQu(
                    frameLayoutAnswer1,
                    frameLayoutAnswer2,
                    frameLayoutAnswer3,
                    frameLayoutAnswer4,
                    TXT_Answer1,
                    TXT_Answer2,
                    TXT_Answer3,
                    TXT_Answer4
                )
            }
        })
        frameLayoutAnswer2.setOnClickListener(View.OnClickListener {
            ClickedAnswer = TXT_Answer2
            makeButtonsUnClickable()

            if (CheckIFAnswerIsTrue()) {

                Score++
                tv_TrueAnswersNumber.text = "اجاباتك الصحيحة :" + Score + "/20"

                frameLayoutAnswer2.setBackground(getDrawable(R.color.GreenColor))
            } else {
                TrueQu(
                    frameLayoutAnswer1,
                    frameLayoutAnswer2,
                    frameLayoutAnswer3,
                    frameLayoutAnswer4,
                    TXT_Answer1,
                    TXT_Answer2,
                    TXT_Answer3,
                    TXT_Answer4
                )
            }
        })
        frameLayoutAnswer3.setOnClickListener(View.OnClickListener {
            makeButtonsUnClickable()

            ClickedAnswer = TXT_Answer3
            if (CheckIFAnswerIsTrue()) {
                Score++
                tv_TrueAnswersNumber.text = "اجاباتك الصحيحة :" + Score + "/20"

                frameLayoutAnswer3.setBackground(getDrawable(R.color.GreenColor))
            } else {
                TrueQu(
                    frameLayoutAnswer1,
                    frameLayoutAnswer2,
                    frameLayoutAnswer3,
                    frameLayoutAnswer4,
                    TXT_Answer1,
                    TXT_Answer2,
                    TXT_Answer3,
                    TXT_Answer4
                )
            }
        })
        frameLayoutAnswer4.setOnClickListener(View.OnClickListener {
            makeButtonsUnClickable()

            ClickedAnswer = TXT_Answer4
            if (CheckIFAnswerIsTrue()) {
                Score++
                tv_TrueAnswersNumber.text = "اجاباتك الصحيحة :" + Score + "/20"

                frameLayoutAnswer4.setBackground(getDrawable(R.color.GreenColor))
            } else {
                TrueQu(
                    frameLayoutAnswer1,
                    frameLayoutAnswer2,
                    frameLayoutAnswer3,
                    frameLayoutAnswer4,
                    TXT_Answer1,
                    TXT_Answer2,
                    TXT_Answer3,
                    TXT_Answer4
                )
            }
        })
        frameLayout_BtnNext.setOnClickListener(View.OnClickListener {
            if (ClickedAnswer != null) {
                DefaultColorViews()
                DataToViews()
                ClickedAnswer = null
            } else {
                Toast.makeText(this@Questions_Screen, "اختر اجابتك اولا", Toast.LENGTH_SHORT).show()
            }
        })
        frameLayoutBtnBack.setOnClickListener(View.OnClickListener {
        if(IntentResult == "Bool")
        {
            Toast.makeText(this, "لقد خرجت من السحب حاول مره اخري", Toast.LENGTH_SHORT).show()
            toggle = true
            startActivity(Intent(this, HomeActivity::class.java))
            finish()

        }else  {
            openDialogWinPoints()
        }


        })

    }

    private fun DataToViews() {
        if (Questions_List.size < 20) {
            Toast.makeText(
                this,
                "عفوا لا يوجد عدد اسائله كافي حاليا جرب مره اخره" + Questions_List.size,
                Toast.LENGTH_SHORT
            ).show()
        } else {
            //limit Qu
            if (UsedQuList.size == 20) {
                //MaxQuestions
                //Check What is incoming he is in bool or in QuAndWin
                if (IntentResult == "Bool") {
                    //Bool
                    if (Score >= 10) {
                        //Winner
                        sendUserToBoolUsers(
                            Constants.GetAuth().currentUser!!.uid
                        )
                    } else {
                        //Losers
                        openDialogFailedBool()
                    }
                } else {
                    //Coming from home
                    openDialogWinPoints()
                }
            } else {  //Go To Next Question
                getRandomQuestion()

            }


        }

    }

    private fun getRandomQuestion() {
        val randN = getRandomNumber(0, Questions_List.size)
        if (!UsedQuList.contains(randN)) {
            //ShowAdd
            showAd()
            //Add Question number
            CurrentQuNumber++
            TXT_CurrentQuestionNumber!!.text = " عدد الاسئلة : $CurrentQuNumber/20 "

            //Get Rand Model
            CurrentModel = Questions_List[randN]
            getAnswersRand(CurrentModel!!)
            UsedQuList.add(randN)

        } else {
            getRandomQuestion()
        }

    }

    private fun showAd() {
        initAd(resources.getString(R.string.rewarded_ad_interstitial))
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)

        }
    }

    fun initAd(UnitID: String) {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this, UnitID, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null

            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd

                mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                    }

                    override fun onAdShowedFullScreenContent() {
                        mInterstitialAd = null
                    }
                }
            }
        })
    }

    // Firebase
    private fun GetQuFromFB() {
        Constants.GetFireStoneDb().collection("Questions")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    spin_kit_QS!!.visibility = View.GONE
                    linearLayout_Answers!!.visibility = View.VISIBLE
                    frameLayout_Question!!.visibility = View.VISIBLE
                    for (document in Objects.requireNonNull(task.result)!!) {
                        Question = document.data
                        val questions_model = Questions_Model(
                            Question["main_question"].toString(),
                            Question["t_answer_1"].toString(),
                            Question["f_answer_2"].toString(),
                            Question["f_answer_3"].toString(),
                            Question["f_answer_4"].toString()
                        )
                        Questions_List!!.add(questions_model)
                    }
                    DataToViews()
                } else {
                    Toast.makeText(
                        this@Questions_Screen,
                        "" + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun sendUserToBoolUsers(userId: String?) {
        Constants.GetRef().child("Users").child(userId!!).get()
            .addOnSuccessListener { dataSnapshot ->
                val userModel = dataSnapshot.getValue(UserModel::class.java)

                //Add boolUsers Var
                val MapUser: MutableMap<String, Any> = HashMap()
                val tsLong = System.currentTimeMillis() / 1000
                MapUser["userName"] = userModel!!.name
                MapUser["UserID"] = Constants.GetAuth().currentUser!!
                    .uid
                MapUser["date"] = tsLong

                //Change User status to inPending
                Constants.GetRef().child("Users").child(userId).child("status")
                    .setValue("داخل السحب")

                //AddBoolUsers
                Constants.GetFireStoneDb().collection("BoolUsers").document(
                    userId
                ).set(MapUser).addOnSuccessListener { openDialogYouEnteredBool() }
            }
    }

    //Small Fun
    fun CheckIFAnswerIsTrue(): Boolean {
        return ClickedAnswer!!.text.toString() == CurrentModel!!.t_answer_1
    }

    fun CheckIFBuIsTrueOrFalse(textView: TextView?): Boolean {
        return textView!!.text.toString() == CurrentModel!!.t_answer_1
    }

    fun getAnswersRand(model: Questions_Model) {
        val randN = getRandomNumber(1, 4)
        when (randN) {
            1 -> {
                TXT_MainQuestion!!.text = model.main_question
                TXT_Answer1!!.text = model.t_answer_1
                TXT_Answer2!!.text = model.f_answer_2
                TXT_Answer3!!.text = model.f_answer_3
                TXT_Answer4!!.text = model.f_answer_4
            }
            2 -> {
                TXT_MainQuestion!!.text = model.main_question
                TXT_Answer1!!.text = model.f_answer_2
                TXT_Answer2!!.text = model.t_answer_1
                TXT_Answer3!!.text = model.f_answer_3
                TXT_Answer4!!.text = model.f_answer_4
            }
            3 -> {
                TXT_MainQuestion!!.text = model.main_question
                TXT_Answer1!!.text = model.f_answer_2
                TXT_Answer2!!.text = model.f_answer_3
                TXT_Answer3!!.text = model.t_answer_1
                TXT_Answer4!!.text = model.f_answer_4
            }
            4 -> {
                TXT_MainQuestion!!.text = model.main_question
                TXT_Answer1!!.text = model.f_answer_2
                TXT_Answer2!!.text = model.f_answer_4
                TXT_Answer3!!.text = model.f_answer_3
                TXT_Answer4!!.text = model.t_answer_1
            }
        }
    }

    fun DefaultColorViews() {
        frameLayoutAnswer1!!.background = getDrawable(R.color.purple_color)
        frameLayoutAnswer2!!.background = getDrawable(R.color.purple_color)
        frameLayoutAnswer3!!.background = getDrawable(R.color.purple_color)
        frameLayoutAnswer4!!.background = getDrawable(R.color.purple_color)
        TXT_Answer1!!.setTextColor(resources.getColor(R.color.green_color))
        TXT_Answer2!!.setTextColor(resources.getColor(R.color.green_color))
        TXT_Answer3!!.setTextColor(resources.getColor(R.color.green_color))
        TXT_Answer4!!.setTextColor(resources.getColor(R.color.green_color))
        frameLayoutAnswer1!!.isClickable = true
        frameLayoutAnswer2!!.isClickable = true
        frameLayoutAnswer3!!.isClickable = true
        frameLayoutAnswer4!!.isClickable = true
    }

    fun getRandomNumber(min: Int, max: Int): Int {
        return (Math.random() * (max - min) + min).toInt()
    }

    fun TrueQu(
        Qu1: FrameLayout?,
        Qu2: FrameLayout?,
        Qu3: FrameLayout?,
        Qu4: FrameLayout?,
        TxtQu1: TextView?,
        TxtQu2: TextView?,
        TxtQu3: TextView?,
        TxtQu4: TextView?
    ) {
        makeCurrentButtonRed(ClickedAnswer!!)

        if (CheckIFBuIsTrueOrFalse(TxtQu1)) {
            Qu1!!.background = getDrawable(R.color.GreenColor)


        }
        if (CheckIFBuIsTrueOrFalse(TxtQu2)) {
            Qu2!!.background = getDrawable(R.color.GreenColor)

        }
        if (CheckIFBuIsTrueOrFalse(TxtQu3)) {

            Qu3!!.background = getDrawable(R.color.GreenColor)
        }
        if (CheckIFBuIsTrueOrFalse(TxtQu4)) {

            Qu4!!.background = getDrawable(R.color.GreenColor)
        }

    }

    private fun makeButtonsUnClickable() {
        frameLayoutAnswer1!!.isClickable = false
        frameLayoutAnswer2!!.isClickable = false
        frameLayoutAnswer3!!.isClickable = false
        frameLayoutAnswer4!!.isClickable = false
    }

    private fun makeCurrentButtonRed(text: TextView) {

        if (text == TXT_Answer1) {
            frameLayoutAnswer1.background = getDrawable(R.color.RedColor)
            frameLayoutAnswer1.isClickable = false
        }
        if (text == TXT_Answer2) {
            frameLayoutAnswer2.background = getDrawable(R.color.RedColor)
            frameLayoutAnswer2.isClickable = false

        }
        if (text == TXT_Answer3) {
            frameLayoutAnswer3.background = getDrawable(R.color.RedColor)
            frameLayoutAnswer3.isClickable = false

        }
        if (text == TXT_Answer4) {
            frameLayoutAnswer4.background = getDrawable(R.color.RedColor)
            frameLayoutAnswer4.isClickable = false

        }
    }

    //Dialogs
    private fun openDialogYouEnteredBool() {
        val dialog = Dialog(this) // Context, this, etc.
        dialog.setContentView(R.layout.dialog_entered_bool)
        val button = dialog.findViewById<Button>(R.id.bu_dialogBool)
        //Edit User Status to pending
        button.setOnClickListener { //Go to profile Screen
            dialog.dismiss()
            toggle = true
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
        dialog.setTitle("EnteredBool")
        dialog.show()
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun openDialogWinPoints() {
        val dialog = Dialog(this) // Context, this, etc.
        dialog.setContentView(R.layout.dialog_points_win)
        val textView = dialog.findViewById<TextView>(R.id.tv_score_dialogWin)
        val button = dialog.findViewById<Button>(R.id.bu_dialogWin)
        Constants.AddValueTOPoints(Score, applicationContext)
        textView.text = Score.toString() + ""

        button.setOnClickListener {
            dialog.dismiss()
            toggle = true
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        dialog.setTitle("winPoints")
        dialog.show()
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }


    private fun openDialogFailedBool() {
        val dialog = Dialog(this) // Context, this, etc.
        dialog.setContentView(R.layout.dialog_failed_in_bool)
        val button = dialog.findViewById<Button>(R.id.bu_dialogFailedBool)
        button.setOnClickListener {
            dialog.dismiss()
            toggle = true
            startActivity(Intent(this@Questions_Screen, HomeActivity::class.java))
            finishAffinity()
        }
        dialog.setTitle("FailedBool")
        dialog.show()
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    override fun onStop() {
        super.onStop()
        if (!toggle) {
            startActivity(Intent(this@Questions_Screen, HomeActivity::class.java))
            finishAffinity()
        }
    }

}