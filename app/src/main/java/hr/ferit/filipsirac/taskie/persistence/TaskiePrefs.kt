package hr.ferit.filipsirac.taskie.persistence

import android.preference.PreferenceManager
import hr.ferit.brunozoric.taskie.Taskie

object TaskiePrefs{

    const val KEY_PRIORITY_NAME = "PRIORITY"

    private fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(Taskie.getAppContext())

    fun store(key: String, value: Int){
        sharedPrefs().edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defaultValue:Int): Int{
        return sharedPrefs().getInt(key, defaultValue)
    }

}