package com.example.listoftraffictickets

import android.content.Context
import com.google.gson.Gson

object GsonHelper {
    private const val PREFS_NAME = "MyPrefsFile"
    private const val TICKETS_KEY = "Tickets"

    fun saveTickets(context: Context, tickets: List<Ticket>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(tickets)
        editor.putString(TICKETS_KEY, json)
        editor.apply()
    }

    fun loadTickets(context: Context): List<Ticket> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString(TICKETS_KEY, null)
        return if (json != null) {
            gson.fromJson(json, Array<Ticket>::class.java).toList()
        } else {
            emptyList()
        }
    }

    fun clearTickets(context: Context) {
        val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        preferences.edit().remove(TICKETS_KEY).apply()
    }
}