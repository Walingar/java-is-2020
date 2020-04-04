package impl.file;

import api.file.FileEncodingWriter;

public class FileEncodingWriterFactory {
    public static FileEncodingWriter getInstance() {
        return new FileEncodingWriterImpl();
    }
}
