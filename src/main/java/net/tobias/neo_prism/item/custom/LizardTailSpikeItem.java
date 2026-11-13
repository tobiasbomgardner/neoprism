package net.tobias.neo_prism.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.tobias.neo_prism.entity.entities.projectiles.ShardLizardTailSpike;
import net.tobias.neo_prism.item.ModItems;

import java.util.function.Predicate;

public class LizardTailSpikeItem extends Item {
    public LizardTailSpikeItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!level.isClientSide) {
            ShardLizardTailSpike spike = new ShardLizardTailSpike(level, player);
            spike.setVariant(1);
            spike.setBaseDamage(5.0f);
            spike.setOwner(player);
            spike.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.0F, 1.0F);
            level.addFreshEntity(spike);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemInHand.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide);
    }
}