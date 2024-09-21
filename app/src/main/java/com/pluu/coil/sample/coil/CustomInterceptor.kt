package com.pluu.coil.sample.coil

import android.content.Context
import android.graphics.drawable.Drawable
import coil.decode.DataSource
import coil.intercept.Interceptor
import coil.request.ImageResult
import coil.request.SuccessResult
import java.util.Collections

class CustomInterceptor(
    private val context: Context
) : Interceptor {

    private val httpsSchemes = Collections.unmodifiableSet(
        setOf("http", "https")
    )

    private val imageFile = "pluu.jpeg"

    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val value = chain.request.data.toString()
        if (isApplicable(value)) {
            return SuccessResult(
                drawable = getInterceptDrawable(),
                request = chain.request,
                dataSource = DataSource.DISK
            )
        }
        return chain.proceed(chain.request)
    }

    private fun getInterceptDrawable(): Drawable {
        return requireNotNull(
            Drawable.createFromStream(
                context.assets.open(imageFile),
                null
            )
        )
    }

    private fun isApplicable(data: String): Boolean {
        return httpsSchemes.any { data.startsWith(it) }
    }
}