package org.example

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import kotlin.system.measureNanoTime

class ElimBanch {

    fun test_1(): String {
        val stack: EliminationBackoffStack<Int> = EliminationBackoffStack();
        for (i: Int in 1..1000) {
            stack.push(Random.nextInt(1, 100))
        }

        val executionTime = measureNanoTime {

        val executor = Executors.newFixedThreadPool(8)
            executor.execute {
                repeat(1000) {
                    stack.push(Random.nextInt(1, 100))

                }

                repeat(1000) {
                    stack.pop()
                }
                executor.shutdown()
                executor.awaitTermination(1, TimeUnit.MINUTES)
            }
        }
        return "elim stack $executionTime"
    }


    fun test_2(): String {
        val stack: EliminationBackoffStack<Int> = EliminationBackoffStack();
        for (i: Int in 1..1000) {
            stack.push(Random.nextInt(1, 100))
        }

        val executionTime = measureNanoTime {

            val executor = Executors.newFixedThreadPool(8)
            executor.execute {
                repeat(1000) {
                    stack.push(Random.nextInt(1, 100))
                    stack.pop()
                }


            }
            executor.shutdown()
            executor.awaitTermination(1, TimeUnit.MINUTES)
        }
        return("elim stack $executionTime")
    }
}
