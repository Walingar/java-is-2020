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
        try {
            File directory = new File((file.getParent()));
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    throw new RuntimeException("Unable to create directory " + directory.toString());
                }
            }
            try (var dataReader = new InputStreamReader(data, dataEncoding);
                 var fileWriter = new FileWriter(file.getAbsoluteFile(), fileEncoding)) {
                int symbol;
                while ((symbol = dataReader.read()) >= 0) {
                    fileWriter.write(symbol);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to write to file", e);
        }
    }
}
