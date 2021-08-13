package team.progison.pofpower.mixin;

import team.progison.pofpower.registry.ModPotions;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrewingRecipeRegistry.class)
public abstract class BrewingRecipeRegistryMixin {
    @Shadow
    private static void registerPotionRecipe(Potion input, Item item, Potion output) {
    }

    @Inject(method = "registerDefaults", at = @At("TAIL"))
    private static void registerPotions(CallbackInfo ci) {
        registerPotionRecipe(Potions.AWKWARD, Items.AMETHYST_SHARD, ModPotions.DWARVISH_SENSE_POTION);
        registerPotionRecipe(ModPotions.DWARVISH_SENSE_POTION, Items.REDSTONE, ModPotions.LONG_DWARVISH_SENSE_POTION);
        registerPotionRecipe(ModPotions.DWARVISH_SENSE_POTION, Items.GLOWSTONE_DUST,
                ModPotions.STRONG_DWARVISH_SENSE_POTION);
    }
}
