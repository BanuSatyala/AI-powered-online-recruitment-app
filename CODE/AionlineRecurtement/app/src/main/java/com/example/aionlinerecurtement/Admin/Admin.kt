package com.example.aionlinerecurtement.Admin

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.aionlinerecurtement.LoginPage
import com.example.aionlinerecurtement.R

class Admin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        findViewById<Button>(R.id.appCompatButton).setOnClickListener {
            startActivity(Intent(this,AddCompany::class.java))
        }
        findViewById<Button>(R.id.appCompatButton2).setOnClickListener {
            startActivity(Intent(this,ViewAll::class.java))
        }
        findViewById<Button>(R.id.logout).setOnClickListener {
            AlertDialog.Builder(this).apply {
                setCancelable(false)
                setTitle("Do You Want Log out???")
                setPositiveButton("Yes") { dialog, which ->
                    dialog.dismiss()
                    getSharedPreferences("user", MODE_PRIVATE).edit().clear().apply()
                    startActivity(Intent(this@Admin, LoginPage::class.java))
                    finishAffinity()
                }
                setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                show()
            }
        }
    }
}