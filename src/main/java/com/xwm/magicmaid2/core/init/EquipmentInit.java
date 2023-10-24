package com.xwm.magicmaid2.core.init;

import com.xwm.magicmaid.object.item.equipment.EquipmentAttribute;
import com.xwm.magicmaid.object.item.equipment.ItemEquipment;
import com.xwm.magicmaid.registry.MagicEquipmentRegistry;
import com.xwm.magicmaid2.common.item.equipment.ItemDarkClaw;
import com.xwm.magicmaid2.common.item.equipment.ItemDarkSoul;
import com.xwm.magicmaid2.common.item.equipment.ItemStarArrow;
import com.xwm.magicmaid2.common.item.equipment.ItemUnrealRing;
import net.minecraft.util.math.Vec3d;

public class EquipmentInit
{
    public static EquipmentAttribute UNREAL_BALL = new EquipmentAttribute()
            .setName("unreal_ball")
            .setEquipment((ItemEquipment)ItemInit.UNREAL_BALL)
            .setPiece(ItemInit.UNREAL_BALL_PIECE)
            .setAttackDamage(null)
            .setBaseArea(new Vec3d(1, 1, 1))
            .setGrowArea(new Vec3d(0.0, 0.0, 0.0))
            .setType(EquipmentAttribute.EquipmentType.WEAPON);

    public static EquipmentAttribute UNREAL_RING = new EquipmentAttribute()
            .setName("unreal_ring")
            .setEquipment((ItemEquipment)ItemInit.UNREAL_RING)
            .setPiece(ItemInit.UNREAL_RING_PIECE)
            .setAttackDamage(ItemInit.UNREAL_RING.getAttackDamage())
            .setBaseArea(new Vec3d(1, 1, 1))
            .setGrowArea(new Vec3d(0.0, 0.0, 0.0))
            .setType(EquipmentAttribute.EquipmentType.WEAPON);

    public static EquipmentAttribute FOX_ARMOR = new EquipmentAttribute()
            .setName("fox_armor")
            .setEquipment((ItemEquipment) ItemInit.FOX_ARMOR)
            .setPiece(ItemInit.FOX_ARMOR_PIECE)
            .setType(EquipmentAttribute.EquipmentType.ARMOR);

    public static EquipmentAttribute DARK_CLAW = new EquipmentAttribute()
            .setName("dark_claw")
            .setEquipment((ItemEquipment)ItemInit.DARK_CLAW)
            .setPiece(ItemInit.DARK_CLAW_PIECE)
            .setAttackDamage(ItemInit.DARK_CLAW.getAttackDamage())
            .setBaseArea(new Vec3d(2, 2, 2))
            .setGrowArea(new Vec3d(1.0, 1.0, 1.0))
            .setType(EquipmentAttribute.EquipmentType.WEAPON);

    public static EquipmentAttribute DARK_SOUL = new EquipmentAttribute()
            .setName("dark_soul")
            .setEquipment((ItemEquipment)ItemInit.DARK_SOUL)
            .setPiece(ItemInit.DARK_SOUL_PIECE)
            .setAttackDamage(ItemInit.DARK_SOUL.getAttackDamage())
            .setBaseArea(new Vec3d(1, 1, 1))
            .setGrowArea(new Vec3d(0.0, 0.0, 0.0))
            .setType(EquipmentAttribute.EquipmentType.WEAPON);

    public static EquipmentAttribute BANSHEE_VEIL = new EquipmentAttribute()
            .setName("banshee_veil")
            .setEquipment((ItemEquipment) ItemInit.BANSHEE_VEIL)
            .setPiece(ItemInit.BANSHEE_VEIL_PIECE)
            .setType(EquipmentAttribute.EquipmentType.ARMOR);

    public static EquipmentAttribute STAR_ARROW = new EquipmentAttribute()
            .setName("star_arrow")
            .setEquipment((ItemEquipment)ItemInit.STAR_ARROW)
            .setPiece(ItemInit.STAR_ARROW_PIECE)
            .setAttackDamage(ItemInit.STAR_ARROW.getAttackDamage())
            .setBaseArea(new Vec3d(1, 1, 1))
            .setGrowArea(new Vec3d(0.0, 0.0, 0.0))
            .setType(EquipmentAttribute.EquipmentType.WEAPON);

