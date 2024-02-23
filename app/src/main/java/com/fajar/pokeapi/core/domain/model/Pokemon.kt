package com.fajar.pokeapi.core.domain.model

import android.os.Parcelable
import com.fajar.pokeapi.core.data.remote.response.StatsItem
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
class Pokemon(

    val id: Int,
    val name: String,
    val types: String,
    val height: Int,
    val weight: Int,
    val abilities: String,
    val stats: String,
    val sprites: String,
    var isFavorite: Boolean = false,

    ):Parcelable