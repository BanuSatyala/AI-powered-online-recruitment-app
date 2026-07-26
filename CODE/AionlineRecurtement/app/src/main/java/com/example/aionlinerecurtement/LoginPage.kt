package com.example.aionlinerecurtement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import androidx.core.view.isVisible
import com.example.aionlinerecurtement.Admin.Admin
import com.example.aionlinerecurtement.Company.CompanyActivity
import com.example.aionlinerecurtement.Response.LoginResponse
import com.example.aionlinerecurtement.Response.ReTrofit
import com.example.aionlinerecurtement.Users.UserActivity
import com.example.aionlinerecurtement.databinding.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response

class LoginPage : AppCompatActivity() {
private   lateinit var bind:ActivityLoginPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(bind.root)
        bind.register.setOnClickListener{
             startActivity(Intent(this,SignUp::class.java))
        }
        bind.btn.setOnClickListener{
            val email=bind.email.text.toString()
            val password=bind.password.text.toString()
            if(email.isEmpty()){
                it.toast("Please Enter Your Email")
            }else if(password.isEmpty()){
                it.toast("Please Enter Your Password")
            }else if(email.lowercase()=="admin"&&password.lowercase()=="admin"){
                getSharedPreferences("user", MODE_PRIVATE).edit().putString("type","admin").apply()
startActivity(Intent(this, Admin::class.java))
                finishAffinity()
            }else{

                bind.progress.isVisible=true
                bind.card1.isVisible=false
                bind.linearLayout.isVisible=false
bind.register.isVisible=false
                CoroutineScope(IO).launch {
                    ReTrofit.instance.login("login",email,password).enqueue(object :Callback<LoginResponse>{
                        override fun onResponse(
                            call: retrofit2.Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                        val data=response.body()!!.data
                            if(data.isNotEmpty()){
                                val data2=data[0]

                                getSharedPreferences("user", MODE_PRIVATE).edit().apply{
                                    putString("id",data2.id.toString())
                                    putString("name",data2.name)
                                    putString("mail",data2.mail)
                                    putString("password",data2.password)
                                    putString("mobile",data2.mobile)
                                    putString("type",data2.type)

                                    apply()
                                }

                                if(data2.type=="user"){
                                    startActivity(Intent(this@LoginPage,UserActivity::class.java))
                                }else if(data2.type=="company"){
                                    startActivity(Intent(this@LoginPage,CompanyActivity::class.java))
                                }
finishAffinity()
                            }else{
                                bind.progress.isVisible=false
                                bind.card1.isVisible=true
                                bind.linearLayout.isVisible=true
                                bind.register.isVisible=true
                                it.toast(response.body()!!.message)
                            }


                        }

                        override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                            it.toast(t.message!!)
                            bind.progress.isVisible=false
                            bind.card1.isVisible=true
                            bind.linearLayout.isVisible=true
                            bind.register.isVisible=true
                        }
                    })
                }
            }
        }
    }
}