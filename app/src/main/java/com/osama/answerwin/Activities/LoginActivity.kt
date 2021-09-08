package com.osama.answerwin.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import carbon.widget.Button
import com.osama.answerwin.R
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
                            Toast.makeText(this, "تم تسجيل الدخول بنجاح.",
                                    Toast.LENGTH_SHORT).show()
                            updateUI()
                        }else {
                            spin_kit_QS.visibility = View.GONE
                            clLogin.visibility = View.VISIBLE
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this, "فشل تسجيل الدخول.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
        }else if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "أدخل بريدك الالكتروني.", Toast.LENGTH_SHORT).show()
        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "أدخل كلمة مرورك.", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "أدخل البيانات الناقصة.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}