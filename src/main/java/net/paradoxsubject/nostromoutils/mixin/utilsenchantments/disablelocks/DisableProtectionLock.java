package net.paradoxsubject.nostromoutils.mixin.utilsenchantments.disablelocks;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.ProtectionEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProtectionEnchantment.class)
public class DisableProtectionLock {

    /**
     * Disables the lock when merging two different protection enchantments, giving you the ability to obtain "God Armor"
     * from 1.14.2
     * @see net.minecraft.enchantment.ProtectionEnchantment#canAccept(Enchantment)
     * @param other Enchantment
     */
    @Inject(method = "canAccept", at = @At(value = "RETURN"), cancellable = true)
    public void canAccept(Enchantment other, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(true);

    }
}
