import java.util.*
import kotlin.NoSuchElementException

class SequentialIntStack {
    private val stack = mutableListOf<Int>()
     fun push(elt: Int) {
        stack.add(elt)
    }
     fun pop(): Int {
         try {
             return stack.removeLast()
         }catch (e: NoSuchElementException){
             throw EmptyStackException()
         }
    }
     fun peek(): Int? {
        return stack.lastOrNull()
    }
}
