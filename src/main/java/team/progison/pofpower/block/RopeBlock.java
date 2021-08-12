package team.progison.pofpower.block;

import team.progison.pofpower.registry.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Random;

/** @noinspection deprecation */
public class RopeBlock extends Block {
    public static final VoxelShape shape = Block.createCuboidShape(6, 0, 6, 10, 16, 10);

    public RopeBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState up = world.getBlockState(pos.offset(Direction.UP));
        if (up.isOf(this) || up.isSideSolidFullSquare(world, pos.offset(Direction.UP), Direction.DOWN))
            return true;

        return false;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shape;
    }

    @Override
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return shape;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.isClient()) {
            world.getBlockTickScheduler().schedule(pos, this, 1);
        }
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return false;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockPos upos = pos.offset(Direction.UP);
        BlockState up = world.getBlockState(upos);

        if (!up.isOf(this) && !up.isSideSolidFullSquare(world, upos, Direction.DOWN)) {
            world.breakBlock(pos, true);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
            WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!world.isClient()) {
            world.getBlockTickScheduler().schedule(pos, this, 1);
        }
        return state;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
            BlockHitResult hit) {
        if (!state.isOf(this))
            return ActionResult.PASS;
        if (!player.getMainHandStack().isOf(Item.fromBlock(ModBlocks.ROPE_BLOCK)))
            return ActionResult.PASS;
        BlockPos down = pos.offset(Direction.DOWN);
        BlockState statedown = world.getBlockState(down);
        if (statedown.isOf(ModBlocks.ROPE_BLOCK)) {
            return statedown.getBlock().onUse(statedown, world, down, player, hand, hit);
        } else if (statedown.isAir() && !world.isOutOfHeightLimit(down.getY())) {
            world.setBlockState(down, state);
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_WOOL_PLACE,
                    SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!player.isCreative())
                player.getMainHandStack().decrement(1);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

}
