package com.example.aadpractice.utils

import android.content.Context
import android.net.ConnectivityManager

object ProjectUtils {
    @JvmStatic
    fun isNetworkConnected(mContext: Context): Boolean {
        val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni = cm.activeNetworkInfo
        return ni != null
    }
}