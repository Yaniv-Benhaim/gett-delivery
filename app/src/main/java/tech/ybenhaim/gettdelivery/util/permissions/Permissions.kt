package tech.ybenhaim.gettdelivery.util.permissions

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import pub.devrel.easypermissions.EasyPermissions
import tech.ybenhaim.gettdelivery.util.Constants
import tech.ybenhaim.gettdelivery.util.TrackingUtility

fun Context.getActivity(): AppCompatActivity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is AppCompatActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}


