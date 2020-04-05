package impl.file;

import api.file.FileEncodingReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

public class FileEncodingReaderFactory {
    public static FileEncodingReader getInstance() {
        var fileReader = new FileEncodingReader() {

            @Override
            public Reader read(File file, Charset fileEncoding) {
                Reader reader = null;
                try {
                    reader = new FileReader(file, fileEncoding);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
                return reader;
            }
        };
        return fileReader;
    }
}
