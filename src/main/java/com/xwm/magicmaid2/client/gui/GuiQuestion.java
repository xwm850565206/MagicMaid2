package com.xwm.magicmaid2.client.gui;

import com.google.common.collect.Lists;
import com.xwm.magicmaid.network.entity.CPacketEntityData;
import com.xwm.magicmaid2.Main;
import com.xwm.magicmaid2.core.init.ItemInit;
import com.xwm.magicmaid2.core.network.NetworkLoader;
import com.xwm.magicmaid2.core.network.packet.client.CPacketGiveItem;
import com.xwm.magicmaid2.core.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.IOUtils;
import org.lwjgl.input.Keyboard;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SideOnly(Side.CLIENT)
public class GuiQuestion extends GuiScreen
{
    public static ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/question.png");
    public static ResourceLocation TEXT = new ResourceLocation(Reference.MODID, "texts/question.txt");
    private final int imageWidth = 248;
    private final int imageHeight = 163;
    private GuiTextField answer;
    private List<String> lines;
    private int index;
    private Random random = new Random();

    public GuiQuestion()
    {

    }

    @Override
    public void initGui() {
        super.initGui();
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        this.addButton(new GuiButton(0, i + 200, j + 136, 20, 10, "确定"));
        this.addButton(new GuiButton(1, i + 10, j + 136, 26, 10, "上一页"));
        this.addButton(new GuiButton(2, i + 44, j + 136, 26, 10, "下一页"));

        Keyboard.enableRepeatEvents(true);
        this.answer = new GuiTextField(1, this.fontRenderer, i + 80, j + 136, 80, this.fontRenderer.FONT_HEIGHT);
        this.answer.setMaxStringLength(50);
        this.answer.setEnableBackgroundDrawing(false);
        this.answer.setTextColor(16777215);

        if (this.lines == null)
        {
            this.lines = Lists.<String>newArrayList();
            IResource iresource = null;

            try
            {
                String s = "" + TextFormatting.WHITE + TextFormatting.OBFUSCATED + TextFormatting.GREEN + TextFormatting.AQUA;
                iresource = this.mc.getResourceManager().getResource(TEXT);
                InputStream inputstream = iresource.getInputStream();
                BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
                Random random = new Random(8124371L);
                String s1;

                while ((s1 = bufferedreader.readLine()) != null)
                {
                    this.lines.addAll(this.mc.fontRenderer.listFormattedStringToWidth(s1, 240));
                }
                inputstream.close();
            }
            catch (Exception exception)
            {
                Main.logger.error("Couldn't load memory", (Throwable)exception);
            }
            finally
            {
                IOUtils.closeQuietly((Closeable)iresource);
            }
        }
        index = 0;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {


        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(i, j, 0, 0, imageWidth, imageHeight);
        this.answer.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
        drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        for (int t = 0; t+index*7 < lines.size() && t < 7; t++) {
            fontRenderer.drawString(lines.get(t+index*7), i+4, j + 60 + t*fontRenderer.FONT_HEIGHT, 0x000000);
        }
    }


    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (this.answer.textboxKeyTyped(typedChar, keyCode))
        {

        }
        else {
            super.keyTyped(typedChar, keyCode);
        }
    }


    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.answer.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled)
        {
            if (button.id == 0) {
                if (checkAnswer()) {
                    EntityPlayer player = Minecraft.getMinecraft().player;
                    player.sendMessage(new TextComponentString("你答对了我的问题，而这也是历史的必然，去追寻，去创造奇迹。"));
                    CPacketGiveItem packet = new CPacketGiveItem(new ItemStack(ItemInit.MEMORY_CORPSE), Minecraft.getMinecraft().player.getUniqueID());
                    NetworkLoader.instance.sendToServer(packet);

                    player.getEntityData().setBoolean(Reference.MODID + "_answer", true);
                    CPacketEntityData packet1 = new CPacketEntityData(player.getEntityId(), player.dimension, 0, "true", Reference.MODID + "_answer");
                    com.xwm.magicmaid.network.NetworkLoader.instance.sendToServer(packet1);

                    EntityLightningBolt bolt = new EntityLightningBolt(player.world, player.posX, player.posY, player.posZ, true);
                    player.world.spawnEntity(bolt);
                    player.world.addWeatherEffect(bolt);
                    mc.displayGuiScreen(null);
                }
                else {
                    String[] msg = {"错！", "再想想吧", "看来你不是我要找的人", "得道多助失道寡助"};
                    Minecraft.getMinecraft().player.sendMessage(new TextComponentString(msg[random.nextInt(msg.length)]));
                }
            }
            else if (button.id == 1) {
                index = index > 0?index-1:index;
            }
            else if (button.id == 2) {
                index = index*7 > lines.size()?index:index+1;
            }
        }
    }

    private boolean checkAnswer()
    {
        String answer = this.answer.getText();
        if (answer.equals("大于3") || answer.equals(">3") || answer.matches(".>3") || answer.matches(".大于3"))
            return true;
        else
            return false;
    }
}
