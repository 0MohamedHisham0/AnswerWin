package com.osama.answerwin.Activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import carbon.widget.Button
import com.osama.answerwin.Adapters.WinnersDashAdapter
import com.osama.answerwin.Models.BooledModel
import com.osama.answerwin.Models.UserModel
import com.osama.answerwin.Models.WinnersDashModel
import com.osama.answerwin.Models.myPrizeModel
import com.osama.answerwin.R
import com.osama.answerwin.Utils.Constants
import kotlinx.android.synthetic.main.activity_winners_dashboard.*
import kotlinx.android.synthetic.main.item_win_dash.*
import java.util.HashMap

class WinnersDashboardActivity : BaseActivity() {
    private var pWinners: Map<String, Any> = HashMap()
    var pWinnersList = mutableListOf<UserModel>()
    var BUsersIDList = mutableListOf<String>()
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winners_dashboard)

        getPendingWinners2(Constants.GetAuth().currentUser?.uid)

        ivBackP_WinnersDash.setOnClickListener { onBackPressed() }

    }

    private fun getPendingWinners2(uid: String?) {
        if (uid != null) {
            Constants.GetFireStoneDb().collection("PendingUsers")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        for (document in task.result!!) {
                            pWinners = document.data
                            val userID = pWinners.get("UserID").toString()

                            BUsersIDList.add(userID)

                            Constants.GetRef().child("Users").child(userID).get()
                                .addOnSuccessListener { dataSnapshot ->
                                    val userModel = dataSnapshot.getValue(UserModel::class.java)

                                    if (userModel != null) {
                                        pWinnersList.add(userModel)
                                        Toast.makeText(
                                            this,
                                            "" + pWinnersList.size,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        if (document == task.result!!.last()) {
                                            initRV(pWinnersList, BUsersIDList)
                                        }
                                    }
                                }


                            pb_WinnersDash.visibility = View.INVISIBLE
                        }

                    }

//                    else {
//                        Toast.makeText(
//                            this,
//                            "" + task.exception!!.message,
//                            Toast.LENGTH_SHORT
//                        )
//                            .show()
//                    }
                }
        }
    }

    private fun initRV(winnerList: List<UserModel>, bUserID: List<String>) {
        rv_WinnersDash.adapter =
            WinnersDashAdapter(
                winnerList, this
            ) { prize, position ->
                cl_winnersDash.visibility = View.INVISIBLE
                pb_WinnersDash.visibility = View.VISIBLE
                if (prize.isNullOrEmpty()) {
                    pb_WinnersDash.visibility = View.INVISIBLE
                    cl_winnersDash.visibility = View.VISIBLE
                    Toast.makeText(
                        this@WinnersDashboardActivity,
                        "من فضلك, أدخل قيمة الجائزة.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    addWinners(
                        winnerList.get(position),
                        bUserID.get(position),
                        prize
                    )
                }
            }
    }

    private fun getPendingWinners(uid: String?) {
        if (uid != null) {
            Constants.GetFireStoneDb().collection("PendingUsers")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        for (document in task.result!!) {
                            pWinners = document.data
                            val userID = pWinners.get("UserID").toString()

                            Constants.GetRef().child("Users").child(userID).get()
                                .addOnSuccessListener { dataSnapshot ->
                                    val userModel = dataSnapshot.getValue(UserModel::class.java)

                                    if (userModel != null) {
                                        pWinnersList.add(userModel)
                                        Toast.makeText(
                                            this,
                                            "" + pWinnersList.size,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            pb_WinnersDash.visibility = View.INVISIBLE
                        }
                    } else {

                    }

                }
        }
    }

    fun addWinners(model: UserModel, id: String, nPrize: String) {
        val userData_ = mutableMapOf<String, String>()
            userData_["userName"] = model.name.toString()
        userData_["prize"] = nPrize

          val userData_MyPrize = mutableMapOf<String, String>()
        userData_MyPrize["date"] = Constants.getCurrentTimestamp().toString()
        userData_MyPrize["prize"] = nPrize

        //Winners
        Constants.GetFireStoneDb().collection("WinnerUsers").document(id)
            .set(userData_)

        //My Prize
        Constants.GetFireStoneDb().collection(id).add(userData_MyPrize)

        //User Data
        Constants.GetRef().child("Users").child(id).child("prize").setValue(nPrize)

        //Delete Pending
        Constants.GetFireStoneDb().collection("PendingUsers").document(id)
            .delete()

        pb_WinnersDash.visibility = View.INVISIBLE
        cl_winnersDash.visibility = View.VISIBLE

        Toast.makeText(
            this@WinnersDashboardActivity,
            "تم منح الجائزة.",
            Toast.LENGTH_SHORT
        ).show()

        recreate()
    }
}
