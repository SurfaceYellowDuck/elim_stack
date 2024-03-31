package org.example

import kotlinx.atomicfu.*
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


class EliminationArray<T>(capacity: Int, timeout: Long, unit: TimeUnit){
    private val timeout_ = timeout
    private val unit_ = unit
//    val exchanger_ = LockFreeExchanger<T>()
//    private val exchanger = atomicArrayOfNulls<LockFreeExchanger<T>>(capacity);
    private val exchanger = Array(capacity){LockFreeExchanger<T>()}
    private val random: Random = Random()

//    private val duration: Long = 10
//    init {
//        for (i in 0 ..< capacity) {
//             exchanger[i] = LockFreeExchanger()
//             }
//    }


    @Throws(TimeoutException::class)
    fun visit(value_: T?, range_: Int): T{
        val slot: Int = this.random.nextInt(range_)
        return (exchanger[slot].exchange(value_, timeout_, unit_ ))
    }
}
