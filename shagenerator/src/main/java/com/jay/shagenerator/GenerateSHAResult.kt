package com.jay.shagenerator

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import android.util.Log
import java.security.MessageDigest

class GenerateSHAResult {

    fun getAppSignatureSHA256(context: Context): String? {
        return try {
            val packageName = context.packageName
            val packageManager = context.packageManager

            val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
            } else {
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            }

            val signatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.signingInfo?.apkContentsSigners
            } else {
                @Suppress("DEPRECATION")
                packageInfo.signatures
            }

            val signature = signatures?.getOrNull(0)
            val md = MessageDigest.getInstance("SHA-256")
            val sha = signature?.toByteArray()?.let { md.digest(it) }
            Base64.encodeToString(sha, Base64.NO_WRAP)
        } catch (e: Exception) {
            Log.e("SHA256", "Error getting SHA-256", e)
            null
        }
    }
}