package com.xwm.magicmaid2.client.render;

import com.xwm.magicmaid2.client.render.tileentity.*;
import com.xwm.magicmaid2.common.block.BlockCandleStick;
import com.xwm.magicmaid2.common.entity.EntitySoul;
import com.xwm.magicmaid2.common.entity.maid.*;
import com.xwm.magicmaid2.common.entity.mob.EntityEvilSkeleton;
import com.xwm.magicmaid2.common.entity.mob.EntityLostEvil;
import com.xwm.magicmaid2.common.entity.mob.EntityLostHeart;
import com.xwm.magicmaid2.common.entity.mob.EntityRoutu;
import com.xwm.magicmaid2.common.entity.throwable.EntityEvilDeathBall;
import com.xwm.magicmaid2.common.entity.throwable.EntityEvilSwordPower;
import com.xwm.magicmaid2.common.entity.throwable.EntityHeavenArrow;
import com.xwm.magicmaid2.common.entity.throwable.EntityStarArrow;
import com.xwm.magicmaid2.common.tileentity.*;
import com.xwm.magicmaid2.core.init.BlockInit;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IRegistryDelegate;
import software.bernie.example.block.tile.FertilizerTileEntity;
import software.bernie.example.client.renderer.tile.FertilizerTileRenderer;

@SideOnly(Side.CLIENT)
public class RenderHandler
{
    public static void registerEntityRenders()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityMagicMaidAili.class, RenderEntityMagicMaidAili::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMagicMaidCassiu.class, RenderEntityMagicMaidCassiu::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMagicMaidCarlie.class, RenderEntityMagicMaidCarlie::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMagicMaidAiliBoss.class, RenderEntityMagicMaidAili::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMagicMaidCassiuBoss.class, RenderEntityMagicMaidCassiu::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMagicMaidCarlieBoss.class, RenderEntityMagicMaidCarlie::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMagicMaidCarlieBoss.class, RenderEntityMagicMaidCarlie::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMagicMaidXimo.class, RenderEntityMagicMaidXimo::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMagicMaidXimoBoss.class, RenderEntityMagicMaidXimo::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMagicMaidMaier.class, RenderEntityMagicMaidMaier::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityLostHeart.class, RenderEntityLostHeart::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityLostEvil.class, RenderEntityLostEvil::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityEvilSkeleton.class, RenderEntityEvilSkeleton::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRoutu.class, RenderEntityRoutu::new);



        RenderingRegistry.registerEntityRenderingHandler(EntitySoul.class, RenderEntitySoul::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityEvilDeathBall.class, RenderEntityEvilBall::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityStarArrow.class, RenderEntityStarArrow::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHeavenArrow.class, RenderEntityHeavenArrow::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityEvilSwordPower.class, RenderEntityEvilSwordPower::new);
//        RenderingRegistry.registerEntityRenderingHandler(EntityRing.class, RenderHoverRing::new);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityForestPortal.class, new TileEntityForestPortalRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCorpse.class, new TileEntityCorpseRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySymbol.class, new TileEntitySymbolRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDedicationDead.class, new TileEntityDedicationDeadRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCandleStick.class, new RenderCandleStick());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEvilSkeleton.class, new TileEntityEvilSkeletonRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHolyCross.class, new TileEntityHolyCrossRenderer());

    }

    public static void registerCustomMeshesAndStates()
    {
        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(BlockInit.DEDICATION_EXP_BLOCK), stack -> new ModelResourceLocation(new ResourceLocation(Reference.MODID, "dedication_exp"), "fluid"));
        ModelLoader.setCustomStateMapper(BlockInit.DEDICATION_EXP_BLOCK, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation(new ResourceLocation(Reference.MODID, "dedication_exp"), "fluid");
            }
        });

        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(BlockInit.DEDICATION_LIFE_BLOCK), stack -> new ModelResourceLocation(new ResourceLocation(Reference.MODID, "dedication_life"), "fluid"));
        ModelLoader.setCustomStateMapper(BlockInit.DEDICATION_LIFE_BLOCK, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation(new ResourceLocation(Reference.MODID, "dedication_life"), "fluid");
            }
        });
    }
}
