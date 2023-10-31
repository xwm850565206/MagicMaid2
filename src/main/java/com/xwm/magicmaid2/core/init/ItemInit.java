package com.xwm.magicmaid2.core.init;

import com.xwm.magicmaid.object.item.ItemBase;
import com.xwm.magicmaid.object.item.equipment.ItemArmor;
import com.xwm.magicmaid2.common.item.*;
import com.xwm.magicmaid2.common.item.equipment.*;
import com.xwm.magicmaid2.common.item.piece.ItemPiece;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;

import java.util.ArrayList;
import java.util.List;

public class ItemInit
{
    public static final List<Item> ITEMS = new ArrayList<Item>();

    // equipment
    public static ItemAnimatableWeapon DARK_CLAW = new ItemDarkClaw("dark_claw");
    public static ItemAnimatableWeapon DARK_SOUL = new ItemDarkSoul("dark_soul");
    public static ItemArmor BANSHEE_VEIL = new ItemBansheeVeil("banshee_veil");
    public static ItemAnimatableWeapon UNREAL_RING = new ItemUnrealRing("unreal_ring");
    public static ItemAnimatableWeapon UNREAL_BALL = new ItemUnrealBall("unreal_ball");
    public static ItemArmor FOX_ARMOR = new ItemFoxArmor("fox_armor");
    public static ItemAnimatableWeapon STAR_ARROW = new ItemStarArrow("star_arrow");
    public static ItemAnimatableWeapon HEAVEN_ARROW = new ItemHeavenArrow("heaven_arrow");
    public static ItemArmor STAR_BOW = new ItemStarBow("star_bow");
    public static ItemAnimatableWeapon EAGER = new ItemEager("eager");
    public static ItemArmor XIMO_CORE = new ItemSuccubusCore("ximo_core");

    public static ItemAnimatableWeapon HEAVY_COLD = new ItemIceWing("heavy_cold");
    public static ItemArmor NEVA_CORE = new ItemAngerCore("neva_core");

    public static ItemBase EVIL_GHOST_OBSESSION_SWORD = new ItemEvilGhostObsessionSword("evil_ghost_obsession_sword");
    public static ItemBase SCYTHE = new ItemBase("scythe");
    public static Item EVIL_HELMET = new ItemEvilArmor.Helmet(ItemEvilArmor.EVIL_ARMOR, "evil_helmet");
    public static Item EVIL_CHESTPLATE = new ItemEvilArmor.Chestplate(ItemEvilArmor.EVIL_ARMOR, "evil_chestplate");
    public static Item EVIL_LEGGINGS = new ItemEvilArmor.Leggings(ItemEvilArmor.EVIL_ARMOR, "evil_leggings");
    public static Item EVIL_BOOTS = new ItemEvilArmor.Boots(ItemEvilArmor.EVIL_ARMOR, "evil_boots");
    public static ItemTool EVIL_PICKAXE = new ItemEvilPickaxe("evil_pickaxe");

    // memory
    public static Item MEMORY_AILI = new ItemMemoryAili("memory_aili");
    public static Item MEMORY_CASSIU = new ItemMemoryCassiu("memory_cassiu");
    public static Item MEMORY_CARLIE = new ItemMemoryCarlie("memory_carlie");
    public static Item MEMORY_XIMO = new ItemMemoryXimo("memory_ximo");
    public static Item MEMORY_NEVA = new ItemMemoryNeva("memory_neva");
    public static Item MEMORY_CORPSE = new ItemMemoryCorpse("memory_corpse");

    // fruit
    public static Item FRUIT_AILI = new ItemHolyFruitAili("fruit_aili");
    public static Item FRUIT_CASSIU = new ItemHolyFruitCassiu("fruit_cassiu");
    public static Item FRUIT_CARLIE = new ItemHolyFruitCarlie("fruit_carlie");
    public static Item FRUIT_XIMO = new ItemHolyFruitXimo("fruit_ximo");
    public static Item FRUIT_NEVA = new ItemHolyFruitNeva("fruit_neva");

