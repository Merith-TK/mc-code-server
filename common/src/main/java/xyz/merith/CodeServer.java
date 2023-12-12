package xyz.merith;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.fabricmc.api.EnvType;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CodeServer {
    public static final String MOD_ID = "code-server";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static ProcessBuilder codeServerBuilder;
    public static Process codeServerProcess;
    public static Path codeServerDir = Paths.get(".code-server");



    public static void init() {
        // TODO: if on client, `return;`
        LOGGER.info("NOTICE: This mod is in early development and may not work as expected.");
        String hostOS = System.getProperty("os.name").toLowerCase();
        String arch = System.getProperty("os.arch").toLowerCase();

        Env env = Platform.getEnvironment();
        if (env.toPlatform() == EnvType.CLIENT) {
            LOGGER.error("Cannot run on Client");
            return; //please no code server on client
        }

        LifecycleEvent.SERVER_STOPPED.register((server) -> {
            stopCodeServer();
        });

        // supported system
        // arch: x86_64 (amd64), arm64, armhf
        // host: linux
        boolean supportedHost = false;
        boolean supportedArch = true;
        if (hostOS.contains("linux")) {
            supportedHost = true;
        }
        supportedArch = true;
        switch (arch) {
            case "x86_64", "amd64" -> arch = "amd64";
            case "aarch64" -> arch = "arm64";
            case "armhf" -> arch = "armv7l";
            default -> supportedArch = false;
        }


        LOGGER.warn("Host: [" + hostOS + "]: " + supportedHost);
        LOGGER.warn("Arch: [" + arch + "]: " + supportedArch);
        if (!supportedHost || !supportedArch) {
            LOGGER.error("Unsupported host or architecture.");
            return;
        }

        Path codeServerDir = Paths.get(".code-server");
        try {
            Files.createDirectories(codeServerDir);
        } catch (IOException e) {
            LOGGER.error("Failed to create directories for code-server");
            e.printStackTrace();
            return;
        }
        if (!supportedHost) {
            LOGGER.error("Code Server is not supported on this host. (windows)");
            return;
        }
        try {
            LOGGER.info("Downloading code-server (logs to console not log file)");
            URL installScriptURL = new URL("https://raw.githubusercontent.com/cdr/code-server/main/install.sh");
            FileUtils.copyURLToFile(installScriptURL, codeServerDir.resolve("code-server.sh").toFile());
            ProcessBuilder installCMD = Execute.NewCmd(
                    "bash", codeServerDir.resolve("code-server.sh").toString(),
                    "--method", "standalone");
            installCMD.environment().put("HOME", codeServerDir.toAbsolutePath().toString());
            installCMD.environment().put("VERSION", "");
            Execute.RunWait(installCMD);
        } catch (MalformedURLException e) {
            LOGGER.error("Failed to download code-server install script");
            e.printStackTrace();
            return;
        } catch (IOException e) {
            LOGGER.error("Failed to copy code-server install script to .code-server");
            e.printStackTrace();
            return;
        }
        LOGGER.info("CodeServer Downloaded!");
        startCodeServer();
    }


    public static void stopCodeServer() {
        LOGGER.info("Stopping code-server");
        codeServerProcess.destroy();
        LOGGER.info("CodeServer Stopped!");
    }

    public static void startCodeServer() {
        LOGGER.info("Starting code-server");
        LOGGER.info("All command flags map to config.yaml values. (if a value is missing, you can add it to .code-server/.config/code-server/config.yaml)");
        codeServerBuilder = Execute.NewCmd("bash", "-c",
                codeServerDir.resolve(".local/bin/code-server").toString(),
                "--disable-telemetry",
                "--disable-update-check");
        try {
            // set HOME to codeServerDir full path
            codeServerBuilder.environment().put("HOME", codeServerDir.toAbsolutePath().toString());
            codeServerBuilder.environment().put("PATH", codeServerDir.resolve(".bin").toAbsolutePath().toString() + ":" + System.getenv("PATH"));
            codeServerProcess = codeServerBuilder.start();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("CodeServer Setup Complete!");
    }

    public static Boolean isRunning() {
        if (codeServerProcess.isAlive()) {
            LOGGER.info("CodeServer is running!");
            return true;
        } else {
            LOGGER.info("CodeServer is not running!");
            return false;
        }
    }
}
