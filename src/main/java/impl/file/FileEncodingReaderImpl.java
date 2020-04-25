package impl.file;

import api.file.FileEncodingReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

public final class FileEncodingReaderImpl implements FileEncodingReader {
    @Override
    public Reader read(final File file, final Charset fileEncoding) {
        try {
            return new FileReader(file, fileEncoding);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
