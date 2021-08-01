package com.zk.base_project.utils

import android.view.View
import android.view.animation.RotateAnimation

object AnimationUtils {

    fun rotate(view: View,fromDegree: Int ,toDegree: Int,duration:Int = 400) {

        var rotationAnimation = RotateAnimation(fromDegree.toFloat(),
                toDegree.toFloat(),
                view.width/2.toFloat(),
                view.height/2.toFloat())
        rotationAnimation.duration = duration.toLong()
        rotationAnimation.fillAfter = true

        view.startAnimation(rotationAnimation)
    }
}