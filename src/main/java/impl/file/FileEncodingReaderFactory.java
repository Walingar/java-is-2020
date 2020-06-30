package impl.file;

import api.file.FileEncodingReader;

public class FileEncodingReaderFactory {
    public static FileEncodingReader getInstance() {
        return new FileEncodingReaderImpl();
    }
}
