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

        try (Reader input = new BufferedReader(new InputStreamReader(data, dataEncoding))) {
            if (!file.exists()) {
                if (file.getParentFile().mkdirs()) {
                    if (!file.createNewFile()) {
                        throw new RuntimeException(String.format("error create file"));
                    }
                } else {
                    throw new RuntimeException("error create folder");
                }
            }

            try (Writer output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), fileEncoding))) {
                input.transferTo(output);
            }
        } catch (IOException e) {
            throw new RuntimeException(String.format("error in input stream %s", e.getMessage()));
        }
    }
}
