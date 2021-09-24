package com.osama.answerwin.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import carbon.widget.Button
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.osama.answerwin.R
import com.osama.answerwin.Utils.Constants
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initialise()

        tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                message_loginIn.visibility = View.GONE

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                message_loginIn.visibility = View.GONE

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


    }

    private fun initialise() {
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.buLogin)
        btnLogin.setOnClickListener {
            clLogin.visibility = View.GONE
            spin_kit_QS.visibility = View.VISIBLE
            loginUser()
        }

    }

    private fun loginUser() {
        email = etEmail.text.toString()
        password = etPassword.text.toString()
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        spin_kit_QS.visibility = View.GONE
                        // Sign in success, update UI with signed-in user's information
                        Toast.makeText(
                            this, "تم تسجيل الدخول بنجاح.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(this)
                    } else {
                        spin_kit_QS.visibility = View.GONE
                        clLogin.visibility = View.VISIBLE
                        // If sign in fails, display a message to the user.
                        message_loginIn.text =
                            "* البريد الالكتروني او كلمه المرور التي ادخلتهم غير صحيحه, من فضل اعد الادخل."
                        message_loginIn.visibility = View.VISIBLE
                    }
                }
        } else if (TextUtils.isEmpty(email)) {
            spin_kit_QS.visibility = View.GONE
            clLogin.visibility = View.VISIBLE
            Toast.makeText(this, "أدخل بريدك الالكتروني.", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(password)) {
            spin_kit_QS.visibility = View.GONE
            clLogin.visibility = View.VISIBLE
            Toast.makeText(this, "أدخل كلمة مرورك.", Toast.LENGTH_SHORT).show()
        } else {
            spin_kit_QS.visibility = View.GONE
            clLogin.visibility = View.VISIBLE
            Toast.makeText(this, "أدخل البيانات الناقصة.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(context: Context) {
        Constants.GetAuth().currentUser?.let {
            mDatabaseReference?.child("Users")?.child(it.uid)
                ?.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.child("role").value.toString() == "admin"){
                            val intent = Intent(context, AdminHomeActivity::class.java)
                            startActivity(intent)
                            finishAffinity()
                        }else{
                            val intent = Intent(context, HomeActivity::class.java)
                            startActivity(intent)
                            finishAffinity()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            applicationContext, "فشل استقبال البيانات.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                })
        };
    }
}