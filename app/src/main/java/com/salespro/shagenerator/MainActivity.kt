package com.salespro.shagenerator

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jay.shagenerator.GenerateSHAResult

import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val getSignature=  GenerateSHAResult.getAppSignatureSHA256(this)
//        val getSignature=getAppSignatureSHA256(this)
        Log.d("Signature:: ","$getSignature")
    }

   /* fun getAppSignatureSHA256(context: Context): String? {
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
    }*/
}