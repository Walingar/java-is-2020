package impl.file;

import api.file.FileEncodingReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

public class FileEncodingReaderImpl implements FileEncodingReader {
    @Override
    public Reader read(File file, Charset fileEncoding) {
        try {
            return new BufferedReader(new FileReader(file, fileEncoding));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file!");
        }
    }
}
