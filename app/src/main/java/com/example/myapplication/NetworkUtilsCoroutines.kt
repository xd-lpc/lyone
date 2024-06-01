package com.example.myapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class NetworkUtilsCoroutines {
    private var resultPicture: Bitmap? = null
    private var context: Context
    constructor(context: Context) {
        this.context = context
    }

    fun loadPicture (url :URL) : Bitmap? {
        var bitmapFromNetwork: Bitmap? = null
        try {
            var httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.readTimeout = 2000
            httpURLConnection.connectTimeout = 2000
            var inputStream = httpURLConnection.inputStream
            var responseCode = httpURLConnection.responseCode

            if( responseCode == HttpURLConnection.HTTP_OK) {
                bitmapFromNetwork = BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: IOException) {
            Log.d("get internet", "LoadPicture - error: ${e?.toString()}")
        }
        this.resultPicture = bitmapFromNetwork
        return resultPicture
    }


    object CommonConstants {
        const val Address1 =
            "https://ftp.bmp.ovh/imgs/2021/06/679d4f9dda8017a5.jpg"

    }

    fun getSync() :String{
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("http://10.0.2.2:5000/")//因为Android模拟器本身把自己当做了localhost或127.0.0.1，而此时我们又通过localhost或127.0.0.1访问本地服务器，所以会抛出异常了。在模拟器上可以用10.0.2.2代替127.0.0.1和localhost；
                    .build()
                val call=client.newCall(request)
                val response = call.execute()//execute方法会阻塞在这里，必须等到服务器响应，得到response才会执行下面的代码
                val requestData = response.body?.string()
                if (requestData != null) {
                    Log.e("getSync", requestData)
                    return requestData
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        return "internet error"

    }





}
