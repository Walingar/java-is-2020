package impl.file;

import api.file.FileEncodingWriter;
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
        try (var reader = new BufferedReader(new InputStreamReader(data, dataEncoding))) {
            try (var writer = new FileWriter(file, fileEncoding)) {
                int character;
                while ((character = reader.read()) != -1) {
                    writer.write(character);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
