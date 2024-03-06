package com.fajar.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Pokemon(

    val id: Int?,
    val name: String?,
    val types: String?,
    val height: Int?,
    val weight: Int?,
    val abilities: String?,
    val stats: String?,
    val sprites: String?,
    var isFavorite: Boolean = false,

    ):Parcelable