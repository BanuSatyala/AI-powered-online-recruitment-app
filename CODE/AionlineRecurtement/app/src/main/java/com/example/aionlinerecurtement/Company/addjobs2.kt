package com.example.aionlinerecurtement.Company

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.BaseBundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.core.view.isVisible
import com.example.aionlinerecurtement.R
import com.example.aionlinerecurtement.Response.CommonResponse
import com.example.aionlinerecurtement.Response.ReTrofit
import com.example.aionlinerecurtement.databinding.AddJobsBinding
import com.example.aionlinerecurtement.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class addjobs2 : AppCompatActivity() {
    var encoded=""
    private lateinit var bind:AddJobsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= AddJobsBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.click.setOnClickListener{
            val int=Intent(Intent.ACTION_GET_CONTENT)
            int.setType("image/*")
            startActivityForResult(int,0)
        }


        bind.create.setOnClickListener {
            val jtitle=bind.jobtitle.text.toString().trim()
            val discirption=bind.discription.text.toString().trim()
            val role=bind.role.text.toString().trim()
            val skills=bind.skills.text.toString().trim()
            if(jtitle.isEmpty()){
                it.toast("Please Enter the Job title")
            }else if(discirption.isEmpty()){
                it.toast("Please Enter the Discirption")
            }else if(role.isEmpty()){
                it.toast("Please Enter the Job Role")
            }else if(skills.isEmpty()){
                it.toast("Please Enter the Skills Fields")
            }else{
                bind.progrees3.isVisible=true
                bind.liner2.isVisible=false
                val id=getSharedPreferences("user", MODE_PRIVATE).getString("id","")!!
CoroutineScope(IO).launch {
    ReTrofit.instance.add_jobs(jtitle,discirption,role,skills,id,encoded).enqueue(object :Callback<CommonResponse>{
        override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
            val kd=response.body()!!.message
            it.toast(kd)
            if(kd=="success"){
                finish()
            }else{
                bind.progrees3.isVisible = false
                bind.liner2.isVisible = true
            }
        }

        override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
       it.toast(t.message!!)
            bind.progrees3.isVisible = false
            bind.liner2.isVisible = true
        }
    })
}
            }

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    if(data!=null){
        val image=data.data.toString()
        val bitmap=MediaStore.Images.Media.getBitmap(contentResolver,Uri.parse(image))
        bind.click.setImageBitmap(bitmap)

        val output=ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,100,output)
        encoded=Base64.encodeToString(output.toByteArray(),Base64.NO_WRAP)
    }
    }
}