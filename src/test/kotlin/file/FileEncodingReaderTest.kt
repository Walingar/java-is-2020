package file

import impl.file.FileEncodingReaderFactory
import impl.file.FileEncodingWriterFactory
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.nio.charset.Charset

internal class FileEncodingReaderTest {
    @Test
    fun `read with default encoding`() {
        readTestFileWithEncoding(Charset.defaultCharset())
    }

    @Test
    fun `read UTF-8`() {
        readTestFileWithEncoding(Charsets.UTF_8)
    }

    @Test
    fun `read CP-1251`() {
        readTestFileWithEncoding(cp1251)
    }

    @Test
    fun `read few times`() {
        readTestFileWithEncoding(Charsets.UTF_8)
        readTestFileWithEncoding(cp1251, "Тестируем...")
    }

    private fun readTestFileWithEncoding(fileEncoding: Charset, data: String = TEST_STRING) {
        val writer = FileEncodingWriterFactory.getInstance()
        val streamEncoding = Charsets.UTF_8
        val stream = data.byteInputStream(streamEncoding)
        writer.write(testFile, stream, streamEncoding, fileEncoding)

        val encodingReader = FileEncodingReaderFactory.getInstance()
        encodingReader.read(testFile, fileEncoding).use { reader ->
            assertEquals(data, reader.readText())
        }
    }
}