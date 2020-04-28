package impl.file;

import api.file.FileEncodingReader;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Objects;

public class FileEncodingReaderImpl implements FileEncodingReader {
    @Override
    public Reader read(File file, Charset fileEncoding) {
        try {
            return new FileReader(file, fileEncoding);
        } catch (IOException e) {
            System.err.println("Error while opening file " + file.getPath());
        }
        return Reader.nullReader();
    }
}
