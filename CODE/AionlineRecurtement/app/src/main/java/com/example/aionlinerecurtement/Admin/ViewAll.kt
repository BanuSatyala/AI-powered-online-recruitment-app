package com.example.aionlinerecurtement.Admin

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aionlinerecurtement.R
import com.example.aionlinerecurtement.Response.ReTrofit
import com.example.aionlinerecurtement.Response.ResumeResponse
import com.example.aionlinerecurtement.Users.AdapterShowCompany
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response

class ViewAll : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all)
        val cycle=findViewById<RecyclerView>(R.id.cycle3)
        cycle.layoutManager=LinearLayoutManager(this)
val p=ProgressDialog(this)
        p.setCancelable(false)
        p.setTitle("Loading........")
        p.show()
        CoroutineScope(IO).launch {
            ReTrofit.instance.view_details().enqueue(object :Callback<ResumeResponse>{
                override fun onResponse(call: retrofit2.Call<ResumeResponse>, response: Response<ResumeResponse>) {
                cycle.adapter=AdapterShowCompany(this@ViewAll,response.body()!!.data)
                    p.dismiss()
                }

                override fun onFailure(call: retrofit2.Call<ResumeResponse>, t: Throwable) {
                p.dismiss()
                    Toast.makeText(this@ViewAll, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}