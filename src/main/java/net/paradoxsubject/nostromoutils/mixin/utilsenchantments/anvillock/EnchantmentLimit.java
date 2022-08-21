package net.paradoxsubject.nostromoutils.mixin.utilsenchantments.anvillock;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.screen.AnvilScreenHandler;
import net.paradoxsubject.nostromoutils.mixinproperties.enchantments.anvil.AnvilLockValues;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilScreenHandler.class)
public class EnchantmentLimit {

    /**
     * Modifies the level limit allowed in Enchantment.
     * @see AnvilScreenHandler#updateResult()
     */
    @ModifyConstant(method = "updateResult", constant = @Constant(intValue = 40, ordinal = 2))
    private int updateResult(int input) {
        return AnvilLockValues.getLevelLimit();
    }

    /**
     * Modifies the maximum level allowed in Enchantment.
     * @see AnvilScreenHandler#updateResult()
     */
    @Redirect(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I"))
    private int getMaximumLevelProxy(Enchantment enchantment) {
        return AnvilLockValues.getEnchantmentLimit(enchantment);
    }
}
