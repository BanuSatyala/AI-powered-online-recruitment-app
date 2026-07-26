package com.example.aionlinerecurtement.Company

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.example.aionlinerecurtement.LoginPage
import com.example.aionlinerecurtement.R

class CompanyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)
        findViewById<AppCompatButton>(R.id.addjobs).setOnClickListener {
            startActivity(Intent(this,addjobs2::class.java))
        }
        findViewById<AppCompatButton>(R.id.viewapplied).setOnClickListener {
            startActivity(Intent(this,ViewJobs::class.java))
        }
        findViewById<AppCompatButton>(R.id.logout1).setOnClickListener {
            AlertDialog.Builder(this).apply {
                setCancelable(false)
                setTitle("Do You Want Log out???")
                setPositiveButton("Yes") { dialog, _ ->

                    dialog.dismiss()
                    getSharedPreferences("user", MODE_PRIVATE).edit().clear().apply()
                    startActivity(Intent(this@CompanyActivity, LoginPage::class.java))
                    finishAffinity()
                }
                setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                show()
            }
        }

    }
}