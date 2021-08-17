package team.progison.pofpower.registry;

import team.progison.pofpower.PathOfPowerMod;
import team.progison.pofpower.effect.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEffects {
    public static final StatusEffect DWARVISH_SENSE = new DwarvishSenseStatusEffect();

    public static void registerAll() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(PathOfPowerMod.MOD_ID, "dwarvish_sense"),
                DWARVISH_SENSE);
    }
}