package org.example

import kotlinx.atomicfu.*
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


class EliminationArray<T>(capacity: Int, timeout: Long, unit: TimeUnit){
    private val timeout_ = timeout
    private val unit_ = unit

    private val exchanger = Array(capacity){LockFreeExchanger<T>()}
    private val random: Random = Random()


    @Throws(TimeoutException::class)
    fun visit(value_: T?, range_: Int): T{
        val slot: Int = this.random.nextInt(range_)
        return (exchanger[slot].exchange(value_, timeout_, unit_ ))
    }
}
