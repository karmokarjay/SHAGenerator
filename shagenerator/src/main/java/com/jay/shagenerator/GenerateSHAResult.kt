package com.jay.shagenerator

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import android.util.Log
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

object GenerateSHAResult {

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

            val md = MessageDigest.getInstance("SHA-256")
            signatures?.getOrNull(0)?.toByteArray()?.let { md.update(it) }
            val sha256 = md.digest()
            Log.d("Sign sha256 before ::", "$sha256")
            Log.d("Sign packageName ::", packageName)

            val applicationPackageByteArray = packageName.toByteArray(StandardCharsets.UTF_8)
            val shaMixedPackage = sha256 + applicationPackageByteArray
            Log.d("Sign mixedSha256 ::", "$shaMixedPackage")
//            Log.d("Sign hex array ::","${shaMixedPackage.toHexArray().contentToString}")

            Base64.encodeToString(shaMixedPackage, Base64.NO_WRAP)
        } catch (e: Exception) {
            Log.e("SHA256", "Error getting SHA-256", e)
            null
        }
    }
}