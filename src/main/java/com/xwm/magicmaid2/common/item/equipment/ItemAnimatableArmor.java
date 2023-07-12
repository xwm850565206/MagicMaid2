package com.xwm.magicmaid2.common.item.equipment;

import com.xwm.magicmaid.object.item.equipment.ItemArmor;
import com.xwm.magicmaid2.client.render.item.ItemAnimatableArmorRenderer;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

public class ItemAnimatableArmor extends ItemArmor implements IAnimatable
{
    public AnimationFactory factory = new AnimationFactory(this);

    public ItemAnimatableArmor(String name) {
        super(name);
    }

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event)
    {
        //Not setting an animation here as that's handled in onItemRightClick
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
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
        this.setTileEntityItemStackRenderer(new ItemAnimatableArmorRenderer());
    }

    @Override
    public void doRegister() {
        ItemInit.ITEMS.add(this);
    }
}
