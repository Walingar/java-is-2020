package impl.file;

import api.file.FileEncodingWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileEncodingWriterImpl implements FileEncodingWriter {
    @Override
    public void write(File file, InputStream data, Charset dataEncoding) {
        write(file, data, dataEncoding, StandardCharsets.UTF_8);
    }

    @Override
    public void write(File file, InputStream data, Charset dataEncoding, Charset fileEncoding) {
        Path path = Paths.get(file.getParent());
        try {
            Files.createDirectories(path);
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Cannot create new file");
        }

        try (var reader = new InputStreamReader(data, dataEncoding);
             var fileWriter = new FileWriter(file, fileEncoding)) {
            var charArrayLength = 2048;
            char[] charArray = new char[charArrayLength];
            int numRead = 0;
            while ((numRead = reader.read(charArray, 0, charArrayLength)) >= 0) {
                fileWriter.write(charArray, 0, numRead);
            }
        } catch (IOException e) {
            System.err.println("Error occurred during the file writing");
        }
    }
}
