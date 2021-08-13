package team.progison.pofpower.effect;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import team.progison.pofpower.registry.ModTags;

public class DwarvishSenseStatusEffect extends StatusEffect {
    Random r = new Random();

    public DwarvishSenseStatusEffect() {
        super(StatusEffectType.BENEFICIAL, 0xffff00);
    }

    @Override
    public boolean canApplyUpdateEffect(int remainingTicks, int amplifier) {
        return remainingTicks % 30 == 0;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        double entityX = entity.getX();
        double entityY = entity.getY();
        double entityZ = entity.getZ();
        World entityWorld = entity.world;
        double radius = amplifier * 2 + 3;

        for (int x = (int) -radius; x <= radius; x++) {
            for (int y = (int) -radius; y <= radius; y++) {
                for (int z = (int) -radius; z <= radius; z++) {
                    BlockPos blockPos = new BlockPos(entityX + x, entityY + y, entityZ + z);
                    BlockState blockState = entityWorld.getBlockState(blockPos);

                    if (blockState != null && ((amplifier == 0 && blockState.isIn(ModTags.BLOCK_ORES))
                            || blockState.isIn(ModTags.BLOCK_RARE_ORES))) {
                        // if (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)) <= radius) {
                        float minVolume = 0.3f;
                        float maxVolume = 1.5f;
                        float minPitch = 1.5f;
                        float maxPitch = 2f;
                        entityWorld.playSound(null, blockPos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME,
                                SoundCategory.BLOCKS, minVolume + r.nextFloat() * (maxVolume - minVolume),
                                minPitch + r.nextFloat() * (maxPitch - minPitch));
                        // }
                    }
                }
            }
        }
    }
}
