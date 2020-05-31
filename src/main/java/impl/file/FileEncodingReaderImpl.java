package impl.file;

import api.file.FileEncodingReader;

import java.io.*;
import java.nio.charset.Charset;

public class FileEncodingReaderImpl implements FileEncodingReader {

    @Override
    public Reader read(File file, Charset fileEncoding) {
        try {
            return new FileReader(file, fileEncoding);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
