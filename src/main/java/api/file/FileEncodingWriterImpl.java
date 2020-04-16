package api.file;

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
        File file_dir = file.getParentFile();

        if (file_dir != null && !file_dir.exists() && !file_dir.mkdirs()){
            throw new RuntimeException("Failed to create directory");
        }

        try {
            FileWriter fileWriter = new FileWriter(file, fileEncoding);
            InputStreamReader reader = new InputStreamReader(data, dataEncoding);
            int symbol = reader.read();
            while (symbol != -1) {
                fileWriter.write(symbol);
                symbol = reader.read();
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}