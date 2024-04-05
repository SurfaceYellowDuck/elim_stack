import org.example.EliminationBackoffStack
import org.jetbrains.kotlinx.lincheck.*
import org.jetbrains.kotlinx.lincheck.annotations.*
import org.jetbrains.kotlinx.lincheck.strategy.stress.StressOptions
import org.junit.jupiter.api.Test

class EliminationBackoffStackTest {
    private var stack: EliminationBackoffStack<Int> = EliminationBackoffStack();
    @Operation
    fun push(e: Int) = stack.push(e)

    @Operation
    fun pop() = stack.pop()


    @Operation
    fun peek() = stack.peek()


    @Test
    fun stressTest() = StressOptions()
        .sequentialSpecification(SequentialIntStack::class.java)
        .check(this::class)
}