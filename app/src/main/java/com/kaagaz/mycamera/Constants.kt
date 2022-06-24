package com.kaagaz.mycamera

import android.Manifest

object Constants {
    const val TAG = "CameraX"
    const val FILE_NAME_FORMAT = "yy-mm-dd-HH-mm-ss-SSS"
    const val DATE_FORMAT = "dd MMMM yyyy"
    const val REQUEST_CODE_PERMISSIONS = 1001
    val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
}