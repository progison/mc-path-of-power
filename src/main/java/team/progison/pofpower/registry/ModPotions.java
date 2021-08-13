package team.progison.pofpower.registry;

import team.progison.pofpower.PathOfPowerMod;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModPotions {
    public static final Potion DWARVISH_SENSE_POTION = new Potion(
            new StatusEffectInstance(ModEffects.DWARVISH_SENSE, 3600));
    public static final Potion LONG_DWARVISH_SENSE_POTION = new Potion(
            new StatusEffectInstance(ModEffects.DWARVISH_SENSE, 9600));
    public static final Potion STRONG_DWARVISH_SENSE_POTION = new Potion(
            new StatusEffectInstance(ModEffects.DWARVISH_SENSE, 1800, 1));

    public static void registerAll() {
        Registry.register(Registry.POTION, new Identifier(PathOfPowerMod.MOD_ID, "dwarvish_sense_potion"),
                DWARVISH_SENSE_POTION);
        Registry.register(Registry.POTION, new Identifier(PathOfPowerMod.MOD_ID, "long_dwarvish_sense_potion"),
                LONG_DWARVISH_SENSE_POTION);
        Registry.register(Registry.POTION, new Identifier(PathOfPowerMod.MOD_ID, "strong_dwarvish_sense_potion"),
                STRONG_DWARVISH_SENSE_POTION);
    }
}
