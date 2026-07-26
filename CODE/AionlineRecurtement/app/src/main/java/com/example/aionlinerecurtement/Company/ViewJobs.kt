package com.example.aionlinerecurtement.Company

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aionlinerecurtement.R
import com.example.aionlinerecurtement.Response.ReTrofit
import com.example.aionlinerecurtement.Response.ResumeResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewJobs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_jobs)
val cyclce=findViewById<RecyclerView>(R.id.cyclce)
        cyclce.layoutManager=LinearLayoutManager(this)
        val id=getSharedPreferences("user", MODE_PRIVATE).getString("id","")!!
val p=ProgressDialog(this)
        p.setTitle("Loading..........")
        p.setCancelable(false)
        p.show()
            CoroutineScope(IO).launch {
            ReTrofit.instance.viewmycomapny(id).enqueue(object :Callback<ResumeResponse>{
                override fun onResponse(
                    call: Call<ResumeResponse>,
                    response: Response<ResumeResponse>
                ) {
                   cyclce.adapter=CompanyAdapter(this@ViewJobs,response.body()!!.data)
                p.dismiss()
                }

                override fun onFailure(call: Call<ResumeResponse>, t: Throwable) {
                    Toast.makeText(this@ViewJobs, "${t.message}", Toast.LENGTH_SHORT).show()
                    p.dismiss()
                }
            })
        }
    }
}