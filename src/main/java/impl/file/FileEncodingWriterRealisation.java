package impl.file;

import api.file.FileEncodingWriter;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;

public class FileEncodingWriterRealisation implements FileEncodingWriter {
    @Override
    public void write(File file, InputStream data, Charset dataEncoding) {
        write(file, data, dataEncoding, StandardCharsets.UTF_8);
    }


    @Override
    public void write(File file, InputStream data, Charset dataEncoding, Charset fileEncoding){
        try {
            InputStreamReader input= new InputStreamReader(data, dataEncoding);

            OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(file), fileEncoding);

            input.transferTo(output);
            output.flush();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
