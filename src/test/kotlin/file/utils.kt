package file

import java.nio.charset.Charset
import java.nio.file.Path

internal val testFile = Path.of("C:", "Users", "nguye", "Desktop", "test.txt").toFile()

internal const val TEST_STRING = "Тесты упали?"

internal val cp1251 = Charset.forName("CP1251")