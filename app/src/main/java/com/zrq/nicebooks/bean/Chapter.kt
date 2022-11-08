package com.zrq.nicebooks.bean

import androidx.annotation.Keep

@Keep
data class Chapter(val msg: String = "",
                   val code: Int = 0,
                   val data: Data,
                   val count: Int = 0)

@Keep
data class ChapterListItem(val chapterId: String = "",
                           val title: String = "")
@Keep
data class Data(
    var cover: String = "",
    val descs: String = "",
    val fictionType: String = "",
    val chapterList: ArrayList<ChapterListItem>?,
    val author: String = "",
    val fictionId: String = "",
    val updateTime: String = "",
    var title: String = "")

