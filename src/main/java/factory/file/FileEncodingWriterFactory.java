package factory.file;

import api.file.FileEncodingWriter;
import impl.file.FileEncodingWriterImpl;

public class FileEncodingWriterFactory {

  public static FileEncodingWriter getInstance() {
    return new FileEncodingWriterImpl();
  }
}
