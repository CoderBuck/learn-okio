import okio.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class WriteFile {

    public void run() throws IOException {
        writeEnv(createFile("temp/env.txt"));
        copyFile();
        markSource();
    }

    public void writeEnv(File file) throws IOException {
        try (Sink sink = Okio.sink(file);
             BufferedSink bufferedSink = Okio.buffer(sink)) {
            for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
                String s = entry.getKey() + " = " + entry.getValue() + "\n";
                bufferedSink.writeUtf8(s);
            }
        }
    }

    public void copyFile() throws IOException {
        try (Source source = Okio.source(new File("temp/env.txt"));
             Sink sink = Okio.sink(new File("temp/copyFile.txt"));
             BufferedSource bufferedSource = Okio.buffer(source);
             BufferedSink bufferedSink = Okio.buffer(sink)) {
            bufferedSink.writeAll(bufferedSource);
        }
    }

    public void markSource() throws IOException {
        File file = new File("temp/env.txt");
        File file1 = new File("temp/markSource.txt");
        try (Source source = Okio.source(file);
             MarkSource markSource = new MarkSource(source,file.length());
             Sink sink = Okio.sink(file1);
             BufferedSource bufferedSource = Okio.buffer(markSource);
             BufferedSink bufferedSink = Okio.buffer(sink)) {
            bufferedSink.writeAll(bufferedSource);
        }
    }


    public File createFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) return file;

        File parentFile = file.getParentFile();
        if (parentFile != null && (!parentFile.exists() || !parentFile.isDirectory())) {
            parentFile.mkdirs();
        }
        file.createNewFile();
        return file;
    }
}
