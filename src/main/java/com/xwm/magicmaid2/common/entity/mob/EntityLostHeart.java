package com.xwm.magicmaid2.common.entity.mob;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityLostHeart extends EntityZombie implements IAnimatable
{
    private static final DataParameter<Integer> ZOMBIE_TYPE = EntityDataManager.<Integer>createKey(EntityLostHeart.class, DataSerializers.VARINT);

    private AnimationFactory factory = new AnimationFactory(this);

    public EntityLostHeart(World worldIn)
    {
        super(worldIn);
        this.ignoreFrustumCheck = true;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(ZOMBIE_TYPE, rand.nextInt(4));
    }

    public void initEntityAI(){
        super.initEntityAI();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float)Math.PI);
        if (f1 > 0.01) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.zombie.attack", true));
        }
        else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.zombie.walk", true));
        }
        else {
            return PlayState.STOP;
        }

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data)
    {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public void setZombieType(int type)
    {
        this.getDataManager().set(ZOMBIE_TYPE, type);
    }

    @SideOnly(Side.CLIENT)
    public int getZombieType()
    {
        return this.getDataManager().get(ZOMBIE_TYPE);
    }

}
