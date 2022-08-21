package net.paradoxsubject.nostromoutils.mixin.utilsenchantments.disablelocks;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageEnchantment.class)
public class DisableDamageLock {

    /**
     * Disables the lock when merging damage enchantments, giving the ability to merge Sharpness, Bane of Arthropods and
     * Smite together.
     * @see net.minecraft.enchantment.DamageEnchantment#canAccept(Enchantment)
     * @param other Enchantment
     */
    @Inject(method = "canAccept", at = @At(value = "RETURN"), cancellable = true)
    public void canAccept(Enchantment other, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(true);

    }
}
