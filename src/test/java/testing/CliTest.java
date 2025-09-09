package testing;

import com.example.ispw2.view.cli.Implementations;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class CliTest {
    @Test
    public void testLoginCli() {
        String input = String.join(
                "\n",
                "1",
                "anna.bianchi@example.com",
                "annaPass!"
        );
        InputStream test = new ByteArrayInputStream((input+ "\n").getBytes());
        System.setIn(test);
        runTest();
    }

    private void runTest(){
        Implementations implementations = new Implementations();
        implementations.start();
    }
}
