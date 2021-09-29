package com.osama.answerwin.Activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.osama.answerwin.Adapters.BoolUsersAdapter
import com.osama.answerwin.Models.BooledModel
import com.osama.answerwin.R
import com.osama.answerwin.Utils.Constants
import kotlinx.android.synthetic.main.activity_bool_users.*
import java.util.*

class BoolUsersActivity : AppCompatActivity() {
    val winnersList: MutableList<BooledModel?> = ArrayList()
    val boolUsers: MutableList<BooledModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        var startDate: Long
        var endDate: Long

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bool_users)

        bu_okFilter_boolUsers.setOnClickListener {

            if (et_pickStart_boolUsers.isEmpty || et_pickEnd_boolUsers.isEmpty) {
                Toast.makeText(this, "من فضلك ادحل فتره البحث", Toast.LENGTH_SHORT).show()
            } else {
                startDate = Constants.convertToTimestamp(et_pickStart_boolUsers.text.toString())
                endDate = Constants.convertToTimestamp(et_pickEnd_boolUsers.text.toString())
                getSortedQ(this, startDate, endDate)

            }

        }

        bu_ok_boolUsers.setOnClickListener {
            if (!et_EnterWinnerNum.isEmpty) {
                if (et_EnterWinnerNum.text.toString().toInt() <= boolUsers.size) {
                    getRandomNumFromList(et_EnterWinnerNum.text.toString().toInt())
                    addUsersToPending(winnersList)
                    changeUserDateInRT(boolUsers ,winnersList )
                    Toast.makeText(
                        this,
                        "لقد اخترت عدد " + winnersList.size + " فائزين ",
                        Toast.LENGTH_SHORT
                    ).show()
                    recreate()
                } else {
                    Toast.makeText(
                        this,
                        "عدد الفائزين الذي ادخلته كبير جداا او ان هناك مشكله في استقبال البيانات",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "من فضلك ادخل عدد الفائزين!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun changeUserDateInRT(boolUsers:List<BooledModel>, winnerUsers: MutableList<BooledModel?>) {
        for (i in boolUsers){
            Constants.GetRef().child("Users").child(i.userID).child("status").setValue("خارج السحب")
        }

        for (i in winnerUsers){

            if (i != null) {
                Constants.GetRef().child("Users").child(i.userID).child("status").setValue("رابح")
            }


        }

    }

    //FireBase
    private fun getSortedQ(context: Context?, start: Long, end: Long) {
        Constants.GetFireStoneDb().collection("BoolUsers")
            .whereGreaterThanOrEqualTo("date", start).whereLessThanOrEqualTo("date", end)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    boolUsers.clear()
                    for (documentSnapshot in Objects.requireNonNull(task.result)!!) {
                        val user = documentSnapshot.toObject(BooledModel::class.java)
                        boolUsers.add(user)
                    }
                    if (boolUsers.size == 0){
                        toast("الفتره التي ادخلتها لا يوجدها بها مستخدمين")
                    }

                    rv_BoolUsers.adapter = BoolUsersAdapter(boolUsers, this)
                    rv_BoolUsers.isNestedScrollingEnabled = false;

                } else {
                    Toast.makeText(context, "" + task.exception!!.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun addUsersToPending(list: MutableList<BooledModel?>) {
        for (i in list) {
            if (i != null) {
                val userData_ = mutableMapOf<String, String>()
                userData_["UserID"] = i.userID.toString()
                Constants.GetFireStoneDb().collection("PendingUsers").document(i.userID)
                    .set(userData_)
                Constants.GetFireStoneDb().collection("BoolUsers").document(i.userID)
                    .delete()
            }
        }

    }

    private fun getRandomNumFromList(numberOfWinners: Int) {
        val rand = Random()
        winnersList.clear()
        var list: MutableList<BooledModel> = boolUsers

        for (i in 0 until numberOfWinners) {

            val randomIndex = rand.nextInt(list.size)
            winnersList.add(list[randomIndex])
            list.removeAt(randomIndex)

        }
    }

    fun toast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }


}