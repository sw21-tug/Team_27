package com.swtug.anticovid

import android.content.Context

object TestUtils {

    fun clearSharedPreferences(context: Context) {
        val preferences = context.getSharedPreferences("ANTI_COVID_APP", Context.MODE_PRIVATE)
        preferences.edit()
            .clear()
            .commit()
    }

    fun getResoureceString(context: Context, id: Int): String {
        return context.getString(id)
    }
}