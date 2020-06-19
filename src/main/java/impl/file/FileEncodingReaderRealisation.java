package impl.file;

import api.file.FileEncodingReader;
// import kotlin.text.Charsets;
// import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.Charset;

public class FileEncodingReaderRealisation implements FileEncodingReader {
    @Override
    public Reader read(File file, Charset fileEncoding) throws FileNotFoundException {
        return new InputStreamReader(new FileInputStream(file), fileEncoding);
    }
}
