package com.example.assignment

import android.net.Uri

object ContentProviderHelper {
    const val AUTHORITY = "com.iav.contestdataprovider"
    const val MIME_TYPE = "vnd.android.cursor.dir/text"
    val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/text")
    const val COLUMN_NAME = "data" // The key where JSON is stored


}
