package ru.netology.nmedia.format

import java.math.RoundingMode
import java.text.DecimalFormat

object Format : FormatCount {
    override fun format(num: Long): String {
        return when {
            num < 1000 -> num.toString()
            num < 1_000_000 -> {
                val formatted = num / 1000.0
                val df = DecimalFormat("#.#")
                val df2 = DecimalFormat("#")
                df2.roundingMode = RoundingMode.DOWN
                df.roundingMode = RoundingMode.DOWN
                if (num > 10000) {
                    df2.format(formatted) + "K"
                } else df.format(formatted) + "K"
            }
            else -> {
                val formatted = num / 1_000_000.0
                DecimalFormat("#.#").format(formatted) + "M"
            }
        }

    }
}