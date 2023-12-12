package xyz.merith.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.merith.CodeServer;



import static xyz.merith.CodeServer.MOD_ID;

public class FabricEntrypoint implements ModInitializer {
    @Override
    public void onInitialize() {
        // create logger
        Logger LOGGER = LogManager.getLogger(MOD_ID);
        CodeServer.init();

        // TODO register commands
    }
}