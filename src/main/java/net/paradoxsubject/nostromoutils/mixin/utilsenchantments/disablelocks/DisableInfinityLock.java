package net.paradoxsubject.nostromoutils.mixin.utilsenchantments.disablelocks;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.InfinityEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InfinityEnchantment.class)
public class DisableInfinityLock {

    /**
     * Disables the lock when merging mending and infinity enchantments.
     * @see net.minecraft.enchantment.InfinityEnchantment#canAccept(Enchantment)
     * @param other Enchantment
     */
    @Inject(method = "canAccept", at = @At(value = "RETURN"), cancellable = true)
    public void canAccept(Enchantment other, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(true);

    }
}
