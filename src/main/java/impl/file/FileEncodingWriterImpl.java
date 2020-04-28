package impl.file;

import api.file.FileEncodingWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileEncodingWriterImpl implements FileEncodingWriter {
    private static final int IO_BUFF_SIZE = 4096;
    private static final Charset DEFAULT_FILE_ENCODING = StandardCharsets.UTF_8;

    public void write(File file, InputStream data, Charset dataEncoding) {
        write(file, data, dataEncoding, DEFAULT_FILE_ENCODING);
    }

    public void write(File file, InputStream data, Charset dataEncoding, Charset fileEncoding) {
        if (!file.exists()) {
            File fileDir = file.getParentFile();

            if (fileDir != null && !fileDir.exists() && !fileDir.mkdirs()) {
                throw new RuntimeException("Failed to create parent directory");
            }
        }

        try (FileWriter file_stream = new FileWriter(file, fileEncoding)) {
            InputStreamReader reader = new InputStreamReader(data, dataEncoding);
            char []buff = new char[IO_BUFF_SIZE];
            int read_bytes;

            while ((read_bytes = reader.read(buff, 0, buff.length)) != -1) {
                file_stream.write(buff, 0, read_bytes);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}