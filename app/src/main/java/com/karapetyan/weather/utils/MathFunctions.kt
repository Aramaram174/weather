package com.karapetyan.weather.utils

import kotlin.math.roundToInt

class MathFunctions {

    companion object {
        fun roundNumber(number: Double): Byte {
            return number.roundToInt().toByte()
        }
    }
}