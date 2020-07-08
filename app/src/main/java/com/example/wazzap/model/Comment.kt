package com.example.wazzap.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comment(
    val postId: String = "",
    val author: String = "",
    val time: Long = 0L,
    val content: String = ""
) : Parcelable