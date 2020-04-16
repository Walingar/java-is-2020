package impl.file;

import api.file.FileEncodingWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileEncodingWriterImpl implements FileEncodingWriter {

    @Override
    public void write(File file, InputStream data, Charset dataEncoding) {
        write(file, data, dataEncoding, StandardCharsets.UTF_8);
    }

    @Override
    public void write(File file, InputStream data, Charset dataEncoding, Charset fileEncoding) {
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            throw new RuntimeException("Cannot create directory " + file.getParentFile() + "!");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(data, dataEncoding))) {
            try (BufferedWriter writer =
                         new BufferedWriter(new FileWriter(file, fileEncoding))) {
                reader.transferTo(writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
