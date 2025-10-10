package live.anonymespy.madassistant.constant

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresPermission

object EmergencyCall {
    private const val SOS_NUMBER = "117"

    @RequiresPermission(Manifest.permission.VIBRATE)
    fun dialSOS(context: Context) {
        performHapticFeedback(context)
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$SOS_NUMBER")
        }
        context.startActivity(intent)
    }

    fun dialNumber(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        context.startActivity(intent)
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    private fun performHapticFeedback(context: Context) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(200)
        }
    }
}
