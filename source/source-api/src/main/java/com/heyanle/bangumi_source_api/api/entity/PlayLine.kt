package com.heyanle.bangumi_source_api.api.entity

import androidx.annotation.Keep

/**
 * Created by HeYanLe on 2023/2/18 21:54.
 * https://github.com/heyanLE
 */
@Keep
open class PlayLine(
    val id: String, // 源自己维护和判断
    val label: String,
    val episode: ArrayList<String>,
)


