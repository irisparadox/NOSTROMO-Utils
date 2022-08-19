package net.paradoxsubject.nostromoutils.utils;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import static net.minecraft.state.property.Properties.CAN_SUMMON;

public class ShriekerEnable implements UseBlockCallback {

    public final SoundEvent END_FILL = SoundEvents.BLOCK_END_PORTAL_FRAME_FILL;
    public final SoundEvent CATALYST_BLOOM = SoundEvents.BLOCK_SCULK_CATALYST_BLOOM;

    /**
     * Has a probability of 8.33% success that the Shrieker will be enabled after using an Echo Shard item on the block.
     * @see net.minecraft.state.property.Properties#CAN_SUMMON
     * @param player Player's entity
     * @param world World
     * @param hand Player's hand
     * @param hitResult Result after hit
     */
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (block == Blocks.SCULK_SHRIEKER) {
            ItemStack itemHand = player.getStackInHand(hand);

            if (player.isHolding(Items.ECHO_SHARD) && !state.get(CAN_SUMMON)) {

                if (player instanceof ServerPlayerEntity serverPlayer) {
                    ServerWorld serverWorld = (ServerWorld) world;

                    if (serverWorld.random.nextBetween(1, 12) == 5) {
                        Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, pos, itemHand);
                        serverWorld.setBlockState(pos, state.with(CAN_SUMMON, true), Block.NOTIFY_ALL);
                        serverWorld.playSound(null, pos, END_FILL, SoundCategory.BLOCKS, 1f, 1f);
                        serverWorld.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, state));
                    }
                    serverPlayer.increaseStat(Stats.USED.getOrCreateStat(Items.ECHO_SHARD), 1);

                    serverWorld.spawnParticles(ParticleTypes.SCULK_SOUL,
                                            pos.getX() + 0.5,
                                            pos.getY() + 1.15,
                                            pos.getZ() + 0.5,
                                         2,
                                         0.2,
                                         0.0,
                                         0.2,
                                         0.0);
                    float r_catalystPitch = 0.6f + serverWorld.random.nextFloat() * 0.4f;
                    serverWorld.playSound(null, pos, CATALYST_BLOOM, SoundCategory.BLOCKS, 2.0f, r_catalystPitch);
                }

                if (!player.getAbilities().creativeMode) {
                    itemHand.decrement(1);
                }

                world.syncWorldEvent(player, 0, pos, 0);

                return ActionResult.success(world.isClient);
            } else {
                return ActionResult.PASS;
            }
        } else {
            return ActionResult.PASS;
        }
    }
}
