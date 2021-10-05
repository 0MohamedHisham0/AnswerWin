package com.osama.answerwin.Activities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

open class BaseActivity : AppCompatActivity() {
    var mDatabaseReference: DatabaseReference? = null
    var mDatabase: FirebaseDatabase? = null
    var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (checkInternetConnection(this)){
            mAuth = FirebaseAuth.getInstance()
            mDatabaseReference = FirebaseDatabase.getInstance().reference
        }
        else
        {
            Toast.makeText(this, "لا يوجد اتصال بالانترنت, تأكد من اتصالك", Toast.LENGTH_SHORT).show()
        }

    }

    private fun checkInternetConnection(context: Context): Boolean {
        val connectivity = context
            .getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity == null) {
            return false
        } else {
            val info = connectivity.allNetworkInfo
            if (info != null) {
                for (i in info.indices) {
                    if (info[i].state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
                }
            }
        }
        return false
    }

}