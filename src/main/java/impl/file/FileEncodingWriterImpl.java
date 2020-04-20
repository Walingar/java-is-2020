package impl.file;

import api.file.FileEncodingWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileEncodingWriterImpl implements FileEncodingWriter {
    @Override
    public void write(File file, InputStream data, Charset dataEncoding) {
        write(file, data, dataEncoding, StandardCharsets.UTF_8);
    }

    @Override
    public void write(File file, InputStream data, Charset dataEncoding, Charset fileEncoding) {
        createFile(file);
        var c = 0;
        try (Writer writer = new FileWriter(file, fileEncoding);
             InputStreamReader inputStreamReader = new InputStreamReader(data, dataEncoding)){
            while ((c = inputStreamReader.read()) >= 0) {
                writer.write(c);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void createFile(File file) {
        try {
            var dirs = file.getParentFile();
            dirs.mkdirs();
            dirs.createNewFile();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
