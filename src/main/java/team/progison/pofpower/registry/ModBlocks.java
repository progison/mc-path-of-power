package team.progison.pofpower.registry;

import team.progison.pofpower.PathOfPowerMod;
import team.progison.pofpower.block.FeedingTroughBlock;
import team.progison.pofpower.block.RopeBlock;
import team.progison.pofpower.block.entity.FeedingTroughBlockEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final RopeBlock ROPE_BLOCK = new RopeBlock(FabricBlockSettings.of(Material.BAMBOO).strength(0.1F)
            .nonOpaque().noCollision().sounds(BlockSoundGroup.WOOL));
    public static final BlockItem ROPE_ITEM = new BlockItem(ROPE_BLOCK, new FabricItemSettings().group(ItemGroup.MISC));

    public static final FeedingTroughBlock FEEDING_TROUGH_BLOCK = new FeedingTroughBlock(
            FabricBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(0.2F).nonOpaque());
    public static final BlockItem FEEDING_TROUGH_ITEM = new BlockItem(FEEDING_TROUGH_BLOCK,
            new FabricItemSettings().group(ItemGroup.DECORATIONS));
    public static final BlockEntityType<FeedingTroughBlockEntity> FEEDING_TROUGH_BLOCK_ENTITY = FabricBlockEntityTypeBuilder
            .create(FeedingTroughBlockEntity::new, FEEDING_TROUGH_BLOCK).build(null);

    public static void registerAll() {
        Registry.register(Registry.BLOCK, new Identifier(PathOfPowerMod.MOD_ID, "rope"), ROPE_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(PathOfPowerMod.MOD_ID, "rope"), ROPE_ITEM);
        FlammableBlockRegistry.getDefaultInstance().add(ROPE_BLOCK, 15, 45);
        FuelRegistry.INSTANCE.add(ROPE_ITEM, 150);

        Registry.register(Registry.BLOCK, new Identifier(PathOfPowerMod.MOD_ID, "feeding_trough"),
                FEEDING_TROUGH_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(PathOfPowerMod.MOD_ID, "feeding_trough"), FEEDING_TROUGH_ITEM);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(PathOfPowerMod.MOD_ID, "feeding_trough"),
                FEEDING_TROUGH_BLOCK_ENTITY);
        FlammableBlockRegistry.getDefaultInstance().add(FEEDING_TROUGH_BLOCK, 20, 5);
        FuelRegistry.INSTANCE.add(FEEDING_TROUGH_ITEM, 300);
    }
}
