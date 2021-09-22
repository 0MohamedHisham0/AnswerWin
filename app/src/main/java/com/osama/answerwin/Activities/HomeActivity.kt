package com.osama.answerwin.Activities

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.osama.answerwin.Models.BooledUsers
import com.osama.answerwin.Models.UserModel
import com.osama.answerwin.R
import com.osama.answerwin.Utils.Constants
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.dialog_enter_bool.*
import kotlinx.android.synthetic.main.dialog_points_win.*
import java.util.*

class HomeActivity : BaseActivity() {

    private lateinit var spin_kit_QS: SpinKitView
    //Var

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //Home
        val userId = mAuth!!.currentUser!!.uid

        spin_kit_QS = findViewById(R.id.spin_kit_QS_home)

        spin_kit_QS.visibility = View.VISIBLE
        clHome.visibility = View.GONE


        mDatabaseReference = FirebaseDatabase.getInstance().reference

        mDatabaseReference?.child("Users")?.child(userId)
            ?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    tvJewels.text = snapshot.child("jewels").value.toString()
                    tvPoints.text = snapshot.child("points").value.toString()

                    spin_kit_QS.visibility = View.GONE
                    clHome.visibility = View.VISIBLE
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        applicationContext, "فشل استقبال البيانات.",
                        Toast.LENGTH_SHORT
                    ).show()
                    spin_kit_QS.visibility = View.GONE
                    clHome.visibility = View.VISIBLE
                }


            });

        buLogout.setOnClickListener {
            mAuth?.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }

        buAnsAndWin.setOnClickListener {
            startActivity(Intent(this, Questions_Screen::class.java))
        }

        buEnterToWin.setOnClickListener {
            openDialogDetail()
        }

        buProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        buStore.setOnClickListener {
            intent = Intent(this, StoreActivity::class.java)
            intent.putExtra("points", tvPoints.text.toString())
            intent.putExtra("jewels", tvJewels.text.toString())
            startActivity(intent)
        }

    }

    private fun openDialogDetail() {
        val dialog = Dialog(this) // Context, this, etc.
        dialog.setContentView(R.layout.dialog_enter_bool)

        dialog.buStartBool.setOnClickListener {

            Constants.GetAuth().currentUser?.uid?.let {
                Constants.GetRef().child("Users").child(it).get()
                    .addOnSuccessListener { dataSnapshot ->
                        var userModel = dataSnapshot.getValue(UserModel::class.java)
                        var jewels: String? = userModel?.jewels.toString()
                        var jewelsInt: Int? = jewels?.toInt()

                        if (jewelsInt != null) {
                            if (jewelsInt >= 1) {
                                //You have enough jewels to go
                                Constants.SubValueFromJewel(1, applicationContext)

                                dialog.dismiss()
                                val intent = Intent(this, Questions_Screen::class.java)
                                intent.putExtra("path", "Bool")
                                startActivity(intent)

                            } else {
                                //You don't have enough jewels to go
                                toast("انت لا تمتلك جواهر كافيه")
                            }
                        }
                    }
            }


        }
        dialog.setTitle("EnterBool")
        dialog.show()
        dialog.setCanceledOnTouchOutside(true)
    }

    fun getUserData(userId: String?) {
        var userModel: UserModel
        Constants.GetRef().child("Users").child(userId!!).get()
            .addOnSuccessListener { dataSnapshot ->
                userModel = dataSnapshot.getValue(UserModel::class.java)!!

            }
    }

    fun toast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    //Test
    fun GetSortedQ(context: Context?) {

        //Long tsLong = System.currentTimeMillis()/1000;
        Constants.GetFireStoneDb().collection("BoolUsers")
            .orderBy("date", Query.Direction.ASCENDING).get().addOnCompleteListener { task ->
                val list: MutableList<BooledUsers?> = ArrayList()
                if (task.isSuccessful) {
                    list.clear()
                    for (documentSnapshot in Objects.requireNonNull(task.result)!!) {
                        val user = documentSnapshot.toObject(BooledUsers::class.java)
                        list.add(user)
                    }
                    Toast.makeText(context, "" + list.size, Toast.LENGTH_SHORT).show()
                    Toast.makeText(
                        context,
                        "" + list.get(0)?.userID + "\n" + list.get(1)?.userID,
                        Toast.LENGTH_SHORT
                    ).show()


                } else {
                    Toast.makeText(context, "" + task.exception!!.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }


}