package impl.file;

import api.file.FileEncodingWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileEncodingWriterImpl implements FileEncodingWriter {

  private static final Charset DEFAULT_FILE_ENCODING = StandardCharsets.UTF_8;
  private static final int BUFFER_SIZE = 1024;

  @Override
  public void write(File file, InputStream data, Charset dataEncoding) {
    write(file, data, dataEncoding, DEFAULT_FILE_ENCODING);
  }

  @Override
  public void write(File file, InputStream data, Charset dataEncoding, Charset fileEncoding) {
    try {
      if (!file.exists()) {
        createFile(file);
      }

      fillFileFromInputStream(
          file,
          fileEncoding,
          new char[BUFFER_SIZE],
          new InputStreamReader(data, dataEncoding)
      );
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private void createFile(File file) throws IOException {
    file.getParentFile().mkdirs();

    if (!file.createNewFile()) {
      throw new IOException("Unable to create new file");
    }
  }

  private void fillFileFromInputStream(File file, Charset fileEncoding, char[] buffer, InputStreamReader inputStream) throws IOException {
    int readBytes;
    try (FileWriter outputFileStream = new FileWriter(file, fileEncoding)) {
      while ((readBytes = inputStream.read(buffer, 0, buffer.length)) != -1) {
        outputFileStream.write(buffer, 0, readBytes);
      }
    }
  }
}
