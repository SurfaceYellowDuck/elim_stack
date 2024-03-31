//import org.example.EliminationBackoffStack
//import org.example.TreiberStack
//import org.jetbrains.kotlinx.lincheck.*
//import org.jetbrains.kotlinx.lincheck.annotations.*
//import org.jetbrains.kotlinx.lincheck.strategy.managed.modelchecking.*
//import org.junit.*
//import org.junit.jupiter.api.Test
//
//class TreiberStackTest {
//    private var stack: TreiberStack<Int> = TreiberStack();
//    @Operation
//    fun push(e: Int) = stack.push(e)
//
//    @Operation
//    fun pop() = stack.pop()
//
//    @Operation
//    fun peek() = stack.peek()
//
//    @Test
//    fun modelCheckingTest() = ModelCheckingOptions().check(this::class)
//}