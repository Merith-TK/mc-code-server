package xyz.merith.forge;

import dev.architectury.platform.forge.EventBuses;
import xyz.merith.CodeServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;

@Mod(CodeServer.MOD_ID)
public class ForgeEntrypoint {
    public ForgeEntrypoint() {
        EventBuses.registerModEventBus(CodeServer.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        CodeServer.init();
        // TODO: Register Forge commands for start/stop/restart/reinstall
    }
}
