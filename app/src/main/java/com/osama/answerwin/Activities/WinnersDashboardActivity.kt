package com.osama.answerwin.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.osama.answerwin.Adapters.WinnersAdapter
import com.osama.answerwin.Adapters.WinnersDashAdapter
import com.osama.answerwin.Models.WinnersDashModel
import com.osama.answerwin.Models.WinnersModel
import com.osama.answerwin.R
import com.osama.answerwin.Utils.Constants
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_winners.*
import kotlinx.android.synthetic.main.activity_winners_dashboard.*
import kotlinx.android.synthetic.main.item_win_dash.*
import java.util.HashMap

class WinnersDashboardActivity : BaseActivity() {
    private var pWinners: Map<String, Any> = HashMap()
    private val pWinnersList = mutableListOf<WinnersDashModel>()
    lateinit var  recyclerView: RecyclerView
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

                            var model: WinnersDashModel = WinnersDashModel()
                            var userID = pWinners.get("UserID").toString()


                            mDatabaseReference = FirebaseDatabase.getInstance().reference

                            mDatabaseReference?.child("Users")?.child(userID)
                                ?.addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {

                                        model.name =snapshot.child("name").value.toString()
                                        model.phone = snapshot.child("phone").value.toString()
                                        model.points = snapshot.child("points").value.toString()
                                        model.jewels = snapshot.child("jewels").value.toString()

                                        pWinnersList.add(model)
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        Toast.makeText(
                                            applicationContext, "فشل استقبال البيانات.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }


                                });


                        }
                        initWDRV(pWinnersList)
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

    private fun initWDRV(pWinnersList: MutableList<WinnersDashModel>) {
        recyclerView = findViewById(R.id.rv_WinnersDash)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)


        recyclerView.adapter = WinnersDashAdapter(pWinnersList, this)

        cl_winnersDash.visibility = View.VISIBLE
        pb_WinnersDash.visibility = View.INVISIBLE

    }

}