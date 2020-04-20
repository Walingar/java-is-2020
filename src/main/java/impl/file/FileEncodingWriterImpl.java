package impl.file;

import api.file.FileEncodingWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileEncodingWriterImpl implements FileEncodingWriter {

    private final Charset defaultCharset;

    public FileEncodingWriterImpl() {
        defaultCharset = StandardCharsets.UTF_8;
    }

    @Override
    public void write(File file, InputStream data, Charset dataEncoding) {
        write(file, data, dataEncoding, defaultCharset);
    }

    @Override
    public void write(File file, InputStream data, Charset dataEncoding, Charset fileEncoding) {
        var parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            if (!parent.mkdirs()) {
                // It makes sense to throw here... Hmm...
                System.err.println("Could not create directory for the file");
            }
        }

        try (var reader = new InputStreamReader(data, dataEncoding);
             var writer = new FileWriter(file, fileEncoding)) {
            var s = reader.read();
            while (s != -1) {
                writer.write(s);
                s = reader.read();
            }
        } catch (IOException e) {
            System.err.println(String.format("Something went wrong during I/O: %s", e.getMessage()));
        }
    }
}
