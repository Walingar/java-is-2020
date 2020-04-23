package impl.file;

import api.file.FileEncodingWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileEncodingWriterImpl implements FileEncodingWriter {
    @Override
    public void write(File file, InputStream data, Charset dataEncoding) {
        write(file, data, dataEncoding, StandardCharsets.UTF_8);
    }

    @Override
    public void write(File file, InputStream data, Charset dataEncoding, Charset fileEncoding) {
        try {
            File dir = file.getParentFile();
            if (dir != null) {
                Files.createDirectories(dir.toPath());
            }
            writeFunction(file, data, dataEncoding, fileEncoding);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create a directory or write to file:" + file.getPath() + ", " + e.getMessage());
        }
    }

    private void writeFunction(File file, InputStream data, Charset dataEncoding, Charset fileEncoding) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(data, dataEncoding));
             FileWriter writer = new FileWriter(file, fileEncoding)) {
            int symbol;
            while ((symbol = reader.read()) >= 0) {
                writer.write(symbol);
            }
        }
    }
}
