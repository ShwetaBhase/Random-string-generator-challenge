package com.example.assignment

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import org.json.JSONObject



class RandomStringFetcher(private val context: Context) {

 /*   @RequiresApi(Build.VERSION_CODES.O)
    fun fetchRandomString(maxLength: Int): RandomString? {
        try {
            val uri = Uri.parse("content://com.iav.contestdataprovider/text")

            val cursor = context.contentResolver.query(uri, arrayOf("data"), null, null, null)

            cursor?.use {
                if (it.moveToFirst()) {
                    val jsonData = it.getString(it.getColumnIndexOrThrow("data"))

                    // Debug: Check if the fetched data is correct
                    Log.d("ContentProvider", "Fetched JSON: $jsonData")

                    val parsedData = parseJsonToModel(jsonData)

                    // Debug: Check parsed output
                    Log.d("ContentProvider", "Parsed Value: ${parsedData.value}, Length: ${parsedData.length}")

                    return if (parsedData.length <= maxLength) parsedData else null
                }
            }

        } catch (e: Exception) {
            Log.e("ContentProvider", "Failed to fetch data: ${e.message}")
        }
        return null
    }*/

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchRandomString(maxLength: Int): RandomString? {
        try {
            val uri = Uri.parse("content://com.iav.contestdataprovider/text")

            val cursor = context.contentResolver.query(uri, arrayOf("data"), null, null, null)

            cursor?.use {
                if (it.moveToFirst()) {
                    val jsonData = it.getString(it.getColumnIndexOrThrow("data"))

                    Log.e("ContentProvider", "Fetched JSON: $jsonData") // Debug log

                    val parsedData = parseJsonToModel(jsonData)

                    // Ensure the fetched string is within maxLength
                    val trimmedValue = parsedData.value.take(maxLength)

                    val finalString = RandomString(
                        value = trimmedValue,
                        length = trimmedValue.length, // Actual length after trim
                        created = parsedData.created
                    )

                    Log.e("ContentProvider", "Parsed String: ${finalString.value}, Length: ${finalString.length}")

                    return finalString
                }
            }

        } catch (e: Exception) {
            Log.e("ContentProvider", "Failed to fetch data: ${e.message}")
        }
        return null
    }



    private fun parseJsonToModel(jsonString: String): RandomString {
        val jsonObject = JSONObject(jsonString)
        val randomText = jsonObject.getJSONObject("randomText")

        return RandomString(
            value = randomText.getString("value"),
            length = randomText.getInt("length"),
            created = randomText.getString("created")
        )
    }
}
