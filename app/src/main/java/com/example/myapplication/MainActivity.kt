package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.example.myapplication.ui.theme.MyApplicationTheme
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    //color = MaterialTheme.colors.background
                ) {
                    SimpleWidgetColumn()
                }

            }
        }
    }
}


@Composable
private fun TextClock(
    modifier: Modifier = Modifier,
    format12Hour: String? = null,
    format24Hour: String? = null,
    timeZone: String? = null,

) {
    AndroidView(
        factory = { context ->
            android.widget.TextClock(context).apply {
                format12Hour?.let { this.format12Hour = it }
                format24Hour?.let { this.format24Hour = it }
                timeZone?.let { this.timeZone = it }
                textSize.let { this.textSize = 66f }

                setTextColor(Color.Black .toArgb())


            }

        },
        modifier = modifier,
        //color = Color.Blue


    )
}




@Composable
fun SimpleWidgetColumn() {

    Box{
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = "https://api.bimg.cc/today?w=1920&h=1080&mkt=zh-CN",
            contentDescription = "First line of code",
            contentScale = ContentScale.Crop
        )

        SimpleRow(
            modifier = Modifier.align(Alignment.BottomStart),
        )
//        Text(
//            modifier = Modifier.align(Alignment.TopEnd),
//            text = "This is Text dsadsadsadsasdsa",
//            color = Color.Blue,
//            fontSize = 66.sp
//
//        )
//        TextClock(
//            modifier = Modifier.align(Alignment.TopStart),
//
//        )

    }

}


@Composable
fun SimpleRow( modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                //shape = RoundedCornerShape(28.dp)
            ),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,

    ) {

        TextClock(
            //modifier = Modifier.align(Alignment.TopStart),

            )

        Text(
            //modifier = Modifier.align(Alignment.TopEnd),
            text = "This is Text dsadsadsadsasdsa",
            color = Color.Black,
            fontSize = 66.sp,

//            modifier = Modifier
//            .background(
//
//                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
//                //shape = RoundedCornerShape(28.dp)
//            )
//            .padding(16.dp),

        )

        Button(onClick = {
            // 点击操作
            Log.e("getSync","diandian");
            getSync()
        }) {
            Text(text = "点我")
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        SimpleWidgetColumn()
    }
}



//get同步请求
fun getSync() {
    thread {
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
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}