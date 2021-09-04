package team.progison.pofpower;

import net.fabricmc.api.ModInitializer;
import team.progison.pofpower.registry.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PathOfPowerMod implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Path of POWER!!!");
	public static final String MOD_ID = "pofpower";

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModBlocks.registerAll();
		ModEffects.registerAll();
		ModPotions.registerAll();
		ModScreens.registerAll();
	}
}
