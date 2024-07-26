package xyz.merith;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class Execute {

    static Logger LOGGER = LogManager.getLogger("code-server-exec");

    public static ProcessBuilder NewCmd(String... command) {
        LOGGER.info("Building: {}", (Object) command);
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(command);
        return processBuilder;
    }

    public static void RunWait(ProcessBuilder cmd) {
        try {
            LOGGER.info("Running: {}", cmd.command());
            Process process = cmd.start();

            // Start threads to read output and error streams
            Thread outputThread = new Thread(() -> logStream(process.getInputStream(), LOGGER::info));
            Thread errorThread = new Thread(() -> logStream(process.getErrorStream(), LOGGER::error));
            outputThread.start();
            errorThread.start();

            process.waitFor();

            outputThread.join();
            errorThread.join();

        } catch (Exception e) {
            LOGGER.error("Failed to run command: {}", cmd.command(), e);
        }
    }

    public static void stop(ProcessBuilder cmd) {
        try {
            Process process = cmd.start();
            process.waitFor();
        } catch (Exception e) {
            LOGGER.error("Failed to run command: {}", cmd.command(), e);
        }
    }

    private static void logStream(InputStream inputStream, Consumer<String> logMethod) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logMethod.accept(line);
            }
        } catch (Exception e) {
            LOGGER.error("Error reading stream", e);
        }
    }
}
