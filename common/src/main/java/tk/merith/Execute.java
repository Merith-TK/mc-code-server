package tk.merith;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class Execute {

    static Logger LOGGER = LogManager.getLogger("code-server-exec");
    public static ProcessBuilder NewCmd(String... command) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(command);
        processBuilder.inheritIO();
        return processBuilder;
    }

    public static void RunWait(ProcessBuilder cmd) {
        try {
            Process process = cmd.start();
            process.waitFor();
        } catch (Exception e) {
            // log error
            LOGGER.error("Failed to run command" + cmd.command(), "\n", e);
        }
    }

    public static void stop(ProcessBuilder cmd) {
        try {
            Process process = cmd.start();
            process.waitFor();
        } catch (Exception e) {
            // log error
            LOGGER.error("Failed to run command" + cmd.command(), "\n", e);
        }
    }
}
