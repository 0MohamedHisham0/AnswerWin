package com.osama.answerwin.Activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.osama.answerwin.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.dialog_enter_bool.*
import kotlinx.android.synthetic.main.dialog_points_win.*

class HomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //Home

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