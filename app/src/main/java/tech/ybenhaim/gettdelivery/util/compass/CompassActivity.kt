package tech.ybenhaim.gettdelivery.util.compass

import android.content.Context
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompassActivity {

    private var compass: Compass? = null
    private var currentAzimuth = 0f
    private val sotwFormatter: SOTWFormatter? = null

    private fun adjustArrow(azimuth: Float) {
        val an: Animation = RotateAnimation(
            -currentAzimuth, -azimuth,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
            0.5f
        )
        currentAzimuth = azimuth
        an.duration = 500
        an.repeatCount = 0
        an.fillAfter = true
        //arrowView?.startAnimation(an)
    }

    private fun setupCompass(context: Context) {
        compass = Compass(context)
        val cl: Compass.CompassListener = getCompassListener()
        compass!!.setListener(cl)
    }



    private fun getCompassListener(): Compass.CompassListener {


        return object : Compass.CompassListener {

            override fun onNewAzimuth(azimuth: Float) {

                CoroutineScope(Dispatchers.Main).launch {
                    adjustArrow(azimuth)
                   // adjustSotwLabel(azimuth)
                }
            }
        }
    }
}
