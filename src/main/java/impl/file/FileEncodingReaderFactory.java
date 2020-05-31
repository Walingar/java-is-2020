package impl.file;

import api.file.FileEncodingReader;
import impl.file.FileEncodingReaderImpl;

public class FileEncodingReaderFactory {
    public static FileEncodingReader getInstance() {
        return new FileEncodingReaderImpl();
    }
}
