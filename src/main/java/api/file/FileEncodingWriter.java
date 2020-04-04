package api.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public interface FileEncodingWriter {
    void write(File file, InputStream data, Charset dataEncoding) throws IOException;

    void write(File file, InputStream data, Charset dataEncoding, Charset fileEncoding) throws IOException;
}
