package net.paradoxsubject.nostromoutils.mixin.utilsenchantments.anvillock;

import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.paradoxsubject.nostromoutils.mixinproperties.enchantments.anvil.AnvilLockValues;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AnvilScreen.class)
public class DisableTooExpensive {

    /**
     * Removes the "Too Expensive" lock in the anvil gui.
     * @see net.minecraft.client.gui.screen.ingame.AnvilScreen
     */
    @ModifyConstant(method = "drawForeground", constant = @Constant(intValue = 40, ordinal = 0))
    public int drawForeground(int constant){
        return AnvilLockValues.getLevelLimit();

    }
}
