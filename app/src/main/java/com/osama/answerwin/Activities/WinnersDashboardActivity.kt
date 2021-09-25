package com.osama.answerwin.Activities

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.osama.answerwin.Adapters.WinnersDashAdapter
import com.osama.answerwin.Models.UserModel
import com.osama.answerwin.Models.WinnersDashModel
import com.osama.answerwin.R
import com.osama.answerwin.Utils.Constants
import kotlinx.android.synthetic.main.activity_winners_dashboard.*
import java.util.HashMap

class WinnersDashboardActivity : BaseActivity() {
    private var pWinners: Map<String, Any> = HashMap()
    var pWinnersList = mutableListOf<UserModel>()
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winners_dashboard)

        getPendingWinners(Constants.GetAuth().currentUser?.uid)

        ivBackP_WinnersDash.setOnClickListener { onBackPressed() }
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

                                        if (document == task.result!!.last()){
                                            rv_WinnersDash.adapter = WinnersDashAdapter(pWinnersList,this)
                                        }
                                    }
                                }

                        }


                    } else {
                        Toast.makeText(
                            this,
                            "" + task.exception!!.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
        }
    }


}