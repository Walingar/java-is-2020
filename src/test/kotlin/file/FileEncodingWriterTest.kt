package file

import impl.file.FileEncodingWriterFactory
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import java.nio.charset.Charset

internal class FileEncodingWriterTest {
    @Test
    fun `write UTF-8`() {
        checkWriteWithEncoding(Charsets.UTF_8)
    }

    @Test
    fun `write CP-1251`() {
        checkWriteWithEncoding(cp1251)
    }

    @Test
    fun `write 2 times`() {
        checkWriteWithEncoding(cp1251)
        checkWriteWithEncoding(Charsets.UTF_8, data = "Какая-то другая строчка")
    }

    @Test
    fun `file availability after writing`() {
        val encoding = Charsets.UTF_8
        checkWriteWithEncoding(encoding)
        testFile.writeText("Some text", encoding)
        checkWriteWithEncoding(encoding)
    }

    @Test
    fun `write with CP-1251 file encoding`() {
        checkWriteWithEncoding(Charsets.UTF_8, data = "Строчка на русском", fileEncoding = cp1251)
        checkWriteWithEncoding(cp1251, fileEncoding = cp1251)
    }

    private fun checkWriteWithEncoding(
        streamEncoding: Charset,
        data: String = TEST_STRING,
        fileEncoding: Charset = Charsets.UTF_8
    ) {
        val writer = FileEncodingWriterFactory.getInstance()
        val stream = data.byteInputStream(streamEncoding)
        if (fileEncoding == Charsets.UTF_8) {
            writer.write(testFile, stream, streamEncoding)
        } else {
            writer.write(testFile, stream, streamEncoding, fileEncoding)
        }
        assertTrue(testFile.exists())
        assertTrue(testFile.isFile)
        assertEquals(data, testFile.readText(fileEncoding))
    }
}