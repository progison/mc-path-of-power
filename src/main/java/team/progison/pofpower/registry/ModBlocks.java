package team.progison.pofpower.registry;

import team.progison.pofpower.PathOfPowerMod;
import team.progison.pofpower.block.RopeBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final RopeBlock ROPE_BLOCK = new RopeBlock(FabricBlockSettings.of(Material.BAMBOO).strength(0.1F)
            .nonOpaque().noCollision().sounds(BlockSoundGroup.WOOL));

    public static void registerAll() {
        Registry.register(Registry.BLOCK, new Identifier(PathOfPowerMod.MOD_ID, "rope"), ROPE_BLOCK);
        BlockItem rope = new BlockItem(ROPE_BLOCK, new FabricItemSettings().group(ItemGroup.MISC));
        Registry.register(Registry.ITEM, new Identifier(PathOfPowerMod.MOD_ID, "rope"), rope);
        FlammableBlockRegistry.getDefaultInstance().add(ROPE_BLOCK, 15, 45);
        FuelRegistry.INSTANCE.add(rope, 150);
    }
}
