package org.example

import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import java.util.concurrent.atomic.AtomicReference


class EliminationBackoffStack<T> : TreiberStack<T>(){
//    val capacity: Int = 8
private val CAPACITY: Int = 8
    private val TIMEOUT: Long = 1
    private val UNIT: TimeUnit = TimeUnit.MILLISECONDS
//    top = AtomicReference(null)
    private val eliminationArray: EliminationArray<T> = EliminationArray(CAPACITY, TIMEOUT, UNIT)
    private val policy: ThreadLocal<RangePolicy> = object : ThreadLocal<RangePolicy>() {
        override fun initialValue(): RangePolicy {
            return RangePolicy(CAPACITY)
        }
    }


    init {
         top = AtomicReference(null)
//        eliminationArray = EliminationArray(CAPACITY, TIMEOUT, UNIT)
    }
    //    val RangePolicy: ThreadLocal = ThreadLocal<RangePolicy>()
    fun push(el: T) {
        val rangePolicy: RangePolicy = policy.get()
        val node: TreiberNode<T> = TreiberNode(el)
        while (true) {
            if (!tryPush(node)) {
                try {
                    val otherValue: T = eliminationArray.visit(el, rangePolicy.getRange())
//                val otherValue: T = eliminationArray.visit(el) ?: return
//                                    rangePolicy.recordEliminationSuccess()
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
//                val exchangedItem = eliminationArray.visit(null)

                if (exchangedItem != null){
                    rangePolicy.recordEliminationSuccess()
                return exchangedItem
                }
            } catch (e: TimeoutException){
                rangePolicy.recordEliminationTimeout()
            }
        }

    }

//    fun iterator(){
//        var currentEl = this.top.value
//        if (currentEl != null) {
//            while (currentEl != null){
//                println(currentEl.value)
//                currentEl = currentEl.next
//            }
//        }
//    }
}