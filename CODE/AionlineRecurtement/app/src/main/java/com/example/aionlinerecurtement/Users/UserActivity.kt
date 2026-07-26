package com.example.aionlinerecurtement.Users

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aionlinerecurtement.LoginPage
import com.example.aionlinerecurtement.R
import com.example.aionlinerecurtement.Response.JobsResponse
import com.example.aionlinerecurtement.Response.ReTrofit
import com.example.aionlinerecurtement.UserJobAdapter
import com.example.aionlinerecurtement.toast
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserActivity : AppCompatActivity() {
    lateinit var cycle:RecyclerView
    lateinit var p:ProgressBar
    lateinit var actionBarDrawerToggle:ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
cycle=findViewById(R.id.cycle)
        cycle.layoutManager=LinearLayoutManager(this)
         p=findViewById(R.id.progress2)
        cycle.isVisible=false
        p.isVisible=true
        val drawer=findViewById<DrawerLayout>(R.id.drwable)
        val view=findViewById<NavigationView>(R.id.navigationview)

        actionBarDrawerToggle=ActionBarDrawerToggle(this,drawer,R.string.opens,R.string.closes)
        drawer.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
view.setNavigationItemSelectedListener {
    when(it.itemId){
        R.id.jobs->{
drawer.closeDrawers()
           }
        R.id.applied->{
            startActivity(Intent(this,ViewJobApplies::class.java))
    drawer.closeDrawers()
        }
        R.id.logout2->{

            AlertDialog.Builder(this).apply {
                setTitle("Do you want to Logout??")
                setCancelable(false)
                setPositiveButton("Yes") { dialogInterface, i ->
                    dialogInterface.dismiss()
                    getSharedPreferences("user", MODE_PRIVATE).edit().clear().apply()
                    finishAffinity()
                    startActivity(Intent(this@UserActivity, LoginPage::class.java))
                }
                setNegativeButton("No"){dialog,_->
                    dialog.dismiss()
                }
                drawer.closeDrawers()
                show()
            }

                }

}
    true
}
        datad()




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
 actionBarDrawerToggle.onOptionsItemSelected(item)
            return true

    }



    fun datad(){
        CoroutineScope(IO).launch {
            ReTrofit.instance.View_companies().enqueue(object :Callback<JobsResponse>{
                override fun onResponse(call: Call<JobsResponse>, response: Response<JobsResponse>) {
                    cycle.isVisible=true
                    p.isVisible=false
                    cycle.adapter=UserJobAdapter(this@UserActivity, response.body()!!.data)
                }

                override fun onFailure(call: Call<JobsResponse>, t: Throwable) {
                    if(t.message=="timeout"){
datad()
                    }else {
                        cycle.isVisible = true
                        p.isVisible = false
                        cycle.toast(t.message!!)
                    }}
            })
        }
    }
}