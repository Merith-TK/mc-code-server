package xyz.merith.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.merith.CodeServer;

import net.minecraft.server.MinecraftServer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;


import static xyz.merith.CodeServer.MOD_ID;

public class FabricEntrypoint implements ModInitializer {
    Logger LOGGER = LogManager.getLogger(MOD_ID);
    
    @Override
    public void onInitialize() {
        // create logger
        CodeServer.init();

        // TODO register commands
    
        ServerLifecycleEvents.SERVER_STOPPING.register(this::onServerStopping);
    }
    private void onServerStopping(MinecraftServer server) {
        LOGGER.info("CODE SERVER RUNS ASYNC, AS OF THIS MOMENT I CANNOT KILL IT, PLEASE KILL JAVA");
        LOGGER.warn("CODE SERVER RUNS ASYNC, AS OF THIS MOMENT I CANNOT KILL IT, PLEASE KILL JAVA");
        LOGGER.error("CODE SERVER RUNS ASYNC, AS OF THIS MOMENT I CANNOT KILL IT, PLEASE KILL JAVA");
    }
}