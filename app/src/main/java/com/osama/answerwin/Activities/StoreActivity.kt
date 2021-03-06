package com.osama.answerwin.Activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.osama.answerwin.Models.UserModel
import com.osama.answerwin.R
import com.osama.answerwin.Utils.Constants
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_store.*
import kotlinx.android.synthetic.main.activity_store.clProfile
import kotlinx.android.synthetic.main.dialog_welcome.*

class StoreActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

        val userId = mAuth!!.currentUser!!.uid

        val intent = getIntent()

        tvPointsP_Store.text = intent.getStringExtra("points")
        tvJewelsP_Store.text = intent.getStringExtra("jewels")

        ivBackP_Store.setOnClickListener {
            onBackPressed()
        }

        bu_UseThisPlan.setOnClickListener {
            getUserData(userId)

        }
    }

    private fun getUserData(userId: String) {
        Constants.GetRef().child("Users").child(userId).get().addOnSuccessListener { dataSnapshot ->
            val userModel = dataSnapshot.getValue(UserModel::class.java)
            if (userModel != null) {
                tvPointsP_Store.text = userModel.points.toString()
                tvJewelsP_Store.text = userModel.jewels.toString()

                if (userModel.points.toInt() > 60) {
                    // - 60 Form points
                    Constants.GetRef().child("Users").child(userId).child("points")
                        .setValue(((userModel.points.toInt() - 60).toString()))

                    // 1 +  jewels
                    Constants.GetRef().child("Users").child(userId).child("jewels")
                        .setValue((userModel.jewels.toInt() + 1).toString())

                    tvPointsP_Store.text = (userModel.points.toInt() - 60).toString()
                    tvJewelsP_Store.text = (userModel.jewels.toInt() + 1).toString()

                } else {
                    openDialogNoEnoughPoints(this)
                }

            } else {

                Toast.makeText(
                    applicationContext, "?????? ?????????????? ????????????????.",
                    Toast.LENGTH_SHORT
                ).show()

            }

        }
    }

    fun openDialogNoEnoughPoints(context: Context?) {
        val dialog = Dialog(context!!) // Context, this, etc.
        dialog.setContentView(R.layout.dialog_welcome)
        val button = dialog.findViewById<Button>(R.id.bu_dialog_welcome)
        button.text = "???????? ?????? ???????? ????????????????"

        button.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }

        dialog.tv_welcomeDialog.text =
            " ?????? ???? ?????????? ???????? ?????????? ???????? ?????? ???????????? ???? ???????????????? ?????????? ?????????? "
        dialog.setTitle("EnoughPoints")
        dialog.show()
        dialog.setCanceledOnTouchOutside(true)
    }

}