package com.xwm.magicmaid2.core.init;

import com.xwm.magicmaid2.common.entity.EntitySoul;
import com.xwm.magicmaid2.common.entity.maid.*;
import com.xwm.magicmaid2.common.entity.mob.*;
import com.xwm.magicmaid2.common.entity.throwable.EntityEvilDeathBall;
import com.xwm.magicmaid2.common.entity.throwable.EntityEvilSwordPower;
import com.xwm.magicmaid2.common.entity.throwable.EntityHeavenArrow;
import com.xwm.magicmaid2.common.entity.throwable.EntityStarArrow;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

public class EntityInit
{
    public static int ID = 0;

    public static void registerEntities(RegistryEvent.Register<EntityEntry> event)
    {
        registerEntityWithoutEgg(event, Reference.MODID + "_aili", EntityMagicMaidAili.class, ID++, 50);
        registerEntityWithoutEgg(event, Reference.MODID + "_cassiu", EntityMagicMaidCassiu.class, ID++, 50);
        registerEntityWithoutEgg(event, Reference.MODID + "_carlie", EntityMagicMaidCarlie.class, ID++, 50);
        registerEntityWithoutEgg(event, Reference.MODID + "_aili_boss", EntityMagicMaidAiliBoss.class, ID++, 50);
        registerEntityWithoutEgg(event, Reference.MODID + "_cassiu_boss", EntityMagicMaidCassiuBoss.class, ID++, 50);
        registerEntityWithoutEgg(event, Reference.MODID + "_carlie_boss", EntityMagicMaidCarlieBoss.class, ID++, 50);

        registerEntityWithoutEgg(event, Reference.MODID + "_ximo", EntityMagicMaidXimo.class, ID++, 50);
        registerEntityWithoutEgg(event, Reference.MODID + "_ximo_boss", EntityMagicMaidXimoBoss.class, ID++, 50);

        registerEntity(event, Reference.MODID + "_maier", EntityMagicMaidMaier.class, ID++, 50, 0x000000, 0xffffff);
        registerEntity(event, Reference.MODID + "_lost_heart", EntityLostHeart.class, ID++, 50, 0x000000, 0x4e3d25);
        registerEntity(event, Reference.MODID + "_lost_evil", EntityLostEvil.class, ID++, 50, 0x000000, 0xffffff);
        registerEntity(event, Reference.MODID + "_evil_skeleton", EntityEvilSkeleton.class, ID++, 50, 0x000000, 0xffffff);
        registerEntity(event, Reference.MODID + "_evil_skeleton_master", EntityEvilSkeletonMaster.class, ID++, 50, 0x000000, 0xffffff);

        registerEntity(event, Reference.MODID + "_routu", EntityRoutu.class, ID++, 50, 0x858585, 0xc9c9c9);


        registerEntityWithoutEgg(event, Reference.MODID + "_soul", EntitySoul.class, ID++, 50);
        registerEntityWithoutEgg(event, Reference.MODID + "_evildeathball", EntityEvilDeathBall.class, ID++, 50);
        registerEntityWithoutEgg(event, Reference.MODID + "_startarrow", EntityStarArrow.class, ID++, 50);
        registerEntityWithoutEgg(event, Reference.MODID + "_heavenarrow", EntityHeavenArrow.class, ID++, 50);
        registerEntityWithoutEgg(event, Reference.MODID + "_evilswordpower", EntityEvilSwordPower.class, ID++, 50);

//        registerEntityWithoutEgg(event, Reference.MODID + "_ring", EntityRing.class, ID++, 50);
    }

    private static void registerEntity(RegistryEvent.Register<EntityEntry> event, String name, Class<? extends Entity> entity, int id, int range, int color1, int color2) {
        event.getRegistry().register(
                EntityEntryBuilder.create()
                        .entity(entity)
                        .name(name)
                        .id(new ResourceLocation(Reference.MODID + ":" + name), id)
                        .tracker(range, 1, true)
                        .egg(color1, color2)
                        .build()
        );
    }

    private static void registerEntityWithoutEgg(RegistryEvent.Register<EntityEntry> event, String name, Class<? extends Entity> entity, int id, int range)
    {
        event.getRegistry().register(
                EntityEntryBuilder.create()
                        .entity(entity)
                        .name(name)
                        .id(new ResourceLocation(Reference.MODID + ":" + name), id)
                        .tracker(range, 1, true)
                        .build()
        );
    }
}
