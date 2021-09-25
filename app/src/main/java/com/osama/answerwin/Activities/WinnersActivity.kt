package com.osama.answerwin.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.osama.answerwin.Adapters.BoolUsersAdapter
import com.osama.answerwin.Adapters.WinnersAdapter
import com.osama.answerwin.Models.WinnersModel
import com.osama.answerwin.R
import com.osama.answerwin.Utils.Constants
import kotlinx.android.synthetic.main.activity_winners.*
import java.util.HashMap

class WinnersActivity : AppCompatActivity() {

    private var winners: Map<String, Any> = HashMap()
    private val winnersList = mutableListOf<WinnersModel>()
    lateinit var  recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winners)

        getWinners(Constants.GetAuth().currentUser?.uid)

        ivBackP_Winners.setOnClickListener { onBackPressed() }

    }

    private fun getWinners(uid: String?) {
        if (uid != null) {
            Constants.GetFireStoneDb().collection("WinnerUsers")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        for (document in task.result!!) {
                            winners = document.data


                            var model: WinnersModel = WinnersModel()
                            model.name = winners.get("userName").toString()
                            model.prize = winners.get("prize").toString()

                            winnersList.add(model)
                        }
                        initWRV(winnersList)
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

    private fun initWRV(winnersList: MutableList<WinnersModel>) {
        recyclerView = findViewById(R.id.rv_Winners)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)


        recyclerView.adapter =
            WinnersAdapter(winnersList, this)

        cl_winners.visibility = View.VISIBLE
        pb_Winners.visibility = View.INVISIBLE

    }

}