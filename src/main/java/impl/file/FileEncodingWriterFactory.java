package impl.file;

import api.file.FileEncodingWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileEncodingWriterFactory {
    public static FileEncodingWriter getInstance() {
        var fileWriter = new FileEncodingWriter() {
            //`FileEncodingWriter` должен уметь создавать файл и
// записывать в него данные из `InputStream` с кодировкой `dataEncoding` в кодировке `fileEncoding` (`UTF-8`, если `fileEncoding` не передана).
            @Override
            public void write(File file, InputStream data, Charset dataEncoding) {
                int c = 0;
                createFile(file);
                try (Writer writer = new FileWriter(file, StandardCharsets.UTF_8);
                     InputStreamReader inputStreamReader = new InputStreamReader(data, dataEncoding)){
                    while ((c = inputStreamReader.read()) >= 0) {
                        writer.write(c);
                    }
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }

            @Override
            public void write(File file, InputStream data, Charset dataEncoding, Charset fileEncoding) {
                createFile(file);
                var c = 0;
                try (Writer writer = new FileWriter(file, fileEncoding);
                     InputStreamReader inputStreamReader = new InputStreamReader(data, dataEncoding)){
                    while ((c = inputStreamReader.read()) >= 0) {
                        writer.write(c);
                    }
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }

            private void createFile(File file) {
                try {
                    var dirs = file.getParentFile();
                    dirs.mkdirs();
                    dirs.createNewFile();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        };
        return fileWriter;
    }
}
