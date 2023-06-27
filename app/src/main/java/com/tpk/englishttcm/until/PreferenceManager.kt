package com.tpk.englishttcm.until

import android.content.Context
import android.content.SharedPreferences
import com.tpk.englishttcm.common.Constant.KEY_TOTAL_COIN

class PreferenceManager(context: Context) {
    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor?

    companion object {
        private var instance: PreferenceManager?= null
        fun getInstance(context: Context): PreferenceManager {
            if (instance == null) {
                instance = PreferenceManager(context)
            }
            return instance!!
        }
    }

    init {
        sharedPreferences = context.getSharedPreferences("EnglishPreferences", Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()
    }


    fun putBoolean(key: String?, value: Boolean?) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value!!)
        editor.apply()
    }

    fun getBoolean(key: String?): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun putString(key: String?, value: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String?): String? {
        return sharedPreferences.getString(key, null)
    }

    fun setValueCoin(value: Int) {
        editor?.putInt(KEY_TOTAL_COIN, value)?.apply()
    }

    fun getValueCoin(): Int {
        return sharedPreferences.getInt(KEY_TOTAL_COIN, 0) ?: 0
    }

    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}