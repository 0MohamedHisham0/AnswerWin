package com.osama.answerwin.Activities

import android.content.Intent
import android.os.Bundle
import com.osama.answerwin.R
import kotlinx.android.synthetic.main.activity_admin_home.*

class AdminHomeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        buAdminLogout.setOnClickListener {
            mAuth?.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }

        buBooled.setOnClickListener {
            startActivity(Intent(this, BoolUsersActivity::class.java))

        }

    }
}