package com.example.ngumeniuk.curogram.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


class DateUtil {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun formatedDate(time: Long): String =
                SimpleDateFormat("yyyy/MM/dd kk:mm:ss")
                        .format(Date(time))

    }
}
