package impl.file;

import api.file.FileEncodingWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileEncodingWriterImpl implements FileEncodingWriter {
    @Override
    public void write(File file, InputStream data, Charset dataEncoding) {
        write(file, data, dataEncoding, StandardCharsets.UTF_8);
    }

    @Override
    public void write(File file, InputStream data, Charset dataEncoding, Charset fileEncoding) {
        var path = Paths.get(file.getParent());
        try {
            Files.createDirectories(path);
            if (!file.createNewFile()) {
                System.out.println(file.getPath() + " already exists");
            }
        } catch (IOException e) {
            System.out.println("File / Directory creating error");
        }
        try (Reader reader = new InputStreamReader(data, dataEncoding);
             Writer writer = new FileWriter(file, fileEncoding)) {
            var symbol = reader.read();
            while (symbol != -1) {
                writer.write(symbol);
                symbol = reader.read();
            }
        } catch (IOException e) {
            System.out.println("Reader / Writer creating error");
        }
    }
}
