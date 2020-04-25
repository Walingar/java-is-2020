package impl.file;

import api.file.FileEncodingWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public final class FileEncodingWriterImpl implements FileEncodingWriter {
    @Override
    public void write(final File file, final InputStream data, final Charset dataEncoding) {
        write(file, data, dataEncoding, StandardCharsets.UTF_8);
    }

    @Override
    public void write(final File file, final InputStream data, final Charset dataEncoding, final Charset fileEncoding) {
        if (!file.exists()) {
            try {
                Files.createDirectories(file.getParentFile().toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (FileWriter output = new FileWriter(file, fileEncoding);
             InputStreamReader input = new InputStreamReader(data, dataEncoding)) {
            input.transferTo(output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
