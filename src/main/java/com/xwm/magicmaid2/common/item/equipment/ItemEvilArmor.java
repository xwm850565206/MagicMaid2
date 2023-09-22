package com.xwm.magicmaid2.common.item.equipment;

import com.google.common.collect.Multimap;
import com.xwm.magicmaid.creativetab.CreativeTabMaid;
import com.xwm.magicmaid.manager.MagicEquipmentUtils;
import com.xwm.magicmaid.player.MagicCreatureAttributes;
import com.xwm.magicmaid.util.interfaces.IHasModel;
import com.xwm.magicmaid.util.interfaces.IRegistrable;
import com.xwm.magicmaid2.Main;
import com.xwm.magicmaid2.core.init.ItemInit;
import com.xwm.magicmaid2.core.init.PotionInit;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ItemEvilArmor extends ItemArmor implements IHasModel, IRegistrable
{
    public static final ItemArmor.ArmorMaterial EVIL_ARMOR = EnumHelper.addArmorMaterial(
            "EVIL", Reference.MODID + ":" + "evil",15, new int[]{4, 7, 9, 4},
            9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F).setRepairItem(new ItemStack(com.xwm.magicmaid.init.ItemInit.ITEM_EVIL));

    private static final UUID[] ARMOR_MODIFIERS = new UUID[] {UUID.nameUUIDFromBytes("evil_speedup".getBytes()), UUID.nameUUIDFromBytes("evil_ignore_reduction".getBytes())};


    public ItemEvilArmor(ArmorMaterial materialIn, EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, materialIn.ordinal(), equipmentSlotIn);
        this.setCreativeTab(CreativeTabMaid.CREATIVE_TAB_MAID);
        doRegister();
    }


    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public void doRegister() {
        ItemInit.ITEMS.add(this);
    }

    public static class Helmet extends ItemEvilArmor
    {
        public Helmet(ArmorMaterial material, String name)
        {
            super(material, EntityEquipmentSlot.HEAD);
            this.setUnlocalizedName(name);
            this.setRegistryName(name);
        }

        @Override
        public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
            super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
            if (itemSlot == 3 && entityIn instanceof EntityLivingBase) {
                ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(PotionInit.POTION_EVIL, 100, 0));
            }
        }

        @Override
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, tooltip, flagIn);
            if (stack.getItem() == ItemInit.EVIL_HELMET) {
                tooltip.add(TextFormatting.LIGHT_PURPLE + "邪恶锭制作而成的头盔，戴着可以持续获得邪恶buff");
            }
        }
    }

    public static class Chestplate extends ItemEvilArmor
    {
        public Chestplate(ArmorMaterial material, String name)
        {
            super(material, EntityEquipmentSlot.CHEST);
            this.setUnlocalizedName(name);
            this.setRegistryName(name);
        }

        @Override
        public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
            super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
            if (itemSlot == 2 && stack.getItem() == ItemInit.EVIL_CHESTPLATE) {
                if (entityIn instanceof EntityLivingBase) {
                    List<EntityMob> entityLivingBases = worldIn.getEntitiesWithinAABB(EntityMob.class, entityIn.getEntityBoundingBox().grow(2));
                    for (EntityLivingBase entityLivingBase : entityLivingBases) {
                        try {
                            if (MagicEquipmentUtils.checkEnemy(entityLivingBase, (EntityLivingBase) entityIn)) {
                                entityLivingBase.addPotionEffect(new PotionEffect(MobEffects.WITHER, 100, 1));
                            }
                        } catch (Exception e) {
                            ;
                        }
                    }
                }
            }
        }

        @Override
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, tooltip, flagIn);
            tooltip.add(TextFormatting.LIGHT_PURPLE + "邪恶锭制作而成的胸甲，戴着可以持续凋零附近生物");
        }
    }

    public static class Leggings extends ItemEvilArmor
    {
        public Leggings(ArmorMaterial material, String name)
        {
            super(material, EntityEquipmentSlot.LEGS);
            this.setUnlocalizedName(name);
            this.setRegistryName(name);
        }

        @Override
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, tooltip, flagIn);
            tooltip.add(TextFormatting.LIGHT_PURPLE + "邪恶锭制作而成的护膝，戴着可以免疫一次正常死亡");
        }
    }

    public static class Boots extends ItemEvilArmor
    {
        public Boots(ArmorMaterial material, String name)
        {
            super(material, EntityEquipmentSlot.FEET);
            this.setUnlocalizedName(name);
            this.setRegistryName(name);
        }

        @Override
        public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
            Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

            if (equipmentSlot == this.armorType)
            {
                multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(ARMOR_MODIFIERS[0], "evil_speedup", 0.05f, 0));
                multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ARMOR_MODIFIERS[1], "evil_ignore_reduction", 5f, 0));
            }

            return multimap;
        }

        @Override
        public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, tooltip, flagIn);
            tooltip.add(TextFormatting.LIGHT_PURPLE + "邪恶锭制作而成的靴子,戴着可以提高移动速度和攻击力");
        }
    }

}
