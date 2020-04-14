package impl.file;

import api.file.FileEncodingWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileEncodingWriterImpl implements FileEncodingWriter {

    @Override
    public void write(File file, InputStream data, Charset dataEncoding) {
        createFile(file);
        try (BufferedWriter bWriter = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8);
             InputStreamReader inpStrReader = new InputStreamReader(data, dataEncoding)) {
            int c;
            while ((c = inpStrReader.read()) >= 0) {
                bWriter.write(c);
            }
        } catch (IOException e) {
            System.out.println("Can't write text to file " + file.getName());
        }
    }

    @Override
    public void write(File file, InputStream data, Charset dataEncoding, Charset fileEncoding) {
        createFile(file);
        try (BufferedWriter bWriter = Files.newBufferedWriter(file.toPath(), fileEncoding);
             InputStreamReader inpStrReader = new InputStreamReader(data, dataEncoding)) {
            int c;
            while ((c = inpStrReader.read()) >= 0) {
                bWriter.write(c);
            }
        } catch (IOException e) {
            System.out.println("Can't write text to file " + file.getName());
        }
    }

    private void createFile(File file) {
        File parentFile = file.getParentFile();
        if (!parentFile.mkdirs()) {
            System.out.println("Can't create directories " + parentFile.getPath());
        }
        try {
            if (file.createNewFile()) {
                System.out.println("Created file " + file.getName());
            }
        } catch (Exception e) {
            System.out.println("Can't create file " + file.getName() + ": " + e.getMessage() + " path: " + file.getPath());
        }
    }
}
                