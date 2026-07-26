package com.example.aionlinerecurtement.Response

import android.os.Message
import com.example.aionlinerecurtement.Models.Resume

data class ResumeResponse (val error: Boolean,var message: String,var data:ArrayList<Resume>)