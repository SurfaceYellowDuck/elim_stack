package org.example

import java.util.EmptyStackException
//import kotlinx.atomicfu.*
import java.util.concurrent.atomic.AtomicReference

open class TreiberStack<E> {
    var top: AtomicReference<TreiberNode<E>> = AtomicReference(null)
//    var top = atomic<TreiberNode<E>?>(null);

    fun createNode(el : E): TreiberNode<E>{
        return TreiberNode(el);
    }

     fun tryPush(node: TreiberNode<E>): Boolean{
        val oldTop: TreiberNode<E>? = top.get()
        node.next = oldTop
        return (top.compareAndSet(oldTop, node))
    }

    @Throws(EmptyStackException::class)
     fun tryPop(): TreiberNode<E>? {
        val oldTop = top.get() ?: throw EmptyStackException()
        val newTop = oldTop.next
        return if (top.compareAndSet(oldTop, newTop)) {
            oldTop
        } else {
            null
        }
    }

    open fun peek(): E?{
        val head: TreiberNode<E> = top.get() ?: return null
        return head.value
    }
}
