package com.example.aionlinerecurtement.Response

import com.example.aionlinerecurtement.Models.Users


data class LoginResponse (
    var error: Boolean,
    var message:String,
    var data:ArrayList<Users>
        )