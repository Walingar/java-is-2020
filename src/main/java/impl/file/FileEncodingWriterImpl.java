package impl.file;

import api.file.FileEncodingWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileEncodingWriterImpl implements FileEncodingWriter {

    private final Charset defaultEncoding;

    public FileEncodingWriterImpl() {
        defaultEncoding = StandardCharsets.UTF_8;
    }

    @Override
    public void write(File file, InputStream data, Charset dataEncoding) {
        write(file, data, dataEncoding, defaultEncoding);
    }

    @Override
    public void write(File file, InputStream data, Charset dataEncoding, Charset fileEncoding) {
        try (Reader in = new BufferedReader(new InputStreamReader(data, dataEncoding))) {
            if (!file.exists()) {
                boolean fileCreated = file.getParentFile().mkdirs();
                fileCreated = fileCreated && file.createNewFile();
                if (!fileCreated) {
                    throw new FileNotFoundException("cant find or create file");
                }
            }
            try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), fileEncoding))) {
                in.transferTo(out);
            }
        } catch (IOException e) {
            System.out.println("something went wrong:");
            System.out.println(e.getMessage());
        }
    }
}
