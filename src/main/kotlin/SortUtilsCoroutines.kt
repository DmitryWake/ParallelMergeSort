import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

fun mergeSortParallel(array: IntArray, minThreshold: Int = 128) : IntArray {
    val arrayCopy = array.copyOf()
    return runBlocking(Dispatchers.IO) {
        val deferred = async(Dispatchers.IO) { mergeSortRecur(arrayCopy, minThreshold) }
        deferred.await()
    }
}


internal suspend fun mergeSortRecur(array: IntArray, minThreshold: Int) : IntArray {
    if (array.size <= minThreshold) {
        return mergeSortIAFast(array)
    }
    val mid = array.size / 2
    val half1 = array.sliceArray(0 until mid)
    val half2 = array.sliceArray(mid until array.size)

    val result = coroutineScope {
        awaitAll(async(Dispatchers.IO) { mergeSortRecur(half1, minThreshold) }, async(Dispatchers.IO) { mergeSortRecur(half2, minThreshold) })
    }

    mergeSeparated(result.first(), result.last(), output = array)
    return array
}

internal fun mergeSortIAFast(array: IntArray) : IntArray {
    val arrayCopy = array.copyOf()
    sortSectionIA(array, arrayCopy, 0, array.size)
    return arrayCopy
}

internal fun mergeSeparated(half1: IntArray, half2: IntArray, output: IntArray) {
    require(half1.size + half2.size == output.size)
    var p1 = 0
    var p2 = 0
    for (i in 0 until half1.size + half2.size) {
        if (p1 < half1.size && (p2 == half2.size || half1[p1] <= half2[p2])) {
            output[i] = half1[p1]
            p1++
        } else {
            output[i] = half2[p2]
            p2++
        }
    }
}