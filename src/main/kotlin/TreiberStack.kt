package org.example

import java.util.EmptyStackException
//import kotlinx.atomicfu.*
import java.util.concurrent.atomic.AtomicReference

open class TreiberStack<E> {
    var top: AtomicReference<TreiberNode<E>> = AtomicReference(null)
//    var top = atomic<TreiberNode<E>?>(null);

    protected fun tryPush(node: TreiberNode<E>): Boolean{
        val oldTop: TreiberNode<E>? = top.get()
        node.next = oldTop
        return (top.compareAndSet(oldTop, node))
    }

    @Throws(EmptyStackException::class)
    protected fun tryPop(): TreiberNode<E>? {
        val oldTop = top.get() ?: throw EmptyStackException()
        val newTop = oldTop.next
        return if (top.compareAndSet(oldTop, newTop)) {
            oldTop
        } else {
            null
        }
    }

    fun peek(): E?{
        val head: TreiberNode<E> = top.get() ?: return null
        return head.value
    }
}
