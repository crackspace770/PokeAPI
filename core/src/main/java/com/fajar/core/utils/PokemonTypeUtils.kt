package com.fajar.core.utils

import com.fajar.core.R

object PokemonTypeUtils {

    fun getTypeColor(type: String): Int {
        return when (type) {
            "normal" -> R.color.normal_color
            "fire" -> R.color.fire_color
            "water" -> R.color.water_color
            "electric" -> R.color.electric_color
            "grass" -> R.color.grass_color
            "ice" -> R.color.ice_color
            "fighting" -> R.color.fighting_color
            "poison" -> R.color.poison_color
            "ground" -> R.color.ground_color
            "flying" -> R.color.flying_color
            "psychic" -> R.color.psychic_color
            "bug" -> R.color.bug_color
            "rock" -> R.color.rock_color
            "ghost" -> R.color.ghost_color
            "dragon" -> R.color.dragon_color
            "dark" -> R.color.dark_color
            "steel" -> R.color.steel_color
            "fairy" -> R.color.fairy_color
            "stellar" -> R.color.stellar_color
            else -> R.color.default_color
        }
    }

    fun getStateColor(type:String):Int{
        return when (type) {
            "hp" -> R.color.hp_color
            "attack" -> R.color.attack_color
            "defense" -> R.color.defense_color
            "special-attack" -> R.color.special_attack_color
            "special-defense" -> R.color.special_defense_color
            "speed" -> R.color.speed_color
            else -> R.color.default_color
        }
    }
}