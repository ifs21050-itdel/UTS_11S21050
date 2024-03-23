package com.ifs21050.dinopedia

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Dinosaurus(
    var name: String,
    var gambar: Int,
    var desc: String,
    var group: String,
    var habitat: String,
    var food: String,
    var tinggi: String,
    var bobot: String,
    var kelemahan: String,
) : Parcelable