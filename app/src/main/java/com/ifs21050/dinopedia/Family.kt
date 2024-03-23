package com.ifs21050.dinopedia

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Family(
    var name: String,
    var icon: Int,
    var description: String,
    var characteristic: String,
    var habitat: String,
    var behavior: String,
    var classification: String,
) : Parcelable