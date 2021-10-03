package com.osama.answerwin.Activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.osama.answerwin.Models.UserModel
import com.osama.answerwin.R
import com.osama.answerwin.Utils.Constants
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.dialog_enter_bool.*
import kotlinx.android.synthetic.main.dialog_watch_win.*

class HomeActivity : BaseActivity() {

    lateinit var mAdView: AdView
    private var mRewardedAd: RewardedAd? = null
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var spin_kit_QS: SpinKitView
    val adRequest = AdRequest.Builder().build()
    //Var

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //Home

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer)
        val navigationView: NavigationView = findViewById(R.id.navView)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.howToPlay -> openWelcomeDialog(this)
                R.id.winners -> startActivity(Intent(this, WinnersActivity::class.java))
                R.id.prizes -> startActivity(Intent(this, MyPrize::class.java))
                R.id.facebookPage -> facebookPage()
            }
            true
        }

        ivmenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        mAdView.loadAd(adRequest)

        clWatchAndWin.setOnClickListener {
            RewardedAd.load(
                this,
                getString(R.string.rewarded_ad_unit_id),
                adRequest,
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        Toast.makeText(
                            applicationContext, "فشل تحميل الاعلان.",
                            Toast.LENGTH_SHORT
                        ).show()
                        mRewardedAd = null
                    }

                    override fun onAdLoaded(rewardedAd: RewardedAd) {
                        Toast.makeText(
                            applicationContext, "تم تحميل الاعلان.",
                            Toast.LENGTH_SHORT
                        ).show()
                        mRewardedAd = rewardedAd
                    }
                })

            openDialogWatch()
        }

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

                    if (snapshot.child("role").value.toString() == "admin") {
                        ivBackP_Home.visibility = View.VISIBLE
                    }

                    spin_kit_QS.visibility = View.GONE
                    clHome.visibility = View.VISIBLE
                }

                override fun onCancelled(error: DatabaseError) {
                    spin_kit_QS.visibility = View.GONE
                    clHome.visibility = View.VISIBLE
                }


            })

        //button CLicks
        buLogout.setOnClickListener {
            mAuth?.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }

        buAnsAndWin.setOnClickListener {
            startActivity(Intent(this, Questions_Screen::class.java))
            finish()
        }

        buEnterToWin.setOnClickListener {
            Constants.GetRef().child("Users").child(userId).get()
                .addOnSuccessListener { dataSnapshot ->
                    val userModel = dataSnapshot.getValue(UserModel::class.java)
                    if (userModel != null) {
                        if (userModel.status == "داخل السحب") {
                            toast("انت داخل السحب بالفعل")
                        } else {
                            openDialogDetail()
                        }
                    }

                }
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

        ivBackP_Home.setOnClickListener {
            startActivity(Intent(this, AdminHomeActivity::class.java))
            finish()
        }

    }

    private fun openDialogDetail() {
        val dialog = Dialog(this) // Context, this, etc.
        dialog.setContentView(R.layout.dialog_enter_bool)

        dialog.buStartBool.setOnClickListener {

            Constants.GetAuth().currentUser?.uid?.let {
                Constants.GetRef().child("Users").child(it).get()
                    .addOnSuccessListener { dataSnapshot ->
                        val userModel = dataSnapshot.getValue(UserModel::class.java)
                        var jewels: String? = userModel?.jewels.toString()
                        var jewelsInt: Int? = jewels?.toInt()

                        if (jewelsInt != null) {
                            if (jewelsInt >= 1) {
                                //You have enough jewels to go
                                Constants.SubValueFromJewel(1, applicationContext)

                                dialog.dismiss()
                                val intent =
                                    Intent(applicationContext, Questions_Screen::class.java)
                                intent.putExtra("path", "Bool")
                                startActivity(intent)
                                finishAffinity()

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
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    private fun openDialogWatch() {
        val dialog = Dialog(this) // Context, this, etc.
        dialog.setContentView(R.layout.dialog_watch_win)

        dialog.bu_dialogWin.setOnClickListener {

            mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    Toast.makeText(
                        applicationContext, "تم فتح الاعلان.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                    // Called when ad fails to show.
                    Toast.makeText(
                        applicationContext, "فشل فتح الاعلان.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    // Set the ad reference to null so you don't show the ad a second time.
                    Constants.GetAuth().currentUser?.uid?.let {
                        Constants.GetRef().child("Users").child(it).get()
                            .addOnSuccessListener { dataSnapshot ->
                                Constants.AddValueTOPoints(5, this@HomeActivity)
                                Toast.makeText(
                                    applicationContext, "تم استلام الجائزة.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                    dialog.dismiss()
                    mRewardedAd = null
                }
            }

            if (mRewardedAd != null) {
                mRewardedAd?.show(this, OnUserEarnedRewardListener {
                    fun onUserEarnedReward(rewardItem: RewardItem) {
                        var rewardAmount = rewardItem.amount
                        var rewardType = rewardItem.type
                        Constants.GetAuth().currentUser?.uid?.let {
                            Constants.GetRef().child("Users").child(it).get()
                                .addOnSuccessListener { dataSnapshot ->
                                    Constants.AddValueTOPoints(5, this)
                                    Toast.makeText(
                                        applicationContext, "تم استلام الجائزة.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    }
                })
            } else {
                Toast.makeText(
                    applicationContext, "لا توجد اعلانات حاليا للمشاهدة.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        dialog.setTitle("Watch&Win")
        dialog.show()
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    fun toast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return true
    }

    fun openWelcomeDialog(context: Context?) {
        val dialog = Dialog(context!!) // Context, this, etc.
        dialog.setContentView(R.layout.dialog_welcome)
        val button = dialog.findViewById<Button>(R.id.bu_dialog_welcome)
        button.setOnClickListener { dialog.dismiss() }
        dialog.setTitle("Welcome")
        dialog.show()
        dialog.setCanceledOnTouchOutside(true)
    }

    fun facebookPage() {
        val uriUrl =
            Uri.parse("https://www.facebook.com/%D8%AC%D8%A7%D9%88%D8%A8-%D9%88%D8%A7%D8%B1%D8%A8%D8%AD-101808732270696/")
        val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
        startActivity(launchBrowser)
    }

}