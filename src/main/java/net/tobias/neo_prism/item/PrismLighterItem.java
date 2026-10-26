package net.tobias.neo_prism.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;

public class PrismLighterItem extends Item {
    public PrismLighterItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();//$$1
        Level level = pContext.getLevel();//$$2
        BlockPos blockPos = pContext.getClickedPos();//$$3
        BlockState blockState = level.getBlockState(blockPos);//$$4
        if (!CampfireBlock.canLight(blockState) && !CandleBlock.canLight(blockState) && !CandleCakeBlock.canLight(blockState)) {
            BlockPos blockPos2 = blockPos.relative(pContext.getClickedFace());//$$5
            if (BaseFireBlock.canBePlacedAt(level, blockPos2, pContext.getHorizontalDirection())) {
                level.playSound(player, blockPos2, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                BlockState blockState2 = BaseFireBlock.getState(level, blockPos2);//$$6
                level.setBlock(blockPos2, blockState2, 11);
                level.gameEvent(player, GameEvent.BLOCK_PLACE, blockPos);
                ItemStack itemInHand = pContext.getItemInHand();//$$7
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, blockPos2, itemInHand);
                    itemInHand.hurtAndBreak(1, player, (p_41300_) -> p_41300_.broadcastBreakEvent(pContext.getHand()));
                }
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.FAIL;
            }
        } else {
            level.playSound(player, blockPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
            level.setBlock(blockPos, (BlockState) blockState.setValue(BlockStateProperties.LIT, true), 11);
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
            if (player != null) {
                pContext.getItemInHand().hurtAndBreak(1, player, (p_41303_) -> p_41303_.broadcastBreakEvent(pContext.getHand()));
            }
            return InteractionResult.SUCCESS;
        }
    }
}