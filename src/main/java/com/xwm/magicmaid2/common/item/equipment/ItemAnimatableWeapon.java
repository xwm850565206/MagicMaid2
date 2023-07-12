package com.xwm.magicmaid2.common.item.equipment;

import com.xwm.magicmaid.object.item.equipment.ItemWeapon;
import com.xwm.magicmaid2.client.render.item.ItemAnimatableWeaponRenderer;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.List;

public class ItemAnimatableWeapon extends ItemWeapon implements IAnimatable
{
    public AnimationFactory factory = new AnimationFactory(this);

    public ItemAnimatableWeapon(String name) {
        super(name);
    }

    protected  <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event)
    {
        //Not setting an animation here as that's handled in onItemRightClick
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 1, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public boolean isChargeable() {
        return false;
    }

    @Override
    public void onUse(World world, EntityLivingBase entityLivingBase, EnumHand enumHand, @Nullable List<EntityLivingBase> list) {

    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
//        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels()
    {
        super.registerModels();
        this.setTileEntityItemStackRenderer(new ItemAnimatableWeaponRenderer());
    }

    @Override
    public void doRegister() {
        ItemInit.ITEMS.add(this);
    }
}
