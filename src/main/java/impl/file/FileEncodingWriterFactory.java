package impl.file;

import api.file.FileEncodingWriter;
import api.file.FileEncodingWriterImpl;

public class FileEncodingWriterFactory {
    public static FileEncodingWriter getInstance() {
        return new FileEncodingWriterImpl();
    }
}
