package zhibo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MainTest {
    @Test
    @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
    public void testMain() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);
        InputStream input = getClass().getClassLoader().getResourceAsStream("input.txt");
        assertNotNull(input);
        FileOutputStream fileOut = new FileOutputStream("src/test/resources/output.txt");
        PrintStream printOut = new PrintStream(fileOut);
        InputStream oldIn = System.in;
        PrintStream oldOut = System.out;
        try {
            System.setIn(input);
            System.setOut(printOut);
            Main.main(new String[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
        }
        String expectedContent = Files.readString(Paths.get("src/test/resources/expected.txt"));
        String outputContent = Files.readString(Paths.get("src/test/resources/output.txt"));

        Assertions.assertEquals(expectedContent, outputContent);
    }
}
