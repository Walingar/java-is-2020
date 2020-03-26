package queue

import java.util.*

class TestQueue(
    private val approvedQueue: Queue<Int>,
    private val testQueueList: List<Queue<Int>>
) : Queue<Int> by approvedQueue {
    private val operations = mutableListOf<String>()

    override fun add(element: Int?): Boolean {
        operations.add("add $element")
        return approvedQueue.add(element).also {
            testQueueList.forEach { queue ->
                queue.add(element)
                checkQueueState(queue)
            }
        }
    }

    override fun poll(): Int {
        operations.add("poll")
        return approvedQueue.poll().also { expected ->
            testQueueList.forEach { queue ->
                val actual = queue.poll()
                check(actual == expected) {
                    "Incorrect result during poll. Expected $expected but got $actual".appendOperations()
                }
                checkQueueState(queue)
            }
        }
    }

    private fun checkQueueState(queue: Queue<Int>) {
        checkQueueSize(queue)
        checkQueueHead(queue)
    }

    private fun checkQueueHead(queue: Queue<Int>) {
        val actualHead = queue.peek()
        val expectedHead = approvedQueue.peek()
        check(actualHead == expectedHead) {
            "Actual head is $actualHead but expected $expectedHead".appendOperations()
        }
    }

    private fun checkQueueSize(queue: Queue<Int>) {
        val actualSize = queue.size
        val expectedSize = approvedQueue.size
        check(actualSize == expectedSize) {
            "Actual size is $actualSize but expected $expectedSize".appendOperations()
        }
    }

    private fun String.appendOperations() = buildString {
        appendln(this@appendOperations)
        appendln("Operations: $operations")
    }
}