package org.example

import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import java.util.concurrent.atomic.AtomicReference



class EliminationBackoffStack<T> : TreiberStack<T>(){
    private val CAPACITY: Int = 100
    private val TIMEOUT: Long = 3
    private val UNIT: TimeUnit = TimeUnit.NANOSECONDS
    private val eliminationArray: EliminationArray<T> = EliminationArray(CAPACITY, TIMEOUT, UNIT)

    private val policy = object : ThreadLocal<RangePolicy>() {
        @Synchronized
        override fun initialValue(): RangePolicy {
            return RangePolicy(CAPACITY)
        }
    }

    init {
         top = AtomicReference(null)
    }
    fun push(el: T) {
        val rangePolicy: RangePolicy = policy.get()
        val node: TreiberNode<T> = TreiberNode(el)
        while (true) {
            if (!tryPush(node)) {
                try {
                    val otherValue: T = eliminationArray.visit(el, rangePolicy.getRange())

                    if (otherValue == null){
                        rangePolicy.recordEliminationSuccess()
                        return
                    }
                } catch (e: TimeoutException) {
                    rangePolicy.recordEliminationTimeout()
                }
            }
            else {
                return
            }
        }
    }

    @Throws(EmptyStackException::class)
    fun pop(): T{
        val rangePolicy: RangePolicy = policy.get()
        while (true){
            val node: TreiberNode<T>? = tryPop()
            if (node != null) return (node.value)
            else try {
                val exchangedItem = eliminationArray.visit(null, rangePolicy.getRange())

                if (exchangedItem != null){
                    rangePolicy.recordEliminationSuccess()
                return exchangedItem
                }
            } catch (e: TimeoutException){
                rangePolicy.recordEliminationTimeout()
            }
        }

    }

    override fun peek(): T?{
        val head: TreiberNode<T> = top.get() ?: return null
        return head.value
    }

}