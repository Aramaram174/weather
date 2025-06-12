package com.karapetyan.weather.utils

import kotlin.math.roundToInt

class MathFunctions {

    fun roundNumber(number: Double): Byte {
        return number.roundToInt().toByte()
    }
}