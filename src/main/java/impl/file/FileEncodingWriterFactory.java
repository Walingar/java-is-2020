package impl.file;

import api.file.FileEncodingWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileEncodingWriterFactory {
    public static FileEncodingWriter getInstance() {
        return new FileEncodingWriterImpl();
    }
}
