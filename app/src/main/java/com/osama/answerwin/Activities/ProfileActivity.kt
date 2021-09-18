package com.osama.answerwin.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.github.ybq.android.spinkit.SpinKitView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.osama.answerwin.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity() {
    private lateinit var spin_kit_QS: SpinKitView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        spin_kit_QS = findViewById(R.id.spin_kit_QS_profile)
        val userId = mAuth!!.currentUser!!.uid

        spin_kit_QS.visibility = View.VISIBLE
        clProfile.visibility = View.GONE

        mDatabaseReference?.child("Users")?.child(userId)
            ?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    tvJewelsP.text = snapshot.child("jewels").value.toString()
                    tvPointsP.text = snapshot.child("points").value.toString()
                    tvName.text = snapshot.child("name").value.toString()
                    tvEmail.text = snapshot.child("email").value.toString()
                    tvPhone.text = snapshot.child("phone").value.toString()
                    tvStatus.text = "الحالة: " + snapshot.child("status").value.toString()

                    spin_kit_QS.visibility = View.GONE
                    clProfile.visibility = View.VISIBLE
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        applicationContext, "فشل استقبال البيانات.",
                        Toast.LENGTH_SHORT
                    ).show()
                    spin_kit_QS.visibility = View.GONE
                    clProfile.visibility = View.VISIBLE

                }
            })

        ivBackP.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}