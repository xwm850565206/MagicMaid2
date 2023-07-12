package com.xwm.magicmaid2.common.entity.mob;

import com.xwm.magicmaid.particle.EnumCustomParticles;
import com.xwm.magicmaid.particle.ParticleSpawner;
import com.xwm.magicmaid2.core.init.LootTableInit;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
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

import javax.annotation.Nullable;

public class EntityLostEvil extends EntityZombie implements IAnimatable
{
    private AnimationFactory factory = new AnimationFactory(this);

    public EntityLostEvil(World worldIn)
    {
        super(worldIn);
        this.ignoreFrustumCheck = true;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
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
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.evil_zombie.attack", true));
        }
        else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.evil_zombie.walk", true));
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

    @SideOnly(Side.CLIENT)
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        for (int i = 0; i < 1; i++) {
            double x = posX + rand.nextFloat() - 0.5;
            double y = posY + rand.nextFloat()*height;
            double z = posZ + rand.nextFloat() - 0.5;
            ParticleSpawner.spawnParticle(EnumCustomParticles.PANDORA, x, y, z, x, y + rand.nextFloat(), z);
        }
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LootTableInit.LOST_HEART;
    }
}
