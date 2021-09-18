package com.osama.answerwin.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.osama.answerwin.Models.UserModel
import com.osama.answerwin.R
import com.osama.answerwin.Utils.Constants
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity() {
    //Var
    private lateinit var spin_kit_QS: SpinKitView
    lateinit var userModel: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        spin_kit_QS = findViewById(R.id.spin_kit_QS_profile)
        val userId = mAuth!!.currentUser!!.uid

        spin_kit_QS.visibility = View.VISIBLE
        clProfile.visibility = View.GONE

        Constants.GetRef().child("Users").child(userId).get().addOnSuccessListener { dataSnapshot ->
            val userModel = dataSnapshot.getValue(UserModel::class.java)
            if (userModel != null) {
                tvJewelsP.text = userModel.jewels
                tvPointsP.text = userModel.points
                tvName.text = userModel.name
                tvEmail.text = userModel.email
                tvPhone.text = userModel.phone
                tvStatus.text = "الحالة: " + userModel.status

                spin_kit_QS.visibility = View.GONE
                clProfile.visibility = View.VISIBLE
            } else {

                Toast.makeText(
                    applicationContext, "فشل استقبال البيانات.",
                    Toast.LENGTH_SHORT
                ).show()
                spin_kit_QS.visibility = View.GONE
                clProfile.visibility = View.VISIBLE

            }

        }

//        mDatabaseReference?.child("Users")?.child(userId)
//            ?.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//
//
////                    tvJewelsP.text = snapshot.child("jewels").value.toString()
////                    tvPointsP.text = snapshot.child("points").value.toString()
////                    tvName.text = snapshot.child("name").value.toString()
////                    tvEmail.text = snapshot.child("email").value.toString()
////                    tvPhone.text = snapshot.child("phone").value.toString()
////                    tvStatus.text = "الحالة: " + snapshot.child("status").value.toString()
////
//                    spin_kit_QS.visibility = View.GONE
//                    clProfile.visibility = View.VISIBLE
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Toast.makeText(
//                        applicationContext, "فشل استقبال البيانات.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    spin_kit_QS.visibility = View.GONE
//                    clProfile.visibility = View.VISIBLE
//
//                }
//            })

        ivBackP.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}