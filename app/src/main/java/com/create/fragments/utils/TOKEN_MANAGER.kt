package com.create.fragments.utils

import android.content.Context
import com.create.fragments.utils.Const.SAVED_EMAIL
import com.create.fragments.utils.Const.USER_EMAIL
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TOKEN_MANAGER @Inject constructor(@ApplicationContext context: Context) {
    private var saved_pref = context.getSharedPreferences(SAVED_EMAIL , Context.MODE_PRIVATE)

    fun saveEmail(email:String){
        val editor = saved_pref.edit()
        editor.putString(USER_EMAIL , email)
        editor.apply()
    }

    fun getEmail():String?{

        return saved_pref.getString(USER_EMAIL , null)

    }


}