
fun main(args: Array<String>) {
    val mergeBenchmark = MergeBenchmark()
    mergeBenchmark.warmingUp()

    println(mergeBenchmark.testMergeSortParallel(100, 50, 3000))
}

