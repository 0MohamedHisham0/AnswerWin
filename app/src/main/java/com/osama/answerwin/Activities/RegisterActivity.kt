package com.osama.answerwin.Activities

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import carbon.widget.Button
import com.github.ybq.android.spinkit.SpinKitView
import com.google.firebase.database.FirebaseDatabase
import com.osama.answerwin.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var rePasswordEt: EditText
    private lateinit var nameEt: EditText
    private lateinit var phoneEt: EditText
    private lateinit var signUpBtn: Button
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var rePassword: String
    private lateinit var name: String
    private lateinit var phone: String
    private lateinit var spin_kit_QS: SpinKitView
    var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        ivBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

        initialise()
    }

    private fun initialise() {
        emailEt = findViewById(R.id.etEmail)
        passwordEt = findViewById(R.id.etPassword)
        rePasswordEt = findViewById(R.id.etRetypePassword)
        nameEt = findViewById(R.id.etUsername)
        phoneEt = findViewById(R.id.etMobNo)
        signUpBtn = findViewById(R.id.buRegister)
        spin_kit_QS = findViewById(R.id.spin_kit_QS)
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase?.reference?.child("Users")
        signUpBtn.setOnClickListener {
            clRegister.visibility = View.GONE
            spin_kit_QS.visibility = View.VISIBLE
            createNewAccount()
        }
    }

    private fun createNewAccount() {
        email = emailEt.text.toString()
        password = passwordEt.text.toString()
        rePassword = rePasswordEt.text.toString()
        name = nameEt.text.toString()
        phone = phoneEt.text.toString()

        if (!TextUtils.isEmpty(email) && email.matches(emailPattern.toRegex()) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(rePassword)
                && password.equals(rePassword) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)) {
            mAuth!!
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val userId = mAuth!!.currentUser!!.uid
                            //Verify Email
                            verifyEmail()
                            //update user profile information
                            val currentUserDb = mDatabaseReference!!.child(userId)
                            currentUserDb.child("name").setValue(name)
                            currentUserDb.child("phone").setValue(phone)
                            spin_kit_QS.visibility = View.GONE
                            clRegister.visibility = View.VISIBLE
                            updateUserInfoAndUI()
                        } else {
                            // If sign in fails, display a message to the user.
                            spin_kit_QS.visibility = View.GONE
                            Toast.makeText(this, "فشل انشاء الحساب.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
        }else if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "أدخل اسمك.", Toast.LENGTH_SHORT).show()
            spin_kit_QS.visibility = View.GONE
            clRegister.visibility = View.VISIBLE
        }else if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "أدخل بريدك الالكتروني.", Toast.LENGTH_SHORT).show()
            spin_kit_QS.visibility = View.GONE
            clRegister.visibility = View.VISIBLE
        }else if (!email.matches(emailPattern.toRegex())){
            Toast.makeText(this, "البريد الالكتروني غير صحيح.", Toast.LENGTH_SHORT).show()
            spin_kit_QS.visibility = View.GONE
            clRegister.visibility = View.VISIBLE
        }else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "أدخل رقم هاتفك.", Toast.LENGTH_SHORT).show()
            spin_kit_QS.visibility = View.GONE
            clRegister.visibility = View.VISIBLE
        }else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "أدخل كلمة مرورك.", Toast.LENGTH_SHORT).show()
            spin_kit_QS.visibility = View.GONE
            clRegister.visibility = View.VISIBLE
        }else if (TextUtils.isEmpty(rePassword)) {
            Toast.makeText(this, "أعد ادخال كلمة مرورك.", Toast.LENGTH_SHORT).show()
            spin_kit_QS.visibility = View.GONE
            clRegister.visibility = View.VISIBLE
        }else if (!password.equals(rePassword)) {
            Toast.makeText(this, "كلمة المرور غير متطابقة.", Toast.LENGTH_SHORT).show()
            spin_kit_QS.visibility = View.GONE
            clRegister.visibility = View.VISIBLE
        }else {
            Toast.makeText(this, "أدخل البيانات الناقصة.", Toast.LENGTH_SHORT).show()
            spin_kit_QS.visibility = View.GONE
            clRegister.visibility = View.VISIBLE
        }
    }

    private fun updateUserInfoAndUI() {
        //start next activity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser
        mUser!!.sendEmailVerification()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,
                                "تم ارسال بريد التأكيد الى  " + mUser.email,
                                Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this,
                                "فشل ارسال بريد التأكيد.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }
}