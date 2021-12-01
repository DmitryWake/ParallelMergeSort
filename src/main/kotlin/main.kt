import kotlin.random.Random

fun main(args: Array<String>) {
    val list = generateList(10000000)

    repeat(3) {
        mergeSortIA(list)
        mergeSortParallel(list)
    }

    var start = System.nanoTime()
    mergeSortIA(list)
    var end = System.nanoTime()

    println((end - start)/10000)

    start = System.nanoTime()
    mergeSortParallel(list)
    end = System.nanoTime()

    println((end - start)/10000)
}

private val random = Random(System.currentTimeMillis())

private fun generateList(size: Int): IntArray {
    return IntArray(size) {
        random.nextInt()
    }
}