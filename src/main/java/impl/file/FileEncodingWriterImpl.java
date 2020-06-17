package impl.file;

import api.file.FileEncodingWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileEncodingWriterImpl implements FileEncodingWriter {

    @Override
    public void write(File file, InputStream data, Charset dataEncoding) {
        writeToFile(file, data, dataEncoding, StandardCharsets.UTF_8);
    }

    @Override
    public void write(File file, InputStream data, Charset dataEncoding, Charset fileEncoding) {
        writeToFile(file, data, dataEncoding, fileEncoding);
    }

    private void writeToFile(File file, InputStream data, Charset dataEncoding, Charset fileEncoding) {
        File parentFile = file.getParentFile();
        try {
            if (!parentFile.mkdirs()) {
                throw new IOException();
            }
        } catch (IOException e) {
            System.out.println("Can't create directories " + parentFile.getPath());
        }

        try {
            if (file.createNewFile()) {
                System.out.println("Created file " + file.getName());
            }
        } catch (IOException e) {
            System.out.println("Can't create file " + file.getName() + ": " + e.getMessage() + " path: " + file.getPath());
        }

        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(file, fileEncoding));
             InputStreamReader inpStrReader = new InputStreamReader(data, dataEncoding)) {
            int c;
            while ((c = inpStrReader.read()) >= 0) {
                bWriter.write(c);
            }
        } catch (IOException e) {
            System.out.println("Can't write text to file " + file.getName());
        }
    }
}
                