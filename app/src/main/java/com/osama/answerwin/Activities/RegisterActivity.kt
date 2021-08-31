package com.osama.answerwin.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.osama.answerwin.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        buRegister.setOnClickListener {
            finish()
        }

    }
}