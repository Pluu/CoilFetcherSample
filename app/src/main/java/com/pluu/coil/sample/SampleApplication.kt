package com.pluu.coil.sample

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.DebugLogger
import com.pluu.coil.sample.coil.CustomFetcher
import com.pluu.coil.sample.coil.CustomInterceptor

class SampleApplication : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components {
                // Use Custom Interceptor
                add(CustomInterceptor(this@SampleApplication))
                // or Custom Fetcher
                add(CustomFetcher.Factory())
            }
            .logger(DebugLogger())
            .build()
    }
}
