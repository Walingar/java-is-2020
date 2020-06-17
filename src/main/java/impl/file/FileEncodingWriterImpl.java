package impl.file;

import api.file.FileEncodingWriter;

import java.util.Objects;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

public class FileEncodingWriterImpl implements FileEncodingWriter {

    @Override
    public void write(File file, InputStream data, Charset dataEncoding) {
        write(file, data, dataEncoding, StandardCharsets.UTF_8);
    }

    @Override
    public void write(File file, InputStream data, Charset dataEncoding, Charset fileEncoding) {
        Objects.requireNonNull(file, "File is null");
        Path path = Paths.get(file.getParent());
        try {
            Files.createDirectories(path);
            if (!file.createNewFile()) {
                System.out.println("File already exists " + file.getPath());
            }
        } catch (IOException e) {
            System.out.println("Could not create file or directory " + file.getPath());
        }
        try (var reader = new InputStreamReader(data, dataEncoding);
             var fileWriter = new FileWriter(file, fileEncoding)) {
            var symbol = reader.read();
            while (symbol != -1) {
                fileWriter.write(symbol);
                symbol = reader.read();
            }
        } catch (IOException e) {
            System.err.println("Error during file writing");
        }
    }
}