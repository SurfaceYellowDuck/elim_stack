package org.example

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import kotlin.system.measureNanoTime

class TreibBanch {
    fun test_1(): String {
        val stack: TreiberStack<Int> = TreiberStack();
        for (i: Int in 1..1000) {
            val node: TreiberNode<Int> = TreiberNode(Random.nextInt(1, 100))
            stack.tryPush(node)
        }


        val executor = Executors.newFixedThreadPool(8)
        val executionTime = measureNanoTime {
                executor.execute {
                    repeat(1000) {
                        val node: TreiberNode<Int> = TreiberNode(Random.nextInt(1, 100))
                        stack.tryPush(node)
                    }

                    repeat(1000) {
                        stack.tryPop()
                    }
                }

            executor.shutdown()
            executor.awaitTermination(1, TimeUnit.MINUTES)
        }
        return("treiber stack $executionTime")
    }


    fun test_2(): String {
        val stack: TreiberStack<Int> = TreiberStack();
        for (i: Int in 1..1000) {
            val node: TreiberNode<Int> = TreiberNode(Random.nextInt(1, 100))
            stack.tryPush(node)
        }


        val executor = Executors.newFixedThreadPool(8)
        val executionTime = measureNanoTime {
            executor.execute {
                repeat(1000) {
                    val node: TreiberNode<Int> = TreiberNode(Random.nextInt(1, 100))
                    stack.tryPush(node)
                    stack.tryPop()
                }
            }

            executor.shutdown()
            executor.awaitTermination(1, TimeUnit.MINUTES)

        }
        return("treiber stack $executionTime")
    }
}