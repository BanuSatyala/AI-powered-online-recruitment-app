package com.example.aionlinerecurtement

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aionlinerecurtement.Models.Jobs
import com.example.aionlinerecurtement.Users.ViewApplcaition

import com.example.aionlinerecurtement.databinding.Card1Binding

class UserJobAdapter(val context: Context,val data:ArrayList<Jobs>):RecyclerView.Adapter<UserJobAdapter.Viewed>() {
    class Viewed(val item:Card1Binding ):RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        Viewed(Card1Binding.inflate(LayoutInflater.from(context),parent,false))
    override fun onBindViewHolder(holder: Viewed, position: Int) {
        val k=data[position]
with(holder.item){
    Glide.with(context).load(Uri.parse(k.url)).into(titleimage)
    var int=1
    titleimage.setOnClickListener {
    details.isVisible=int%2==0
        int++
    }
    val text="<b>Title</b><br></br>${k.jobtitle} <br></br><b>Description</b><br></br>${k.description}<br></br>"
    details.text=HtmlCompat.fromHtml(text,FROM_HTML_MODE_LEGACY)
    details.setOnClickListener {
        val int2=Intent(context,ViewApplcaition::class.java)
        int2.putExtra("data2",k)
        context.startActivity(int2)
    }
}
    }

    override fun getItemCount()=data.size
}