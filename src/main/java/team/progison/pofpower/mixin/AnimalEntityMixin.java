package team.progison.pofpower.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.world.GameRules;
import team.progison.pofpower.entity.ai.goal.SelfFeedGoal;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.stream.Collectors;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin extends PassiveEntity {
    private static final ItemStack[] FORBIDDEN_ITEMS = Arrays
            .stream(new Item[] { Items.CARROT_ON_A_STICK, Items.WARPED_FUNGUS_ON_A_STICK }).map(ItemStack::new)
            .toArray(ItemStack[]::new);

    protected AnimalEntityMixin(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void AFTAddSelfFeedingGoal(EntityType<? extends MobEntity> entityType, World world, CallbackInfo ci) {
        if (world != null && !world.isClient) {
            ((GoalSelectorAccessor) this.goalSelector).getGoals().stream()
                    .filter(prioritizedGoal -> prioritizedGoal.getGoal().getClass().equals(TemptGoal.class))
                    .collect(Collectors.toList()).forEach(prioritizedGoal -> {
                        TemptGoal goal = (TemptGoal) prioritizedGoal.getGoal();
                        PathAwareEntity mob = ((TemptGoalAccessor) goal).getMob();
                        double speed = ((TemptGoalAccessor) goal).getSpeed();
                        Ingredient food = (((TemptGoalAccessor) goal).getFood());
                        boolean hasForbiddenFood = false;
                        for (ItemStack itemStack : FORBIDDEN_ITEMS) {
                            if (food.test(itemStack)) {
                                hasForbiddenFood = true;
                                break;
                            }
                        }
                        if (!hasForbiddenFood) {
                            this.goalSelector.add(prioritizedGoal.getPriority() + 1,
                                    new SelfFeedGoal((AnimalEntity) mob, speed, food));
                        }
                    });
        }
    }

    public void breed(ServerWorld world, AnimalEntity other) {
        PassiveEntity passiveEntity = ((AnimalEntity) (Object) this).createChild(world, other);
        if (passiveEntity != null) {
            ServerPlayerEntity serverPlayerEntity = ((AnimalEntity) (Object) this).getLovingPlayer();
            if (serverPlayerEntity == null && other.getLovingPlayer() != null) {
                serverPlayerEntity = other.getLovingPlayer();
            }

            if (serverPlayerEntity != null) {
                serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
                Criteria.BRED_ANIMALS.trigger(serverPlayerEntity, ((AnimalEntity) (Object) this), other, passiveEntity);
            }

            ((AnimalEntity) (Object) this).setBreedingAge(6000);
            other.setBreedingAge(6000);
            ((AnimalEntity) (Object) this).resetLoveTicks();
            other.resetLoveTicks();
            passiveEntity.setBaby(true);
            passiveEntity.refreshPositionAndAngles(((AnimalEntity) (Object) this).getX(),
                    ((AnimalEntity) (Object) this).getY(), ((AnimalEntity) (Object) this).getZ(), 0.0F, 0.0F);
            world.spawnEntityAndPassengers(passiveEntity);
            world.sendEntityStatus(((AnimalEntity) (Object) this), (byte) 18);
            if (world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && serverPlayerEntity != null) {
                world.spawnEntity(new ExperienceOrbEntity(world, ((AnimalEntity) (Object) this).getX(),
                        ((AnimalEntity) (Object) this).getY(), ((AnimalEntity) (Object) this).getZ(),
                        ((AnimalEntity) (Object) this).getRandom().nextInt(7) + 1));
            }
        }
    }
}
