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
        CreateIfNeeded(file);

        try {
            FileWriter fileWriter = new FileWriter(file, fileEncoding);
            InputStreamReader streamReader = new InputStreamReader(data, dataEncoding);

            streamReader.transferTo(fileWriter);
            fileWriter.flush();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void CreateIfNeeded(File file) {
        if (!file.exists()) {
            Path parentPath = Paths.get(file.getParent());
            try {
                Files.createDirectories(parentPath);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }
}
