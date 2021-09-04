package team.progison.pofpower.registry;

import team.progison.pofpower.PathOfPowerMod;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class ModTags {
    public static final Tag<Block> BLOCK_ORES = TagRegistry.block(new Identifier(PathOfPowerMod.MOD_ID, "ores"));
    public static final Tag<Block> BLOCK_RARE_ORES = TagRegistry
            .block(new Identifier(PathOfPowerMod.MOD_ID, "rare_ores"));

    public static final Tag<Item> ITEM_FEED = TagRegistry.item(new Identifier(PathOfPowerMod.MOD_ID, "feed"));
}
