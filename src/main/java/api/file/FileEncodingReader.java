package api.file;

import java.io.File;
import java.io.Reader;
import java.nio.charset.Charset;

public interface FileEncodingReader {
    Reader read(File file, Charset fileEncoding);
}
