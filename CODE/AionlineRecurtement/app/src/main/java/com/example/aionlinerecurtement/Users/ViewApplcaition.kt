package com.example.aionlinerecurtement.Users

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.FileUtils

import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.aionlinerecurtement.Models.Jobs
import com.example.aionlinerecurtement.Response.CommonResponse
import com.example.aionlinerecurtement.Response.ReTrofit
import com.example.aionlinerecurtement.databinding.ActivityViewApplcaitionBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class ViewApplcaition : AppCompatActivity() {
    val textrecog=ArrayList<String>()
    lateinit var data:Jobs
    private lateinit var bind:ActivityViewApplcaitionBinding
    lateinit var filepath:Uri
    @RequiresApi(Build.VERSION_CODES.R)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityViewApplcaitionBinding.inflate(layoutInflater)
        setContentView(bind.root)

        data= intent.getParcelableExtra("data2")!!


        val text="<b>Title</b><br></br>${data.jobtitle} <br></br>"
        val text2="<b>Description</b><br></br>${data.description} <br></br>"

        with(bind){
            Glide.with(this@ViewApplcaition).load(Uri.parse(data.url)).into(imageView)
            title.text=HtmlCompat.fromHtml(text,FROM_HTML_MODE_LEGACY)
            desription.text=HtmlCompat.fromHtml(text2, FROM_HTML_MODE_LEGACY)

            cam.setOnClickListener {
                if(ActivityCompat.checkSelfPermission(this@ViewApplcaition,android.Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){ requestPermissions(arrayOf(android.Manifest.permission.CAMERA),100) }else{ startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE),100) }
            }
            appCompatButton3.setOnClickListener {
                if(ActivityCompat.checkSelfPermission(this@ViewApplcaition,android.Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED||
                    ActivityCompat.checkSelfPermission(this@ViewApplcaition,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE),100)
                }else {
                 val k=appCompatButton3.text.toString()
                    if(k=="Apply") {
                        val int = Intent(Intent.ACTION_GET_CONTENT)
                        int.type = "*/*"
                        startActivityForResult(int, 1001)
                    }else {
                        uploadpdf(filepath)
                    }

                }
            }
        }

    }



    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun uploadpdf(path: Uri) {
val pdfname=Calendar.getInstance().timeInMillis
        val real=getRealFilePath(this,path)
        val pat=File(real!!)
        val p=ProgressDialog(this)
        p.setCancelable(false)
        p.setTitle("Uploading.......")
        p.show()
        val data1=SimpleDateFormat("MM-dd-yyyy hh:mm:ss")
        val shared=getSharedPreferences("user", MODE_PRIVATE)
        val requestbody=RequestBody.create(MediaType.parse("*/*"),pat)
        val multipart=MultipartBody.Part.createFormData("filename",pat.name,requestbody)
        val filename=RequestBody.create(MediaType.parse("text/plain"), "$pdfname.${path.lastPathSegment}")

        val jobid=RequestBody.create(MediaType.parse("text/plain"),"${data.id}")

       val mobile = RequestBody.create(MediaType.parse("text/plain"),shared.getString("mobile","")!!)
        val date = RequestBody.create(MediaType.parse("text/plain"),data1.format(Date()))
        val id=RequestBody.create(MediaType.parse("text/plain"),data.companyid)
        val   userid =RequestBody.create(MediaType.parse("text/palin"),shared.getString("id","")!!)

        CoroutineScope(IO).launch {
            ReTrofit.instance.uploadImage(multipart,filename,mobile=mobile,
                    date=date,
                    userid=userid,
                    companyid =id
            , workid = jobid).enqueue(object :retrofit2.Callback<CommonResponse>{
                override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                    p.dismiss()
                    val ff=response.body()!!.message

                    if(ff=="Success") {
                    finish()
                        Toast.makeText(this@ViewApplcaition, "SuccessFully Applied", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@ViewApplcaition, ff, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                p.dismiss()
              Log.i("texttt","${t.message}")   }
            })
        }
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    @Throws(IOException::class)
    private fun     getRealFilePath(context: Context, uri: Uri): String? {
        var realPath: String? = null
        if (uri.scheme == "content") {
            val contentResolver: ContentResolver = context.contentResolver
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            if (inputStream != null) {
                val file = File(context.getCacheDir(), uri.lastPathSegment!!)
                FileUtils.copy(inputStream,file.outputStream())
                realPath = file.absolutePath
            }
        } else if (uri.scheme == "file") {
            realPath = uri.path
        }
        return realPath
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    
        super.onActivityResult(requestCode, resultCode, data)
        if(data!=null){
            if(requestCode!=1001) {
                findtext(data.extras!!.get("data") as Bitmap)
            }else{
                filepath = data.data!!
                bind.appCompatButton3.text="Upload"
            }
    }else{

            Toast.makeText(this, "please Snap a Pick", Toast.LENGTH_SHORT).show()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun findtext(bitmap: Bitmap) {
        val p=ProgressDialog(this)
        p.setTitle("Recognizing.....")
        p.setCancelable(false)
        p.show()










        val instance=TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            instance.process(InputImage.fromBitmap(bitmap,0))
                .addOnSuccessListener {
                    Toast.makeText(this, it.text, Toast.LENGTH_SHORT).show()
                    it.text.split(",").forEach {
                        if(!textrecog.contains(it)) {
                            textrecog.add(it)
                        }
                    }
                    var num=0
                    val k= data.skills.split(",")
                     val size=k.size
                    val score=100/size
                    k.forEach {
                            if(textrecog.contains(it)){
                                num += score
                            }
                    }

                    bind.score.isVisible=true
                    bind.score.text="$num% of Chances\n You Can get the Job!!"
                    Toast.makeText(this, "$textrecog", Toast.LENGTH_SHORT).show()
                    textrecog.clear()

                    p.dismiss()
                }.addOnFailureListener {
                p.dismiss()
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                }

    }
}