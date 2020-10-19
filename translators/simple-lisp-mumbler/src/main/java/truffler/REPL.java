package truffler;

import truffler.env.BaseEnvironment;
import truffler.env.Environment;
import truffler.form.Form;
import truffler.form.ListForm;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class REPL {

    private static ListForm read(String data) throws Exception {

        try {
            return Reader.read(new ByteArrayInputStream(data.getBytes()));
        } catch (IOException e) {
            System.err.println("IO error trying to read: " + e);
            throw new Exception();
        }
    }

    public static void main(String[] args) {

        boolean useConsoleReader = !(args.length != 0
                && "system".equals(args[0].trim()));

        LineReader console = useConsoleReader
                ? new ConsoleLineReader()
                : new SystemLineReader();

        Environment env = BaseEnvironment.getBaseEnvironment();

        while (true) {
            try {

                String data = console.readLine();
                if (data == null || "(quit)".equals(data.trim())) {
                    return;
                }

                ListForm forms = read(data);

                Object output = null;
                for (Form form : forms) {
                    output = form.eval(env);
                }
                System.out.println(output);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    interface LineReader extends AutoCloseable {
        String readLine() throws IOException;
    }

    static class SystemLineReader implements LineReader {

        final BufferedReader reader;

        private SystemLineReader() {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        @Override
        public String readLine() throws IOException {
            System.out.printf("-> ");
            return reader.readLine();
        }

        @Override
        public void close() throws IOException {
            reader.close();
        }
    }

    static class ConsoleLineReader implements LineReader {

        final Console console;

        private ConsoleLineReader() {
            console = System.console();
        }

        @Override
        public String readLine() throws IOException {
            return console.readLine("~> ");
        }

        @Override
        public void close() throws IOException {
        }
    }
}
