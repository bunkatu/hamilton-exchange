package com.hamiltonch.hamiltonexchange.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager


class CustomSharedPrefs {

    companion object{

        private val REFRESH_TIME = "refresh_time"
        private var sharedPrefs: SharedPreferences? = null


        @Volatile private var instance: CustomSharedPrefs? = null

        private val lock = Any()
        operator fun invoke(context: Context) : CustomSharedPrefs = instance ?: synchronized(lock) {
            instance ?: makeCustomSharedPrefs(context).also {
                instance = it
            }
        }

        private fun makeCustomSharedPrefs(context: Context): CustomSharedPrefs {
            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPrefs()
        }
    }

    fun saveTime(time: Long) {
        sharedPrefs?.edit(commit = true){
            putLong(REFRESH_TIME, time)
        }
    }

    fun getTime() = sharedPrefs?.getLong(REFRESH_TIME, 0)
}