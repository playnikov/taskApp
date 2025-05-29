package ru.example.taskapp.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException

object NetworkUtils {
    suspend fun getAvailableIp(ip1: String, ip2: String): String {
        return withContext(Dispatchers.IO) {
            if (isIpReachable(ip1)) {
                ip1
            } else if (isIpReachable(ip2)) {
                ip2
            } else {
                ""
            }
        }
    }

    private fun isIpReachable(ip: String): Boolean {
        return try {
            val process = Runtime.getRuntime().exec("ping -c 1 $ip")
            process.waitFor()
            process.exitValue() == 0
        } catch (e: IOException) {
            false
        }
    }
}