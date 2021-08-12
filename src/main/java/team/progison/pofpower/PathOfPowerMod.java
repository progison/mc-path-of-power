package team.progison.pofpower;

import net.fabricmc.api.ModInitializer;
import team.progison.pofpower.registry.*;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PathOfPowerMod implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String namespace = "pofpower";
	public static boolean zoom = false;
	public static boolean lithiumInstalled = false;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		lithiumInstalled = FabricLoader.getInstance().isModLoaded("lithium");

		ModBlocks.registerAll();
	}
}
