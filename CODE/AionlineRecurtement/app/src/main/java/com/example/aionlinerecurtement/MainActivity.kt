package com.example.aionlinerecurtement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.aionlinerecurtement.Admin.Admin
import com.example.aionlinerecurtement.Company.CompanyActivity
import com.example.aionlinerecurtement.Users.UserActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val image=findViewById<ImageView>(R.id.online)
    image.alpha=0f
        val type=getSharedPreferences("user", MODE_PRIVATE).getString("type","")
        image.animate().setDuration(500).alpha(1f).withEndAction {
overridePendingTransition(androidx.appcompat.R.anim.abc_fade_out,
    androidx.constraintlayout.widget.R.anim.abc_fade_out)

            if(type=="user"){
                startActivity(Intent(this,UserActivity::class.java))
            }else if(type=="company"){
                startActivity(Intent(this,CompanyActivity::class.java))
            }else if(type=="admin"){
                startActivity(Intent(this,Admin::class.java))
            }else{
                startActivity(Intent(this,LoginPage::class.java))
            }
            finish()

        }
    }
}