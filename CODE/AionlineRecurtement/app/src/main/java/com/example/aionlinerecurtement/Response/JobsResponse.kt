package com.example.aionlinerecurtement.Response

import com.example.aionlinerecurtement.Models.Jobs
import kotlinx.coroutines.Job

data class JobsResponse (
    var error:Boolean,
    var message:String,
    var data:ArrayList<Jobs>
        )