package com.fajar.pokeapi.core.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "pokemon")
data class PokemonEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "types")
    val types: String?,

    @ColumnInfo(name = "height")
    val height: Int?,

    @ColumnInfo(name = "weight")
    val weight: Int?,

    @ColumnInfo(name = "abilities")
    val abilities: String?,

    @ColumnInfo(name = "stats")
    val stats: String?,

    @ColumnInfo(name = "sprites")
    val sprites: String?,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,

    ):Parcelable