package net.paradoxsubject.nostromoutils.mixin.utilsItem;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public class NetheriteToolsRework {
    /**
     * Redirects from the method to an instance of the class BlockState fetching a float to the method getEfficiency
     * from an instance of EnchantmentHelper, fetching an integer.
     * */
    @Redirect(method = "getBlockBreakingSpeed(Lnet/minecraft/block/BlockState;)F",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getEfficiency(Lnet/minecraft/entity/LivingEntity;)I"))
    public int getEfficiency(LivingEntity entity) {
        Item mainHandItem = entity.getHandItems().iterator().next().getItem();
        int efficiencyLevel = EnchantmentHelper.getEfficiency(entity);

        if (mainHandItem instanceof ToolItem && ((ToolItem) mainHandItem).getMaterial().equals(ToolMaterials.NETHERITE))
            efficiencyLevel = Math.max(efficiencyLevel * 8 / 5, 255);

        return Math.max(efficiencyLevel, 255);
    }
}
