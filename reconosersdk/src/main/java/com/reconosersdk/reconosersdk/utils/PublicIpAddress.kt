package com.reconosersdk.reconosersdk.utils

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL


object PublicIpAddress  {

    fun getPublicIpAddress() : String?{
        return try {
            //permission
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val myIp = URL("https://checkip.amazonaws.com/")
            val c = myIp.openConnection()
            c.connectTimeout = 1000
            c.readTimeout = 1000
            val inIp = BufferedReader(InputStreamReader(c.getInputStream()))
            inIp.readLine()
        } catch (e: java.lang.Exception) {
            e.message
        }
    }

}

