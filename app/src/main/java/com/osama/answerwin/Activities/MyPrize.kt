package com.osama.answerwin.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.osama.answerwin.Adapters.MyPrizeAdapter
import com.osama.answerwin.Models.myPrizeModel
import com.osama.answerwin.R
import com.osama.answerwin.Utils.Constants
import kotlinx.android.synthetic.main.activity_my_prize.*
import java.util.*

class MyPrize : BaseActivity() {

    private var myPrize: Map<String, Any> = HashMap()
    private val prizeList = mutableListOf<myPrizeModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_prize)

        getMyPrice(Constants.GetAuth().currentUser?.uid)

        ivBackP_MyPrize.setOnClickListener { onBackPressed() }
    }

    private fun getMyPrice(uid: String?) {
        if (uid != null) {
            Constants.GetFireStoneDb().collection(uid)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        for (document in task.result!!) {
                            myPrize = document.data

                            val model: myPrizeModel = myPrizeModel()
                            model.prize = myPrize.get("prize").toString()
                            model.date = myPrize.get("date").toString()

                            prizeList.add(model)

                        }
                        initRV(prizeList)
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

    private fun initRV(prizeList: MutableList<myPrizeModel>) {
        lateinit var gridLayoutManager: GridLayoutManager
        gridLayoutManager = GridLayoutManager(this, 3)
        rv_MyPrize.layoutManager = gridLayoutManager

        rv_MyPrize.adapter = MyPrizeAdapter(prizeList, this)

        cl_MyPrize.visibility = View.VISIBLE
        pb_MyPrize.visibility = View.INVISIBLE

    }

}