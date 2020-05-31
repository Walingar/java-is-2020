package impl.file;

import api.file.FileEncodingException;
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
            var directory = file.getParentFile();
            if (directory != null) {
                Files.createDirectories(directory.toPath());
            }
            doWrite(file, data, dataEncoding, fileEncoding);
        } catch (IOException e) {
            throw new FileEncodingException("An error occurred when writing to file", e);
        }
    }

    private void doWrite(File file, InputStream data, Charset dataEncoding, Charset fileEncoding) throws IOException {
        try (var reader = new BufferedReader(new InputStreamReader(data, dataEncoding));
             var writer = new FileWriter(file, fileEncoding)
        ) {
            int character;
            while ((character = reader.read()) >= 0) {
                writer.write(character);
            }
        }
    }
}