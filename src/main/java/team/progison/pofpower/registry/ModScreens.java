package team.progison.pofpower.registry;

import team.progison.pofpower.PathOfPowerMod;
import team.progison.pofpower.screen.FeedingTroughScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreens {
    public static ScreenHandlerType<FeedingTroughScreenHandler> FEEDING_TROUGH_SCREEN_HANDLER;

    public static void registerAll() {
        FEEDING_TROUGH_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(
                new Identifier(PathOfPowerMod.MOD_ID, "feeding_trough"), FeedingTroughScreenHandler::new);
    }
}
