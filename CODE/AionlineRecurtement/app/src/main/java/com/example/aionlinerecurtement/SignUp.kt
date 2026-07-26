package com.example.aionlinerecurtement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.aionlinerecurtement.Response.CommonResponse
import com.example.aionlinerecurtement.Response.ReTrofit
import com.example.aionlinerecurtement.databinding.ActivitySignUpBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUp : AppCompatActivity() {
    private lateinit var bind:ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.signup.setOnClickListener {
            val name=bind.name.text.toString().trim()
            val mail=bind.email2.text.toString().trim()
            val password=bind.password2.text.toString().trim()
            val mobile=bind.mobile.text.toString().trim()

            if(name.isEmpty()){
                it.toast("Please Enter Your Name")
            }else if(mail.isEmpty()){
                it.toast("Please Enter Your Mail")
            }else if(password.isEmpty()){
                it.toast("Please Enter Your Password")
            }else if(mobile.isEmpty()){
                it.toast("Please Enter Your Mobile Number")
            }else {
                bind.progress1.isVisible=true
                bind.liner1.isVisible=false
                CoroutineScope(IO).launch {
                    ReTrofit.instance.singup(name, mail, password, mobile,"user")
                        .enqueue(object : Callback<CommonResponse> {
                            override fun onResponse(
                                call: Call<CommonResponse>,
                                response: Response<CommonResponse>
                            ) {
                                val k=response.body()!!.message
                                it.toast(k)
                                if(k=="successfully Created"){
                                    finish()
                                }
                                bind.progress1.isVisible=false
                                bind.liner1.isVisible=true
                            }

                            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                                it.toast(t.message!!)
                                bind.progress1.isVisible=false
                                bind.liner1.isVisible=true
                            }
                        })
                }
            }
        }
    }
}