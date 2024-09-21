package com.pluu.coil.sample.coil

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import coil.ImageLoader
import coil.decode.DataSource
import coil.fetch.DrawableResult
import coil.fetch.FetchResult
import coil.fetch.Fetcher
import coil.request.Options
import java.util.Collections

class CustomFetcher(
    private val data: Uri,
    private val options: Options
) : Fetcher {

    private val httpsSchemes = Collections.unmodifiableSet(
        setOf("http", "https")
    )

    private val imageFile = "pluu.jpeg"

    override suspend fun fetch(): FetchResult? {
        if (!isApplicable(data)) return null

        // Case 1. DrawableResult
        return DrawableResult(
            drawable = getInterceptDrawable(options.context),
            isSampled = false,
            dataSource = DataSource.DISK
        )

//        // Case 2. SourceResult
//        return SourceResult(
//            source = ImageSource(
//                source = options.context.assets.open(imageFile).source().buffer(),
//                context = options.context,
//                metadata = AssetMetadata(imageFile)
//            ),
//            mimeType = MimeTypeMap.getFileExtensionFromUrl(imageFile),
//            dataSource = DataSource.DISK
//        )
    }

    private fun isApplicable(data: Uri): Boolean {
        return httpsSchemes.contains(data.scheme)
    }

    private fun getInterceptDrawable(context: Context): Drawable {
        return requireNotNull(
            Drawable.createFromStream(
                context.assets.open(imageFile),
                null
            )
        )
    }

    class Factory : Fetcher.Factory<Uri> {
        override fun create(
            data: Uri,
            options: Options,
            imageLoader: ImageLoader
        ): Fetcher = CustomFetcher(data, options)
    }
}
