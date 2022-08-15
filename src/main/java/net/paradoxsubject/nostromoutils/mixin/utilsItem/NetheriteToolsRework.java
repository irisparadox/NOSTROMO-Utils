package net.paradoxsubject.nostromoutils.mixin.utilsItem;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import net.paradoxsubject.nostromoutils.EMath;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public class NetheriteToolsRework {
    /**
     * Modifies a value that is fetched from <code>PlayerEntity.getBlockBreakingSpeed()</code> in the <code>EnchantmentHelper.getEfficiency()</code>
     * method, when it's called, and returns a clamped value which is 8/5 times the default value when using a Netherite tool.
     * @see net.minecraft.entity.player.PlayerEntity#getBlockBreakingSpeed(BlockState)
     * @see net.minecraft.enchantment.EnchantmentHelper#getEfficiency(LivingEntity)
     *
     * @param entity Player's entity.
     *
     * @return Returns an integer value which is the modified efficiency level.
     * */
    @Redirect(method = "getBlockBreakingSpeed(Lnet/minecraft/block/BlockState;)F",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getEfficiency(Lnet/minecraft/entity/LivingEntity;)I"))
    public int getEfficiency(LivingEntity entity) {
        //We get the item from the main hand and the efficiency level
        Item mainHandItem = entity.getHandItems().iterator().next().getItem();
        int efficiencyLevel = EnchantmentHelper.getEfficiency(entity);

        //We check if the material of the main hand item is Netherite
        if (mainHandItem instanceof ToolItem && ((ToolItem) mainHandItem).getMaterial().equals(ToolMaterials.NETHERITE))
            /*
             * In here I used a function which has a slope of 8/5 so if the input is 5, the output will be 8.
             * This means we can instamine blocks we couldn't, only using Netherite.
             * We also clamp the values to 255, so it doesn't break the game's limits.
             * */
            efficiencyLevel = (int) EMath.clamp((double) efficiencyLevel * 8 / 5, 0, 255);

        return efficiencyLevel;
    }
}
