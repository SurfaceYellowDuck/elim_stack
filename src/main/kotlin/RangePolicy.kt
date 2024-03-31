package org.example

class RangePolicy(private var maxRange: Int) {
    private var currentRange: Int = 1
    fun recordEliminationSuccess() {
        if (currentRange < maxRange) currentRange++
    }

    fun recordEliminationTimeout() {
        if (currentRange > 1) currentRange--
    }
    fun getRange(): Int {
        return currentRange
    }
}