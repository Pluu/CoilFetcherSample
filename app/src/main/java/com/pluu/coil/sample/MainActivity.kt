package com.pluu.coil.sample

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    Box(
        modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        if (!LocalInspectionMode.current) {
            AsyncImage(
                model = "https://source.android.com/setup/images/Android_symbol_green_RGB.png",
                contentDescription = null,
                modifier = Modifier.size(200.dp),
            )
        } else {
            val context = LocalContext.current
            val bitmap = loadBitmapFromAssets(context.assets, "pluu.jpeg")
            if (bitmap != null) {
                Image(
                    painter = BitmapPainter(bitmap.asImageBitmap()),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp),
                )
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

@Preview(showBackground = true, widthDp = 300, heightDp = 300)
@Composable
private fun SampleScreenPreview() {
    CoilFetcherSampleTheme {
        SampleScreen()
    }
}