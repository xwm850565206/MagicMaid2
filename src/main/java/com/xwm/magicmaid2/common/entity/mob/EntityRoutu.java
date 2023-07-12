package com.xwm.magicmaid2.common.entity.mob;

import com.google.common.collect.Lists;
import com.xwm.magicmaid2.core.init.LootTableInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;

public class EntityRoutu extends EntityLivingBase implements IAnimatable
{
    private static final DataParameter<Integer> ROUTU_TYPE = EntityDataManager.<Integer>createKey(EntityRoutu.class, DataSerializers.VARINT);

    private AnimationFactory factory = new AnimationFactory(this);

    public EntityRoutu(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.8F);
        this.ignoreFrustumCheck = true;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(ROUTU_TYPE, rand.nextInt(4));
    }

    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        ResourceLocation resourcelocation = LootTableInit.ROUTU;

        LootTable loottable = this.world.getLootTableManager().getLootTableFromLocation(resourcelocation);
        LootContext.Builder lootcontext$builder = (new LootContext.Builder((WorldServer)this.world)).withLootedEntity(this).withDamageSource(source);

        if (wasRecentlyHit && this.attackingPlayer != null)
        {
            lootcontext$builder = lootcontext$builder.withPlayer(this.attackingPlayer).withLuck(this.attackingPlayer.getLuck());
        }

        for (ItemStack itemstack : loottable.generateLootForPools(this.rand, lootcontext$builder.build()))
        {
            this.entityDropItem(itemstack, 0.0F);
        }

        this.dropEquipment(wasRecentlyHit, lootingModifier);

    }

    public void setRoutuType(int type)
    {
        this.getDataManager().set(ROUTU_TYPE, type);
    }

    @SideOnly(Side.CLIENT)
    public int getRoutuType()
    {
        return this.getDataManager().get(ROUTU_TYPE);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(0.5);
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return Lists.newArrayList();
    }

    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {

    }

    @Override
    public EnumHandSide getPrimaryHand() {
        return EnumHandSide.RIGHT;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

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

}
