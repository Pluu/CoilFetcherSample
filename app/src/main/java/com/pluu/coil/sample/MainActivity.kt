@file:OptIn(ExperimentalLayoutApi::class)

package com.pluu.coil.sample

import android.content.ContentResolver
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pluu.coil.sample.ui.theme.CoilFetcherSampleTheme
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoilFetcherSampleTheme {
                SampleScreen()
            }
        }
    }
}

@Composable
fun SampleScreen(modifier: Modifier = Modifier) {
    var imageUrl by remember { mutableStateOf<Any?>(null) }

    Scaffold(modifier) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = {
                    imageUrl = null
                }) {
                    Text("Clear", fontSize = 20.sp)
                }
                Button(onClick = {
                    imageUrl = R.drawable.chrome_dino
                }) {
                    Text("Drawable", fontSize = 20.sp)
                }
                Button(onClick = {
                    imageUrl =
                        ContentResolver.SCHEME_FILE + ":///android_asset/" + "android_studio.png"
                }) {
                    Text("Aesset", fontSize = 20.sp)
                }
                Button(
                    onClick = {
                        imageUrl =
                            "https://source.android.com/setup/images/Android_symbol_green_RGB.png"
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63))
                ) {
                    Text("Load", fontSize = 20.sp)
                }
            }

            Box(Modifier.size(200.dp)) {
                if (LocalInspectionMode.current) {
                    val context = LocalContext.current
                    val bitmap = loadBitmapFromAssets(context.assets, "pluu.jpeg")
                    if (bitmap != null) {
                        Image(
                            painter = BitmapPainter(bitmap.asImageBitmap()),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                } else if (imageUrl != null) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}

private fun loadBitmapFromAssets(assetManager: AssetManager, fileName: String): Bitmap? {
    return try {
        assetManager.open(fileName).use { inputStream ->
            BitmapFactory.decodeStream(inputStream)
        }
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

@Preview(showBackground = true, heightDp = 350)
@Composable
private fun SampleScreenPreview() {
    CoilFetcherSampleTheme {
        SampleScreen()
    }
}