package com.osama.answerwin.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.osama.answerwin.R
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Register
        buRegister.setOnClickListener {
            finish()
        }

    }
}