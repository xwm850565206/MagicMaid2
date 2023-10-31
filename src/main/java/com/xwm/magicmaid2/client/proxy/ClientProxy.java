package com.xwm.magicmaid2.client.proxy;


import com.xwm.magicmaid.gui.GuiInstructionBook;
import com.xwm.magicmaid.gui.GuiShowMemory;
import com.xwm.magicmaid.registry.MagicModelRegistry;
import com.xwm.magicmaid2.client.model.javamodel.*;
import com.xwm.magicmaid2.client.render.RenderHandler;
import com.xwm.magicmaid2.common.proxy.CommonProxy;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResource;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ClientProxy extends CommonProxy
{

    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);

        GuiInstructionBook.BOOK_REGISTRY.add(Reference.MODID);
        GuiInstructionBook.BOOK_NAME.add("第二章");

        MagicModelRegistry.register(new ResourceLocation(Reference.MODID, Reference.MODID + "_aili"), new ModelAili(), new ResourceLocation(Reference.MODID, "textures/entities/aili.png"));
        MagicModelRegistry.register(new ResourceLocation(Reference.MODID, Reference.MODID + "_cassiu"), new ModelCassiu(), new ResourceLocation(Reference.MODID, "textures/entities/cassiu.png"));
        MagicModelRegistry.register(new ResourceLocation(Reference.MODID, Reference.MODID + "_carlie"), new ModelCarlie(), new ResourceLocation(Reference.MODID, "textures/entities/carlie.png"));
        MagicModelRegistry.register(new ResourceLocation(Reference.MODID, Reference.MODID + "_ximo"), new ModelXimo(), new ResourceLocation(Reference.MODID, "textures/entities/ximo.png"));
        MagicModelRegistry.register(new ResourceLocation(Reference.MODID, Reference.MODID + "_neva"), new ModelNeva(), new ResourceLocation(Reference.MODID, "textures/entities/neva.png"));
        MagicModelRegistry.register(new ResourceLocation(Reference.MODID, Reference.MODID + "_maier"), new ModelMaier(), new ResourceLocation(Reference.MODID, "textures/entities/maier.png"));

        GuiShowMemory.MEMORY_POEM.put(3, new ResourceLocation(Reference.MODID, "texts/memory_aili.txt"));
        GuiShowMemory.MEMORY_POEM.put(4, new ResourceLocation(Reference.MODID, "texts/memory_carlie.txt"));
        GuiShowMemory.MEMORY_POEM.put(5, new ResourceLocation(Reference.MODID, "texts/memory_cassiu.txt"));
        GuiShowMemory.MEMORY_POEM.put(6, new ResourceLocation(Reference.MODID, "texts/letter.txt"));
        GuiShowMemory.MEMORY_POEM.put(7, new ResourceLocation(Reference.MODID, "texts/memory_ximo.txt"));
        GuiShowMemory.MEMORY_POEM.put(8, new ResourceLocation(Reference.MODID, "texts/memory_neva.txt"));
        RenderHandler.registerEntityRenders();
    }

    public void init(FMLInitializationEvent event){
        super.init(event);
    }

    public void postInit(FMLPostInitializationEvent event){
        super.postInit(event);
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }

    @Override
    public void registerOBJRenderer(Item item, int meta, String id)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
    }

    @Override
    public ItemStack getWrittenBook(String name) {
        Minecraft mc = Minecraft.getMinecraft();
        IResource iresource = null;
        ItemStack tomeStack = new ItemStack(Items.WRITTEN_BOOK);
        NBTTagList bookPages = new NBTTagList();

        try {
            String filename = "texts/" + name + ".txt";
            iresource = mc.getResourceManager().getResource(new ResourceLocation(Reference.MODID, filename));
            InputStream inputstream = iresource.getInputStream();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
            StringBuilder s = new StringBuilder();
            String s1;
            while ((s1 = bufferedreader.readLine()) != null)
            {
                if (s1.equals("###")) {
                    bookPages.appendTag(new NBTTagString("\"" + s.toString() + "\""));
                    s = new StringBuilder();
                }
                else{
                    s.append(s1);
                    s.append('\n');
                }
            }
            if (s.length() > 0)
                bookPages.appendTag(new NBTTagString("\"" + s.toString() + "\""));

        } catch (IOException e) {
            bookPages.appendTag(new NBTTagString("...读取说明书失败"));
        }

        tomeStack.setTagInfo("pages", bookPages);
        tomeStack.setTagInfo("author", new NBTTagString("梅尔"));
        tomeStack.setTagInfo("title", new NBTTagString("手札"));
        return tomeStack;
    }
}
