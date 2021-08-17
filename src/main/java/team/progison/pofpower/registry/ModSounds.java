package team.progison.pofpower.registry;

import team.progison.pofpower.PathOfPowerMod;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {
    public static final SoundEvent ORE_REVEALS = new SoundEvent(new Identifier(PathOfPowerMod.MOD_ID, "ore_reveals"));

    public static void registerAll() {
        Registry.register(Registry.SOUND_EVENT, ORE_REVEALS.getId(), ORE_REVEALS);
    }
}