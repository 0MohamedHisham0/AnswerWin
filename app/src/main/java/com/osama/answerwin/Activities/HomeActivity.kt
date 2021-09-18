package com.osama.answerwin.Activities

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.github.ybq.android.spinkit.SpinKitView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.osama.answerwin.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.dialog_enter_bool.*
import kotlinx.android.synthetic.main.dialog_points_win.*

class HomeActivity : BaseActivity() {
    private lateinit var spin_kit_QS: SpinKitView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //Home

        spin_kit_QS = findViewById(R.id.spin_kit_QS_home)

        spin_kit_QS.visibility = View.VISIBLE
        clHome.visibility = View.GONE

        val userId = mAuth!!.currentUser!!.uid

        mDatabaseReference = FirebaseDatabase.getInstance().reference

        mDatabaseReference?.child("Users")?.child(userId)?.get()?.addOnSuccessListener {

            tvJewels.text = it.child("jewels").value.toString()
            tvPoints.text = it.child("points").value.toString()
            spin_kit_QS.visibility = View.GONE
            clHome.visibility = View.VISIBLE

        }?.addOnFailureListener{
            Toast.makeText(this, "فشل استقبال البيانات.",
                    Toast.LENGTH_SHORT).show()
            spin_kit_QS.visibility = View.GONE
            clHome.visibility = View.VISIBLE
        }



        buLogout.setOnClickListener {
            mAuth?.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }

        buAnsAndWin.setOnClickListener {
            startActivity(Intent(this, Questions_Screen::class.java))
        }

        buEnterToWin.setOnClickListener {
            openDialogDetail()
        }

    }

    private fun openDialogDetail() {
        val dialog = Dialog(this) // Context, this, etc.
        dialog.setContentView(R.layout.dialog_enter_bool)

        dialog.buStartBool.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this, Questions_Screen::class.java)
            intent.putExtra("path","Bool")
            startActivity(intent)

        }
        dialog.setTitle("EnterBool")
        dialog.show()
        dialog.setCanceledOnTouchOutside(true)
    }

}