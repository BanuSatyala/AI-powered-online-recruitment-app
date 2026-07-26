package com.example.aionlinerecurtement.Users

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.recyclerview.widget.RecyclerView
import com.example.aionlinerecurtement.Models.Resume
import com.example.aionlinerecurtement.Response.JobsResponse
import com.example.aionlinerecurtement.Response.ReTrofit
import com.example.aionlinerecurtement.databinding.CarduserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdapterShowCompany(val context: Context,val data:ArrayList<Resume>):RecyclerView.Adapter<AdapterShowCompany.Viewed2>() {
    class Viewed2 (val item:CarduserBinding):RecyclerView.ViewHolder(item.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        Viewed2(CarduserBinding.inflate(LayoutInflater.from(context),parent,false))
    override fun onBindViewHolder(holder: Viewed2, position: Int) {
val k=data[position]
        with(holder.item){
    val text="<b>Job id :</b>${k.id}<br></br>"+
            "<b>Date Applied:</b>${k.date}<br></br>"+"<b>Status : </b>${k.status}"
            items.text=HtmlCompat.fromHtml(text,FROM_HTML_MODE_LEGACY)
            doc.setOnClickListener {
                val int=Intent(context,WebView::class.java)
                int.putExtra("uri",k.path)
                context.startActivity(int)
            }
            jobs.setOnClickListener { 
                jobsdetails(k.workid)
            }
}
    }

    private fun jobsdetails(workid: String) {
        val p=ProgressDialog(context)
        p.setCancelable(false)
        p.setTitle("Loading.....")
        p.show()
CoroutineScope(IO).launch {
    ReTrofit.instance.viewdetails(jobid=workid).enqueue(object :Callback<JobsResponse>{
        override fun onResponse(call: Call<JobsResponse>, response: Response<JobsResponse>) {
            p.dismiss()
            val data=response.body()!!.data
            if(data.isNotEmpty()) {
                val f=data[0]
                val text="<b>Job Title :</b>${f.jobtitle}<br></br>" +
                        "<b>Job Description :</b>${f.description}<br></br>" +
                        "<b>Job role :</b>${f.roles}<br></br>"

                AlertDialog.Builder(context).apply {
                    setMessage(HtmlCompat.fromHtml(text, FROM_HTML_MODE_LEGACY))
                setPositiveButton("Cancel"){dialog,_->
                    dialog.dismiss()
                }
                    show()
                }
            }
        }

        override fun onFailure(call: Call<JobsResponse>, t: Throwable) {
        p.dismiss()
        }
    })
}
    }

    override fun getItemCount()=data.size
}