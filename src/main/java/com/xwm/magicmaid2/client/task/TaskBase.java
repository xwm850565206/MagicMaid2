package com.xwm.magicmaid2.client.task;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
@SideOnly(Side.CLIENT)
public abstract class TaskBase
{
    protected Entity taskOwner;
    protected int age;
    protected int maxAge;

    public TaskBase(@Nullable Entity taskOwner, int maxAge) {
        this.taskOwner = taskOwner;
        this.maxAge = maxAge;
    }

    abstract void run();

    public boolean isFinish() {
        return age >= maxAge;
    }

    public Entity getTaskOwner() {
        return taskOwner;
    }

    public void setTaskOwner(Entity taskOwner) {
        this.taskOwner = taskOwner;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public void increaseAge(){
        this.age++;
    }
}
