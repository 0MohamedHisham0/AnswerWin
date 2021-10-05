package com.osama.answerwin.Activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.osama.answerwin.Utils.Constants

open class BaseActivity : AppCompatActivity() {
    var mDatabaseReference: DatabaseReference? = null
    var mDatabase: FirebaseDatabase? = null
    var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        mDatabaseReference = FirebaseDatabase.getInstance().reference
//        if ( Constants.checkInternetConnection(this)){
//
//        }else{
//            Toast.makeText(this, "لا يوجد اتصال بالانترنت, تأكد من اتصالك", Toast.LENGTH_SHORT).show()
//        }

    }
}