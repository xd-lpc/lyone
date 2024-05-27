package com.example.myapplication

import android.os.Bundle
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
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        SimpleWidgetColumn()
    }
}