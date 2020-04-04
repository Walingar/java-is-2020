package impl.file;

import api.file.FileEncodingWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileEncodingWriterImpl implements FileEncodingWriter {

    private final int Size = 512;

    @Override
    public void write(File file, InputStream data, Charset dataEncoding) {
        write(file, data, dataEncoding, StandardCharsets.UTF_8);
    }

    @Override
    public void write(File file, InputStream data, Charset dataEncoding, Charset fileEncoding) {
        if (!file.exists()) {
            var parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
        }
        try (FileWriter writer = new FileWriter(file, fileEncoding);
             InputStreamReader reader = new InputStreamReader(data, dataEncoding)) {
            char[] input = new char[Size];
            while (true) {
                int numBytes = reader.read(input);
                if (numBytes == -1) {
                    break;
                }
                writer.write(input, 0, numBytes);
            }
        } catch (IOException e) {
            System.err.println("unable to write");
        }
    }
}
