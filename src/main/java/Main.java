
import okio.BufferedSource;
import okio.Okio;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        BufferedSource buffer = Okio.buffer(Okio.source(new File("1.txt")));

    }
}
