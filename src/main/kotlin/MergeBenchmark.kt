import kotlin.random.Random

class MergeBenchmark {

    private val random = Random(System.currentTimeMillis())

    fun warmingUp() {
        repeat(3) {
            val list = generateList(1000)
            mergeSortIA(list)
            mergeSortParallel(list)
        }
    }

    private fun generateList(size: Int): IntArray {
        return IntArray(size) {
            random.nextInt()
        }
    }

    fun testMergeSort(startSize: Int, step: Int, count: Int): List<Long> {
        var size = startSize
        val result = mutableListOf<Long>()

        repeat(count) {
            val array = generateList(size)
            val start = System.nanoTime()
            mergeSortIA(array)
            val end = System.nanoTime()

            result.add((end - start) / 10000)
            size += step
        }

        return result
    }

    fun testMergeSortParallel(startSize: Int, step: Int, count: Int): List<Long> {
        var size = startSize
        val result = mutableListOf<Long>()

        repeat(count) {
            val array = generateList(size)
            val start = System.nanoTime()
            mergeSortParallel(array)
            val end = System.nanoTime()

            result.add((end - start) / 10000)
            size += step
        }

        return result
    }
}