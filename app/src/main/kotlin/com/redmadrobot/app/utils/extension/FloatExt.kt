package com.redmadrobot.app.utils.extension

import android.content.res.Resources

val Float.toDp: Float
    get() = this / Resources.getSystem().displayMetrics.density

val Float.toPx: Float
    get() = this * Resources.getSystem().displayMetrics.density
