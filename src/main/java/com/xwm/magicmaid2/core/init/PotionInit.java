package com.xwm.magicmaid2.core.init;

import com.xwm.magicmaid.player.MagicCreatureAttributes;
import com.xwm.magicmaid.player.capability.CapabilityLoader;
import com.xwm.magicmaid.player.capability.CapabilityMagicCreature;
import com.xwm.magicmaid2.common.potion.*;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.UUID;

public class PotionInit
{
    public static UUID CRASY_SPPED_WEAK = UUID.nameUUIDFromBytes((Reference.MODID + "_crasy_speed_weak").getBytes());
    public static UUID CRASY_DAMAGE_WEAK = UUID.nameUUIDFromBytes((Reference.MODID + "_crasy_damage_weak").getBytes());

    public static UUID DESIRE_ENERGY_BOOST = UUID.nameUUIDFromBytes((Reference.MODID + "_desire_energy_boost").getBytes());



    public static final Potion POTION_CRASY = new PotionCrazy()
            .setRegistryName(new ResourceLocation(Reference.MODID,"crazy"))
            .setPotionName("crazy")
            .registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, CRASY_SPPED_WEAK.toString(), -0.20000000596046448D, 2)
            .registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, CRASY_DAMAGE_WEAK.toString(), -0.15, 2);

    public static final Potion POTION_DESIRE = new PotionDesire()
            .setRegistryName(new ResourceLocation(Reference.MODID, "desire"))
            .setPotionName("desire")
            .registerPotionAttributeModifier(MagicCreatureAttributes.PER_ENERGY, DESIRE_ENERGY_BOOST.toString(), 1, 1);

    public static final Potion POTION_DISTORT = new PotionDistort()
            .setRegistryName(new ResourceLocation(Reference.MODID, "distort"))
            .setPotionName("distort");

    public static final Potion POTION_HEAVEN = new PotionHeaven()
            .setRegistryName(new ResourceLocation(Reference.MODID, "heaven"))
            .setPotionName("heaven");

    public static final Potion POTION_EVIL = new PotionEvil()
            .setRegistryName(new ResourceLocation(Reference.MODID, "evil"))
            .setPotionName("evil");
    public static final PotionType POTION_EVIL_TYPE = new PotionType("evil", new PotionEffect[]{new PotionEffect(POTION_EVIL, 4800, 0)}).setRegistryName("evil");
    public static final PotionType LONG_POTION_EVIL_TYPE = new PotionType("evil", new PotionEffect[]{new PotionEffect(POTION_EVIL, 4800 * 5, 1)}).setRegistryName("long_evil");


    public static void registerPotions()
    {
        registerPotionWithoutBottle(POTION_CRASY);
        registerPotionWithoutBottle(POTION_DISTORT);
        registerPotionWithoutBottle(POTION_HEAVEN);
        registerPotionWithoutBottle(POTION_DESIRE);

        registerPotion(POTION_EVIL_TYPE, LONG_POTION_EVIL_TYPE, POTION_EVIL);

        registerPotionMixes();
    }

    private static void registerPotionWithoutBottle(Potion effect){
        ForgeRegistries.POTIONS.register(effect);
    }

    private static void registerPotion(PotionType defaultPotion, PotionType longPotion, Potion effect)
    {
        ForgeRegistries.POTIONS.register(effect);
        ForgeRegistries.POTION_TYPES.register(defaultPotion);
        ForgeRegistries.POTION_TYPES.register(longPotion);
    }

    private static void registerPotionMixes()
    {
//        PotionHelper.addMix(PotionTypes.AWKWARD, com.xwm.magicmaid.init.ItemInit.ITEM_EVIL, POTION_EVIL_TYPE);
//        PotionHelper.addMix(POTION_EVIL_TYPE, Items.REDSTONE, LONG_POTION_EVIL_TYPE);
    }
}
