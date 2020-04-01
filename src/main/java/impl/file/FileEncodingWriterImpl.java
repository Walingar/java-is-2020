package impl.file;

import api.file.FileEncodingWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileEncodingWriterImpl implements FileEncodingWriter {
    private static final int BUFFER_SIZE = 8 * 1024;

    @Override
    public void write(File file, InputStream data, Charset dataEncoding) {
        write(file, data, dataEncoding, StandardCharsets.UTF_8);
    }

    @Override
    public void write(File file, InputStream data, Charset dataEncoding, Charset fileEncoding) {
        if (!createDirectories(file)) {
            System.err.println("Can't create file's directory");
            return;
        }
        try (var reader = new InputStreamReader(data, dataEncoding);
             var fileWriter = new FileWriter(file, fileEncoding)) {
            var buffer = new char[BUFFER_SIZE];
            while (true) {
                var bytesRead = reader.read(buffer);
                if (bytesRead == -1) {
                    break;
                }
                fileWriter.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            System.err.println("Error occurred while writing to file: " + e);
        }
    }

    private static boolean createDirectories(File file) {
        if (!file.exists()) {
            var parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                return parent.mkdirs();
            }
        }
        return true;
    }
}