    // piece
    public static Item DARK_CLAW_PIECE = new ItemPiece("dark_claw_piece");
    public static Item DARK_SOUL_PIECE = new ItemPiece("dark_soul_piece");
    public static Item BANSHEE_VEIL_PIECE = new ItemPiece("banshee_veil_piece");
    public static Item UNREAL_BALL_PIECE = new ItemPiece("unreal_ball_piece");
    public static Item UNREAL_RING_PIECE = new ItemPiece("unreal_ring_piece");
    public static Item FOX_ARMOR_PIECE = new ItemPiece("fox_armor_piece");
    public static Item STAR_ARROW_PIECE = new ItemPiece("star_arrow_piece");
    public static Item HEAVEN_ARROW_PIECE = new ItemPiece("heaven_arrow_piece");
    public static Item STAR_BOW_PIECE = new ItemPiece("star_bow_piece");
    public static Item EAGER_PIECE = new ItemPiece("eager_piece");
    public static Item XIMO_CORE_PIECE = new ItemPiece("ximo_core_piece");
    public static Item HEAVY_COLD_PIECE = new ItemPiece("heavy_cold_piece");
    public static Item NEVA_CORE_PIECE = new ItemPiece("neva_core_piece");

    public static Item MEMORY_AILI_PIECE = new ItemPiece("memory_aili_piece");
    public static Item MEMORY_CASSIU_PIECE = new ItemPiece("memory_cassiu_piece");
    public static Item MEMORY_CARLIE_PIECE = new ItemPiece("memory_carlie_piece");
    public static Item MEMORY_XIMO_PIECE = new ItemPiece("memory_ximo_piece");
    public static Item MEMORY_NEVA_PIECE = new ItemPiece("memory_neva_piece");

    public static Item EVIL_INGOT = new ItemEvilIngot("evil_ingot");

    public static ItemExpBall EXP_BALL = new ItemExpBall("exp_ball");
    public static ItemDedicationDead DEDICATION_DEAD_ITEM = new ItemDedicationDead("dedication_dead_item");

    public static ItemSageBone SAGE_BONE = new ItemSageBone("sage_bone");
    public static ItemSageSoup SAGE_SOUP = new ItemSageSoup("sage_soup");
    public static ItemDesireBone DESIRE_BONE = new ItemDesireBone("desire_bone");
    public static ItemBase HOLY_CROSS = new ItemBase("holy_cross_maid2");

    public static ItemBase HOLY_RHINOCEROS = new ItemBase("rhinoceros");

    public static ItemBase ARROGANT = new ItemArrogant("arrogant");

    // symbol
    public static Item SYMBOL_WHITE = new ItemSymbol("symbol_white");
    public static Item SYMBOL_BLACK = new ItemSymbol("symbol_black");
    public static Item SYMBOL_SPRING = new ItemSymbol("symbol_spring");
    public static Item SYMBOL_SUMMER = new ItemSymbol("symbol_summer");
    public static Item SYMBOL_AUT = new ItemSymbol("symbol_aut");
    public static Item SYMBOL_WINTER = new ItemSymbol("symbol_winter");
    public static Item SYMBOL_GOLD = new ItemSymbol("symbol_gold");
    public static Item SYMBOL_GREEN = new ItemSymbol("symbol_green");
    public static Item SYMBOL_BLUE = new ItemSymbol("symbol_blue");
    public static Item SYMBOL_RED = new ItemSymbol("symbol_red");
    public static Item SYMBOL_EARTH = new ItemSymbol("symbol_earth");
    public static Item SYMBOL_0 = new ItemSymbol("symbol_0");
    public static Item SYMBOL_1 = new ItemSymbol("symbol_1");
    public static Item SYMBOL_2 = new ItemSymbol("symbol_2");
    public static Item SYMBOL_3 = new ItemSymbol("symbol_3");
    public static Item SYMBOL_4 = new ItemSymbol("symbol_4");
    public static Item SYMBOL_5 = new ItemSymbol("symbol_5");
    public static Item SYMBOL_6 = new ItemSymbol("symbol_6");
    public static Item SYMBOL_7 = new ItemSymbol("symbol_7");

}
