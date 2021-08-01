package com.zk.base_project

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class Test {
    enum class Sport { HIKE, RUN, TOURING_BICYCLE, E_TOURING_BICYCLE }
    data class Summary(val sport: Sport, val distance: Int)

    @Test
    fun main() {
        val sportStats = listOf(Summary(Sport.HIKE, 92),
                Summary(Sport.RUN, 77), Summary(Sport.TOURING_BICYCLE, 322),
                Summary(Sport.E_TOURING_BICYCLE, 656))
        sportStats.filter { it.sport != Sport.E_TOURING_BICYCLE }
                .sortedByDescending { it.distance }
                .map {
                    print("type ${it.sport.toString()} distance ${it.distance} \n")
                }
    }
}