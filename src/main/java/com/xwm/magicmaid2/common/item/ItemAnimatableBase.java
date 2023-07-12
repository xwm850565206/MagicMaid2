package com.xwm.magicmaid2.common.item;

import com.xwm.magicmaid.object.item.ItemBase;
import com.xwm.magicmaid2.core.init.ItemInit;
import net.minecraft.item.Item;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ItemAnimatableBase extends ItemBase implements IAnimatable
{
    public AnimationFactory factory = new AnimationFactory(this);

    public ItemAnimatableBase(String name) {
        super(name);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event)
    {
        //Not setting an animation here as that's handled in onItemRightClick
        return PlayState.CONTINUE;
    }

    @Override
    public void doRegister() {
        ItemInit.ITEMS.add(this);
    }
}
