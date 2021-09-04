package team.progison.pofpower.mixin;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ai.brain.task.BreedTask;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.world.GameRules;
import team.progison.pofpower.PathOfPowerMod;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BreedTask.class)
public class BreedTaskMixin {
    @Redirect(method = "keepRunning(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/AnimalEntity;J)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AnimalEntity;breed(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/AnimalEntity;)V"))
    private void feedingTroughBreed(AnimalEntity animal, ServerWorld world, AnimalEntity other) {
        PathOfPowerMod.LOGGER.info("FEEDING TROUGH BREED");
        PassiveEntity passiveEntity = animal.createChild(world, other);
        if (passiveEntity != null) {
            ServerPlayerEntity serverPlayerEntity = animal.getLovingPlayer();
            if (serverPlayerEntity == null && other.getLovingPlayer() != null) {
                serverPlayerEntity = other.getLovingPlayer();
            }

            if (serverPlayerEntity != null) {
                serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
                Criteria.BRED_ANIMALS.trigger(serverPlayerEntity, animal, other, passiveEntity);
            }

            animal.setBreedingAge(6000);
            other.setBreedingAge(6000);
            animal.resetLoveTicks();
            other.resetLoveTicks();
            passiveEntity.setBaby(true);
            passiveEntity.refreshPositionAndAngles(animal.getX(), animal.getY(), animal.getZ(), 0.0F, 0.0F);
            world.spawnEntityAndPassengers(passiveEntity);
            world.sendEntityStatus(animal, (byte) 18);
            if ((animal.getLovingPlayer() != null || other.getLovingPlayer() != null)
                    && world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                world.spawnEntity(new ExperienceOrbEntity(world, animal.getX(), animal.getY(), animal.getZ(),
                        animal.getRandom().nextInt(7) + 1));
            }
        }
    }

    @Inject(method = "keepRunning(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/AnimalEntity;J)V", at = @At("HEAD"))
    private void breedInjected(CallbackInfo ci) {
        PathOfPowerMod.LOGGER.info("FEEDING TROUGH BREED keepRunning");
    }
}
