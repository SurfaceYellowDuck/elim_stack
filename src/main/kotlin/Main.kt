package org.example
import java.io.File
fun main() {
    var concat_string = "";
for (i: Int in 0 .. 10000){
    val test1 = ElimBanch()
    val elim_string = test1.test_2()
    val test2 = TreibBanch()
    val treiber_string = test2.test_2()
    concat_string = "$concat_string $elim_string : $treiber_string \n"
    val elim = File("res4.txt")

    elim.writeText(concat_string)
}
}
