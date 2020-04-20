package impl.file;

import api.file.FileEncodingReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

public class FileEncodingReaderFactory {
    public static FileEncodingReader getInstance() {
        return new FileEncodingReaderImpl();
    }
}
