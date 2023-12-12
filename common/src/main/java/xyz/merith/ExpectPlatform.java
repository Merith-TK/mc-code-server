package xyz.merith;

import dev.architectury.platform.Platform;

import java.nio.file.Path;

// I have no idea why this is needed, but it is.
// otherwise, I would remove it
// -- Merith.TK
public class ExpectPlatform {
    /**
     * We can use {@link Platform#getConfigFolder()} but this is just an example of {@link dev.architectury.injectables.annotations.ExpectPlatform}.
     * <p>
     * This must be a <b>public static</b> method. The platform-implemented solution must be placed under a
     * platform sub-package, with its class suffixed with {@code Impl}.
     * <p>
     * Example:
     * Expect: xyz.merith.ExampleExpectPlatform#getConfigDirectory()
     * Actual Fabric: net.code-server.fabric.ExampleExpectPlatformImpl#getConfigDirectory()
     * Actual Forge: net.code-server.forge.ExampleExpectPlatformImpl#getConfigDirectory()
     * <p>
     * <a href="https://plugins.jetbrains.com/plugin/16210-architectury">You should also get the IntelliJ plugin to help with @ExpectPlatform.</a>
     */
    @dev.architectury.injectables.annotations.ExpectPlatform
    public static Path getConfigDirectory() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }
}
