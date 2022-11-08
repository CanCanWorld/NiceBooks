package com.zrq.nicebooks.bean

import androidx.annotation.Keep

@Keep
data class Content(val msg: String = "",
                   val code: Int = 0,
                   val data: List<String>?,
                   val count: Int = 0)