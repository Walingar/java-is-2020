package impl.file;

import api.file.FileEncodingReader;

import java.io.*;
import java.nio.charset.Charset;

public class FileEncodingReaderImpl implements FileEncodingReader {
    @Override
    public Reader read(File file, Charset fileEncoding) {
        Reader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(file), fileEncoding);
        } catch (FileNotFoundException e) {
            System.out.println("Can't read file " + file.getName());
        }
        return reader;
    }
}
