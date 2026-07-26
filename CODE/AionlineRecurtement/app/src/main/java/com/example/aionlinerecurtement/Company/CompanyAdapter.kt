package com.example.aionlinerecurtement.Company

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.recyclerview.widget.RecyclerView
import com.example.aionlinerecurtement.Models.Resume
import com.example.aionlinerecurtement.Response.CommonResponse
import com.example.aionlinerecurtement.Response.LoginResponse
import com.example.aionlinerecurtement.Response.ReTrofit
import com.example.aionlinerecurtement.Users.WebView
import com.example.aionlinerecurtement.databinding.ViewcardForCompanyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyAdapter(val context: Context,val data:ArrayList<Resume>):RecyclerView.Adapter<CompanyAdapter.Viewed6>() {
    val p=ProgressDialog(context)

    class Viewed6(val item :ViewcardForCompanyBinding):RecyclerView.ViewHolder(item.root)
    override fun getItemCount()=data.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        Viewed6(ViewcardForCompanyBinding.inflate(LayoutInflater.from(context),parent,false))

    override fun onBindViewHolder(holder: Viewed6, position: Int) {
val d=data[position]
        with(holder.item){
    val text="<b>Job Id :</b>${d.id}<br></br>" +
            "<b>Applied in:</b>${d.date}<br></br>" +
            "<b>Status :</b>${d.status}<br></br>"


    details2.text=HtmlCompat.fromHtml(text,FROM_HTML_MODE_LEGACY)
    viewapplicant.setOnClickListener {
        applicatdetails(d.userid)
    }
    resume.setOnClickListener {
        val int=Intent(context,WebView::class.java)
        int.putExtra("uri",d.path)
        context.startActivity(int)
    }
}
        holder.itemView.setOnClickListener {
            AlertDialog.Builder(context).apply {
                setMessage("Do You Want Hire this Person?")
                setCancelable(false)
                setPositiveButton("Yes"){dialog,_->
                    dialog.dismiss()
                    updating(d.id,"Hired")
                }
                setNegativeButton("No"){dialog,_->
                    dialog.dismiss()
                    updating(d.id,"Rejected")
                }
                show()
            }
        }
    }

    private fun updating(id: Int, status: String) {
        p.setCancelable(false)
        p.setTitle("Loading.......")
        p.show()
CoroutineScope(IO).launch {
    ReTrofit.instance.update(id.toString(),status).enqueue(object :Callback<CommonResponse>{
        override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
  p.dismiss()
            Toast.makeText(context, response.body()!!.message, Toast.LENGTH_SHORT).show()
        }

        override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
     p.dismiss()
            Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
        }
    })
}
    }

    private fun applicatdetails(userid: String) {

        CoroutineScope(IO).launch {
    ReTrofit.instance.view_applied(userid = userid).enqueue(object :Callback<LoginResponse>{
        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
            p.dismiss()
            val data=response.body()!!.data
            if(data.isNotEmpty()){
                val f=data[0]
            val text="<b>Name :</b>${f.name}<br></br>" +
                    "<b>Mail :</b>${f.mail}<br></br>" +
                    "<b>Mobile Number:</b>${f.mobile}<br></br>"
                AlertDialog.Builder(context).apply {
                    setCancelable(false)
                    setMessage(HtmlCompat.fromHtml(text,FROM_HTML_MODE_LEGACY))
            setPositiveButton("Dial"){dialo,_->
                context.startActivity(Intent(Intent.ACTION_DIAL,Uri.parse("tel:${f.mobile}")))
                dialo.dismiss()
            }
                    setNegativeButton("Cancel"){dialog,_,->
                        dialog.dismiss()
                    }
                    show()
                }
            }
        }

        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
        p.dismiss()
            Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
        }
    })
}
    }
}