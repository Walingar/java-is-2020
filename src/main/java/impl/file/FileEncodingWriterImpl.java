package impl.file;

import api.file.FileEncodingWriter;

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
        Path path = Paths.get(file.getParent());
        try {
            Files.createDirectories(path.getParent());
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Could not create file or directory " + file.getPath());
        }

        try (Reader reader = new InputStreamReader(data, dataEncoding);
             Writer writer = new FileWriter(file, fileEncoding)) {
            var ch = reader.read();
            while (ch != -1) {
                writer.write(ch);
                ch = reader.read();
            }
        } catch (IOException e) {
            System.out.println("Could not create reader or writer");
        }
    }
}
