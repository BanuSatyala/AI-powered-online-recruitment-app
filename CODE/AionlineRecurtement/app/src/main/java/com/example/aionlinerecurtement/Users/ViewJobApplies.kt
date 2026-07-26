package com.example.aionlinerecurtement.Users

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aionlinerecurtement.R
import com.example.aionlinerecurtement.Response.ReTrofit
import com.example.aionlinerecurtement.Response.ResumeResponse
import com.google.mlkit.vision.text.Text
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ViewJobApplies : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_job_applies)
        val cycle=findViewById<RecyclerView>(R.id.cycle2)
        cycle.layoutManager=LinearLayoutManager(this)
        val p=ProgressDialog(this)
        p.setTitle("Loading.....")
        p.setCancelable(false)
        p.show()
        val id=getSharedPreferences("user", MODE_PRIVATE).getString("id","")!!
        CoroutineScope(IO).launch {
            ReTrofit.instance.view_resume_data(id=id).enqueue(object :retrofit2.Callback<ResumeResponse>{
                override fun onResponse(call: Call<ResumeResponse>, response: Response<ResumeResponse>) {
                    p.dismiss()
               cycle.adapter= AdapterShowCompany(this@ViewJobApplies,response.body()!!.data)

                }
                override fun onFailure(call: Call<ResumeResponse>, t: Throwable) {
                p.dismiss()
                }
            })
        }
    }
}