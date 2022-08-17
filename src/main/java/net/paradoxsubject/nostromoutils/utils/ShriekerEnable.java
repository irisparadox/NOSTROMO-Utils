package net.paradoxsubject.nostromoutils.utils;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import static net.minecraft.state.property.Properties.CAN_SUMMON;

public class ShriekerEnable implements UseBlockCallback {

        /**
         * Has a probability of 20% success that the Shrieker will be enabled after using an Echo Shard item on the block.
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
                ItemStack itemHand = player.getStackInHand(hand);
                Random random = new Random();
                int prob = random.nextInt(1, 5) + 1;

                if(player.isHolding(Items.ECHO_SHARD)) {
                        if (!state.get(CAN_SUMMON) && block == Blocks.SCULK_SHRIEKER) {
                                if(prob==5) {
                                        world.setBlockState(pos, state.with(CAN_SUMMON, true), Block.NOTIFY_ALL);
                                }

                                if(!player.isCreative()) {
                                        itemHand.decrement(1);
                                }

                                if(!world.isClient){
                                        world.playSound
                                        (
                                                null,
                                                pos,
                                                SoundEvents.BLOCK_END_PORTAL_FRAME_FILL,
                                                SoundCategory.BLOCKS,
                                                1f,
                                                1f
                                        );
                                }
                        }
                }

                return ActionResult.PASS;
        }
}
