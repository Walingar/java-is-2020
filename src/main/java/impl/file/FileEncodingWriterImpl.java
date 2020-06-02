package impl.file;

import api.file.FileEncodingWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileEncodingWriterImpl implements FileEncodingWriter {
    @Override
    public void write(File file, InputStream data, Charset dataEncoding) {
        write(file,data,dataEncoding, StandardCharsets.UTF_8);
    }

    @Override
    public void write(File file, InputStream data, Charset dataEncoding, Charset fileEncoding) {
        try {
            File path = file.getParentFile();
            if (path != null) {
                Files.createDirectories(path.toPath());
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(data, dataEncoding));
                 FileWriter writer = new FileWriter(file, fileEncoding)) {
                int symbol;
                while ((symbol = reader.read()) >= 0) {
                    writer.write(symbol);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
