package com.example.aionlinerecurtement

import android.view.View
import android.widget.Toast

fun View.toast(meesage:Any){
    Toast.makeText(context, "$meesage", Toast.LENGTH_SHORT).show()
}