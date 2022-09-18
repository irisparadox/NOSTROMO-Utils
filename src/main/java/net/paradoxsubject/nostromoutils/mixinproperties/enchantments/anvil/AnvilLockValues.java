package net.paradoxsubject.nostromoutils.mixinproperties.enchantments.anvil;

import net.minecraft.enchantment.Enchantment;

public class AnvilLockValues {

    public static int levelLimit = -1;
    public static int globalEnchantmentLevelLimit = -1;
    public static int getLevelLimit() {
        return levelLimit != -1 ? levelLimit + 1 : Integer.MAX_VALUE;
    }
    public static int getEnchantmentLimit(Enchantment enchantment) {
        return globalEnchantmentLevelLimit >= 0 ? globalEnchantmentLevelLimit : enchantment.getMaxLevel();
    }
}