package com.xwm.magicmaid2.common.entity.maid;

import com.xwm.magicmaid.entity.ai.EntityAINearestAttackableTargetAvoidOwner;
import com.xwm.magicmaid.entity.mob.basic.interfaces.IEntityBossCreature;
import com.xwm.magicmaid.enumstorage.EnumMode;
import com.xwm.magicmaid.manager.IMagicBossManager;
import com.xwm.magicmaid.network.NetworkLoader;
import com.xwm.magicmaid.network.RenderAreaPacket;
import com.xwm.magicmaid.network.entity.SPacketMaidInventoryUpdate;
import com.xwm.magicmaid.object.item.equipment.EquipmentAttribute;
import com.xwm.magicmaid.object.item.equipment.ItemEquipment;
import com.xwm.magicmaid.registry.MagicEquipmentRegistry;
import com.xwm.magicmaid.registry.MagicRenderRegistry;
import com.xwm.magicmaid2.common.entity.ai.EntityAISuppleEnergy;
import com.xwm.magicmaid2.common.world.dimension.DimensionRuinForest;
import com.xwm.magicmaid2.core.init.ItemInit;
import com.xwm.magicmaid2.core.init.LootTableInit;
import com.xwm.magicmaid2.core.init.PotionInit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;

public class EntityMagicMaidXimoBoss extends EntityMagicMaidXimo implements IEntityBossCreature
{

    private int factor = 1;
    private boolean selfKilled = false;
    private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName().appendText(" 剩余血条: " + getHealthBarNum()), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS)).setDarkenSky(false);


    private AnimationFactory factory = new AnimationFactory(this);

    public EntityMagicMaidXimoBoss(World worldIn)
    {
        super(worldIn);
        this.ignoreFrustumCheck = true;
        this.initFightManager(worldIn);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.setMode(EnumMode.toInt(EnumMode.BOSS));
        this.setRank(2);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (MagicEquipmentRegistry.getAttribute(this.getWeaponType()) == MagicEquipmentRegistry.NONE && !world.isRemote) {
            ItemStack stack = ItemStack.EMPTY;

            stack = new ItemStack(ItemInit.EAGER);

            this.inventory.func_70299_a(0, stack);
            SPacketMaidInventoryUpdate packet = new SPacketMaidInventoryUpdate(getEntityId(), dimension, 0, stack);
            NetworkLoader.instance.sendToDimension(packet, dimension);
        }

        if (MagicEquipmentRegistry.getAttribute(this.getArmorType()) == MagicEquipmentRegistry.NONE && !world.isRemote){
            ItemStack stack = new ItemStack(ItemInit.XIMO_CORE);
            this.inventory.func_70299_a(1, stack);
            SPacketMaidInventoryUpdate packet = new SPacketMaidInventoryUpdate(getEntityId(), dimension, 1, stack);
            NetworkLoader.instance.sendToAll(packet);
        }

        PotionEffect effect = this.getActivePotionEffect(PotionInit.POTION_DISTORT); // 不受卡修肉身崩坏影响
        if (effect != null)
            this.removePotionEffect(PotionInit.POTION_DISTORT);

        this.bossInfo.setName(this.getDisplayName().appendText(" 剩余血量: " + getHealthBarNum()));
        this.bossInfo.setPercent(getHealth() / getMaxHealth());

        if (fightManager != null) {
            fightManager.onBossUpdate(this);
        }
    }

    /**
     * 提供斩杀接口
     *
     * @param player
     */
    @Override
    public boolean killItSelfByPlayerDamage(EntityPlayer player) {
        if (player.getEntityWorld().isRemote)
            return true;
        this.setAvoidSetHealth(-1);
        this.setAvoidDamage(-1);
        return super.attackEntityFrom(DamageSource.
                causePlayerDamage(player), getMaxHealth()+1);
    }

    @Override
    public void killSelf() {
        super.killSelf();
        this.selfKilled = true;
    }

    @Override
    public IMagicBossManager getFightManager() {
        return fightManager;
    }

    /**
     * 初始化战斗管理器
     */
    @Override
    public void initFightManager(World world) {
        if (!world.isRemote && world.provider instanceof DimensionRuinForest)
        {
            this.fightManager = ((DimensionRuinForest)world.provider).getFightManager();
            this.fightManager.init(this);
        }
        else
        {
            this.fightManager = null;
        }
    }

    @Override
    public void onDeathUpdate()
    {
        super.onDeathUpdate();
        if (getMaxHealth() > 0 && getTrueHealth() <= 0 && fightManager != null) {
            if (this.deathTime == 20) {
                fightManager.setBossAlive(false); //boss真实死亡
            }
            fightManager.setBossKilled(true);
        }
    }

    @Override
    public int getAttackDamage(EquipmentAttribute type)
    {
        return factor * super.getAttackDamage(type);
    }
    /**
     * 设置攻击倍率
     *
     * @param factor
     */
    @Override
    public void setBossDamageFactor(int factor) {
        this.factor = factor;
    }


    @Override
    public int getBossCamp() {
        return 1; // 不受正义影响
    }

    /**
     * 创造一个警告区域，用于boss攻击的抬手范围提示
     *
     * @param i  渲染区域的id号，必须唯一
     * @param bb 攻击区域
     */
    @Override
    public void createWarningArea(int i, AxisAlignedBB bb) {
        if (world.isRemote)
            MagicRenderRegistry.addRenderBox(i, bb);
        else
        {
            RenderAreaPacket packet = new RenderAreaPacket(i, 0, bb);
            NetworkRegistry.TargetPoint target = new NetworkRegistry.TargetPoint(this.getEntityWorld().provider.getDimension(), posX, posY, posZ, 40.0D);
            NetworkLoader.instance.sendToAllAround(packet, target);
        }
    }

    /**
     * 消除警告区域
     *
     * @param i 渲染区域的id号
     */
    @Override
    public void removeWarningArea(int i) {
        if (world.isRemote)
            MagicRenderRegistry.removeRenderBox(i);
        else
        {
            RenderAreaPacket packet = new RenderAreaPacket(i, 1);
            NetworkLoader.instance.sendToAll(packet);
        }
    }


    protected boolean shouldDropEquipment() {
        return false;
    }

    /**
     * Sets the custom name tag for this entity
     */
    public void setCustomNameTag(String name)
    {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    /**
     * Add the given player to the list of players tracking this entity. For instance, a player may track a boss in
     * order to view its associated boss bar.
     */
    public void addTrackingPlayer(EntityPlayerMP player)
    {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    /**
     * Removes the given player from the list of players tracking this entity. See {@link Entity#addTrackingPlayer} for
     * more information on tracking.
     */
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }



    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        target.attackEntityFrom(DamageSource.causeMobDamage(this), this.getAttackDamage(MagicEquipmentRegistry.NONE));
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {

    }


    @Override
    protected ResourceLocation getLootTable()
    {
        if (getTrueHealth() > 0 || selfKilled) return null;
        return LootTableInit.EAGER;
    }

    @Override
    public boolean canEquip(ItemEquipment itemEquipment) {
        return false;
    }

    public void fall(float distance, float damageMultiplier)
    {
    }

    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
    {
    }

    /**
     * Returns true if this entity should move as if it were on a ladder (either because it's actually on a ladder, or
     * for AI reasons)
     */
    public boolean isOnLadder()
    {
        return false;
    }

}
