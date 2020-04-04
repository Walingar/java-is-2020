package impl.file;

import api.file.FileEncodingReader;

import java.io.*;
import java.nio.charset.Charset;

public class FileEncodingReaderImpl implements FileEncodingReader {

    @Override
    public Reader read(File file, Charset fileEncoding) throws IOException {
        try {
            return new InputStreamReader(new FileInputStream(file), fileEncoding);
        } catch (IOException e) {
            System.err.print("Unable to open");
        }
        return Reader.nullReader();
    }
}
