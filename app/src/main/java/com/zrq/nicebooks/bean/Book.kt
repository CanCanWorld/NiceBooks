package com.zrq.nicebooks.bean

import androidx.annotation.Keep

@Keep
data class Book(val msg: String = "",
                val code: Int = 0,
                val data: List<DataItem>?,
                val count: Int = 0)

@Keep
data class DataItem(val cover: String = "",
                    val fictionType: String = "",
                    val descs: String = "",
                    val author: String = "",
                    val fictionId: String = "",
                    val updateTime: String = "",
                    val title: String = "")