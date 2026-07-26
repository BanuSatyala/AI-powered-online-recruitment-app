package com.example.aionlinerecurtement.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.aionlinerecurtement.R
import com.example.aionlinerecurtement.Response.CommonResponse
import com.example.aionlinerecurtement.Response.ReTrofit
import com.example.aionlinerecurtement.databinding.ActivityAddCompanyBinding
import com.example.aionlinerecurtement.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCompany : AppCompatActivity() {
    private lateinit var bind:ActivityAddCompanyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityAddCompanyBinding.inflate(layoutInflater)
        setContentView(bind.root)



        bind.add.setOnClickListener {
            val name=bind.cname.text.toString().trim()
            val mail=bind.cemail.text.toString().trim()
            val password=bind.cpassword.text.toString().trim()
            val address=bind.address.text.toString().trim()
            val mobile=bind.mobilenumber.text.toString().trim()
            if(name.isEmpty()){
it.toast("Please Enter Your name")
            }else if(mail.isEmpty()){
it.toast("Please Enter Your mail")
            }else if(password.isEmpty()){
it.toast("Please Enter Your password")
            }else if(address.isEmpty()){
it.toast("Please Enter Your address")
            }else if(mobile.isEmpty()){
it.toast("Please Enter Your mobile")
            }else{
                bind.progrees2.isVisible=true
                bind.layout2.isVisible=false
                CoroutineScope(IO).launch {
                    ReTrofit.instance.add_company(name,mobile,mail,password,address,"company").enqueue(object :Callback<CommonResponse>{
                        override fun onResponse(call: Call<CommonResponse>,response: Response<CommonResponse>) {
                            it.toast(response.body()!!.message)
                            if(response.body()!!.message=="successfully Created"){
                                finish()
                            }else{
                                bind.progrees2.isVisible=false
                                bind.layout2.isVisible=true
                            }
                        }

                        override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                        it.toast(t.message!!)
                            bind.progrees2.isVisible=false
                            bind.layout2.isVisible=true
                        }
                    })
                }
            }
        }

    }

}