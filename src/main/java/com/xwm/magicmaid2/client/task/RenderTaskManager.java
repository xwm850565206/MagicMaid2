package com.xwm.magicmaid2.client.task;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@SideOnly(Side.CLIENT)
public class RenderTaskManager
{
    public static Queue<TaskBase> renderTasks = new LinkedBlockingQueue<>();

    public static void processRenderTask(RenderWorldLastEvent event)
    {
        Iterator<TaskBase> iterator = renderTasks.iterator();
        while (iterator.hasNext())
        {
            TaskBase task = iterator.next();
            task.run();
            task.increaseAge();
            if (task.isFinish())
                iterator.remove();
        }
    }
}
