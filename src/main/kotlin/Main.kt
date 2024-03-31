package org.example

fun main() {
    val newStack: EliminationBackoffStack<Int> = EliminationBackoffStack();
    newStack.push(12)
    println(newStack.peek())
    newStack.push(13)
    println(newStack.peek())
//    newStack.iterator()
//    println("Hello World!")
}
