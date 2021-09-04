package team.progison.pofpower;

import team.progison.pofpower.registry.ModBlocks;
import team.progison.pofpower.registry.ModScreens;
import team.progison.pofpower.screen.FeedingTroughScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class PathOfPowerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROPE_BLOCK, RenderLayer.getCutout());
        ScreenRegistry.register(ModScreens.FEEDING_TROUGH_SCREEN_HANDLER, FeedingTroughScreen::new);
    }
}
