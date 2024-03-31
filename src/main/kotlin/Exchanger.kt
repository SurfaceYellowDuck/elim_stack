package org.example
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import java.util.concurrent.atomic.AtomicStampedReference
//import java.util.concurrent.atomic.*
import kotlinx.atomicfu.*

class LockFreeExchanger<T>{
    private val EMPTY: Int = 0
    private val WAITING: Int = 1
    private val BUSY: Int = 2
    private val slot: AtomicStampedReference<T> = AtomicStampedReference<T>(null, EMPTY)

    @Throws(TimeoutException::class)
    fun exchange(myItem: T?, timeout: Long, unit:TimeUnit): T{
        val nanos: Long = unit.toNanos(timeout)
        val timeBound: Long = System.nanoTime() + nanos
        val stampHolder = intArrayOf(EMPTY)
        while (true){
            if (System.nanoTime() > timeBound) throw TimeoutException()
            var yrlItem = slot.get(stampHolder)
            val stamp: Int = stampHolder[0]
            when(stamp){
                EMPTY -> {
                    if(slot.compareAndSet(yrlItem, myItem, EMPTY, WAITING)){
                        while (System.nanoTime() < timeBound){
                            yrlItem = slot.get(stampHolder)
                            if (stampHolder[0]==BUSY){
                                slot.set(null, EMPTY)
                                return yrlItem
                            }
                        }
                        if (slot.compareAndSet(myItem, null, WAITING, EMPTY)) throw TimeoutException();
                        else{
                            yrlItem = slot.get(stampHolder)
                            slot.set(null, EMPTY)
                            return yrlItem;
                        }
                    }
                    break
                }
                WAITING -> {
                    if (slot.compareAndSet(yrlItem, myItem, WAITING, BUSY)) return yrlItem
                break
                }
                BUSY -> break
            }
        }
        throw TimeoutException()
    }
}