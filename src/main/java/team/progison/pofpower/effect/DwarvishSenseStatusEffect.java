package team.progison.pofpower.effect;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import team.progison.pofpower.registry.ModSounds;
import team.progison.pofpower.registry.ModTags;

public class DwarvishSenseStatusEffect extends StatusEffect {
    private Random r = new Random();

    public DwarvishSenseStatusEffect() {
        super(StatusEffectType.BENEFICIAL, 0xffff00);
    }

    @Override
    public boolean canApplyUpdateEffect(int remainingTicks, int amplifier) {
        return remainingTicks % 5 == 0;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        double entityX = entity.getX();
        double entityY = entity.getY();
        double entityZ = entity.getZ();
        World entityWorld = entity.world;
        double radius = 3 + amplifier;

        for (int x = (int) -radius; x <= radius; x++) {
            for (int y = (int) -radius; y <= radius; y++) {
                for (int z = (int) -radius; z <= radius; z++) {
                    BlockPos blockPos = new BlockPos(entityX + x, entityY + y, entityZ + z);
                    BlockState blockState = entityWorld.getBlockState(blockPos);

                    if ((amplifier == 0 && blockState.isIn(ModTags.BLOCK_ORES))
                            || blockState.isIn(ModTags.BLOCK_RARE_ORES)) {
                        float dist = (float) Math.sqrt(Math.pow(entityX - (double) blockPos.getX() - 0.5, 2)
                                + Math.pow(entity.getEyeY() - (double) blockPos.getY() - 0.5, 2)
                                + Math.pow(entityZ - (double) blockPos.getZ() - 0.5, 2)) - 0.5f;
                        float volume = (1 - dist / ((float) radius));

                        if (volume > 0 && r.nextInt(20) == 0) {
                            float minVolume = 0.03f;
                            float maxVolume = 1f;
                            volume = minVolume + volume * (maxVolume - minVolume);
                            entityWorld.playSound(null, blockPos, ModSounds.ORE_REVEALS, SoundCategory.BLOCKS,
                                    (float) volume, 1f);
                        }
                    }
                }
            }
        }
    }
}
