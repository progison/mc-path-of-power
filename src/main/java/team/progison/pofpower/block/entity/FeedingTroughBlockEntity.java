package team.progison.pofpower.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import team.progison.pofpower.block.FeedingTroughBlock;
import team.progison.pofpower.inventory.BlockEntityInventory;
import team.progison.pofpower.registry.ModBlocks;
import team.progison.pofpower.registry.ModTags;
import team.progison.pofpower.screen.FeedingTroughScreenHandler;

import org.jetbrains.annotations.Nullable;

public class FeedingTroughBlockEntity extends BlockEntity
        implements NamedScreenHandlerFactory, BlockEntityInventory, SidedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public FeedingTroughBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.FEEDING_TROUGH_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, FeedingTroughBlockEntity blockEntity) {
        if (!world.isClient()) {
            int count = blockEntity.getStack(0).getCount();
            int newLevel = 0;
            if (count > 0) {
                newLevel = MathHelper.floor(blockEntity.getStack(0).getCount() / 16.0F) + 1;
            }
            int currentLevel = state.get(FeedingTroughBlock.LEVEL);
            if (currentLevel != newLevel) {
                BlockState blockState = state.with(FeedingTroughBlock.LEVEL, newLevel);
                world.setBlockState(pos, blockState, 3);
            }
        }
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FeedingTroughScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        Inventories.readNbt(tag, this.inventory);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        Inventories.writeNbt(tag, this.inventory);
        return tag;
    }

    @Override
    public int[] getAvailableSlots(Direction var1) {
        // Just return an array of all slots
        int[] result = new int[getItems().size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }

        return result;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction direction) {
        return stack.isIn(ModTags.ITEM_FEED);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return true;
    }
}