    public static EquipmentAttribute HEAVEN_ARROW = new EquipmentAttribute()
            .setName("heaven_arrow")
            .setEquipment((ItemEquipment)ItemInit.HEAVEN_ARROW)
            .setPiece(ItemInit.HEAVEN_ARROW_PIECE)
            .setAttackDamage(ItemInit.HEAVEN_ARROW.getAttackDamage())
            .setBaseArea(new Vec3d(1, 1, 1))
            .setGrowArea(new Vec3d(0.0, 0.0, 0.0))
            .setType(EquipmentAttribute.EquipmentType.WEAPON);

    public static EquipmentAttribute STAR_BOW = new EquipmentAttribute()
            .setName("star_bow")
            .setEquipment((ItemEquipment) ItemInit.STAR_BOW)
            .setPiece(ItemInit.STAR_BOW_PIECE)
            .setType(EquipmentAttribute.EquipmentType.ARMOR);

    public static EquipmentAttribute EAGER = new EquipmentAttribute()
            .setName("eager")
            .setEquipment(ItemInit.EAGER)
            .setPiece(ItemInit.EAGER_PIECE)
            .setAttackDamage(ItemInit.EAGER.getAttackDamage())
            .setBaseArea(new Vec3d(4, 4, 4))
            .setGrowArea(new Vec3d(2, 1, 2))
            .setType(EquipmentAttribute.EquipmentType.WEAPON);

    public static EquipmentAttribute XIMO_CORE = new EquipmentAttribute()
            .setName("ximo_core")
            .setEquipment((ItemEquipment) ItemInit.XIMO_CORE)
            .setPiece(ItemInit.XIMO_CORE_PIECE)
            .setType(EquipmentAttribute.EquipmentType.ARMOR);


    public static EquipmentAttribute HEAVY_COLD = new EquipmentAttribute()
            .setName("heavy_cold")
            .setEquipment(ItemInit.HEAVY_COLD)
            .setPiece(ItemInit.HEAVY_COLD_PIECE)
            .setAttackDamage(ItemInit.HEAVY_COLD.getAttackDamage())
            .setBaseArea(new Vec3d(1, 1, 1))
            .setGrowArea(new Vec3d(0, 0, 0))
            .setType(EquipmentAttribute.EquipmentType.WEAPON);

    public static EquipmentAttribute NEVA_CORE = new EquipmentAttribute()
            .setName("neva_core")
            .setEquipment((ItemEquipment) ItemInit.NEVA_CORE)
            .setPiece(ItemInit.NEVA_CORE_PIECE)
            .setType(EquipmentAttribute.EquipmentType.ARMOR);

    static {
        ItemInit.FOX_ARMOR.setEquipmentAttribute(FOX_ARMOR);
        ItemInit.UNREAL_RING.setEquipmentAttribute(UNREAL_RING);
        ItemInit.UNREAL_BALL.setEquipmentAttribute(UNREAL_BALL);
        ItemInit.BANSHEE_VEIL.setEquipmentAttribute(BANSHEE_VEIL);
        ItemInit.DARK_SOUL.setEquipmentAttribute(DARK_SOUL);
        ItemInit.DARK_CLAW.setEquipmentAttribute(DARK_CLAW);
        ItemInit.STAR_BOW.setEquipmentAttribute(STAR_BOW);
        ItemInit.STAR_ARROW.setEquipmentAttribute(STAR_ARROW);
        ItemInit.HEAVEN_ARROW.setEquipmentAttribute(HEAVEN_ARROW);
        ItemInit.EAGER.setEquipmentAttribute(EAGER);
        ItemInit.XIMO_CORE.setEquipmentAttribute(XIMO_CORE);
        ItemInit.HEAVY_COLD.setEquipmentAttribute(HEAVY_COLD);
        ItemInit.NEVA_CORE.setEquipmentAttribute(NEVA_CORE);
        MagicEquipmentRegistry.updateMap();
    }
}
