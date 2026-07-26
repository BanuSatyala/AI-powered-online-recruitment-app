package com.example.aionlinerecurtement

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import java.io.InputStream
import java.lang.Exception
import java.net.URL
@SuppressLint("StaticFieldLeak")
class OnlineImage(var imageView: ImageView) : AsyncTask<String, Void, Bitmap>() {
    override fun doInBackground(vararg p0: String?): Bitmap {

        var bitmap: Bitmap? =null
        val input:InputStream
        try {
            input= URL("${p0[0]}").openStream()
        bitmap=BitmapFactory.decodeStream(input)
        }catch (e:Exception){
        Log.i("errorr","${e.message}")
        }
        return bitmap!!
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)

    imageView.setImageBitmap(result)
    }

}