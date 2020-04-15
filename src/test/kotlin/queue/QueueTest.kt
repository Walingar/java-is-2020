package queue

import junit.framework.TestCase.*
import org.junit.Test
import queue.factory.ArrayQueueFactory
import queue.factory.LinkedQueueFactory
import java.util.*

internal class QueueTest {
    private val rand = Random()

    private fun getTestQueueList() = listOf(ArrayQueueFactory.getInstance(), LinkedQueueFactory.getInstance())

    private fun getTestQueue() = TestQueue(ArrayDeque(), getTestQueueList())

    private fun Queue<Int>.checkNoSuchElementException(f: Queue<Int>.() -> Unit) {
        try {
            f()
            fail()
        } catch (e: NoSuchElementException) {
        }
    }

    @Test
    fun emptyState() {
        getTestQueueList().forEach { queue ->
            assertEquals(0, queue.size)
            assertNull(queue.peek())
            assertNull(queue.poll())
            queue.checkNoSuchElementException {
                remove()
            }
            queue.checkNoSuchElementException {
                element()
            }
        }
    }

    @Test
    fun fewElements() {
        val testQueue = getTestQueue()
        testQueue.add(1)
        testQueue.add(2)
        testQueue.poll()
    }

    @Test
    fun hugeTest() {
        repeat(100) {
            val testQueue = getTestQueue()
            repeat(10_000) {
                testQueue.add(rand.nextInt())
                if (rand.nextBoolean()) {
                    testQueue.poll()
                }
            }
        }
    }
}