package team.progison.pofpower.registry;

import team.progison.pofpower.PathOfPowerMod;
import team.progison.pofpower.block.RopeBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final RopeBlock ROPE_BLOCK = new RopeBlock(
            FabricBlockSettings.of(Material.BAMBOO).noCollision().sounds(BlockSoundGroup.WOOL));

    public static void registerAll() {
        Registry.register(Registry.BLOCK, new Identifier(PathOfPowerMod.namespace, "rope"), ROPE_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(PathOfPowerMod.namespace, "rope"),
                new BlockItem(ROPE_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
    }
}
