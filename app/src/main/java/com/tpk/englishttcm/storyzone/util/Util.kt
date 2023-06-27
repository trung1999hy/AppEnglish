package com.tpk.englishttcm.storyzone.util

import android.content.Context
import android.os.Build
import android.os.Environment
import java.io.File

class Util {
    companion object {
        fun getRoot(context: Context): File? {
            val root = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            } else {
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            }
            return root
        }
    }
}