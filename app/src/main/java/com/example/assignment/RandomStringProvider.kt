package com.example.assignment

import android.content.*
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import androidx.annotation.Nullable
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class RandomStringProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.iav.contestdataprovider"
        const val TABLE_NAME = "text"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$TABLE_NAME")
        const val COLUMN_NAME = "data"

        val PROJECTION = arrayOf(COLUMN_NAME)
    }

    override fun onCreate(): Boolean {
        return true // Provider initialized successfully
    }

    @Nullable
    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {

        // Generate a random string
        val randomText = generateRandomString()

        // Create a MatrixCursor to return the result
        val cursor = MatrixCursor(arrayOf(COLUMN_NAME))
        cursor.addRow(arrayOf(randomText))

        return cursor
    }

    override fun getType(uri: Uri): String {
        return "vnd.android.cursor.dir/text"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("Insert not supported")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("Delete not supported")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?
    ): Int {
        throw UnsupportedOperationException("Update not supported")
    }

    private fun generateRandomString(): String {
        val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random()
        val length = 10 // Default length
        val value = (1..length).map { chars[random.nextInt(chars.length)] }.joinToString("")

        // Generate JSON response
        val jsonObject = JSONObject().apply {
            put("randomText", JSONObject().apply {
                put("value", value)
                put("length", length)
                put("created", getCurrentTimeISO8601())
            })
        }
        return jsonObject.toString()
    }

    private fun getCurrentTimeISO8601(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(Date())
    }
}
