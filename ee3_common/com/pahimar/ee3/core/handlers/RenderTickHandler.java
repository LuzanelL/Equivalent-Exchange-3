package com.pahimar.ee3.core.handlers;

import java.util.EnumSet;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.pahimar.ee3.core.helper.RenderUtils;
import com.pahimar.ee3.core.helper.TransmutationHelper;
import com.pahimar.ee3.core.helper.VersionHelper;
import com.pahimar.ee3.item.ITransmutationStone;
import com.pahimar.ee3.lib.ConfigurationSettings;
import com.pahimar.ee3.lib.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.MovingObjectPosition;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class RenderTickHandler implements ITickHandler {

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {

    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {

        Minecraft minecraft = FMLClientHandler.instance().getClient();
        EntityPlayer player = minecraft.thePlayer;
        ItemStack currentItemStack = null;

        if (type.contains(TickType.RENDER)) {
            if (player != null) {
                currentItemStack = player.inventory.getCurrentItem();

                if ((currentItemStack != null) && (minecraft.inGameHasFocus) && (currentItemStack.getItem() instanceof ITransmutationStone) && (ConfigurationSettings.ENABLE_OVERLAY_WORLD_TRANSMUTATION)) {
                    renderStoneHUD(minecraft, player, currentItemStack, (Float) tickData[0]);
                }
            }
        }
    }

    @Override
    public EnumSet<TickType> ticks() {

        return EnumSet.of(TickType.CLIENT, TickType.CLIENTGUI, TickType.RENDER);
    }

    @Override
    public String getLabel() {

        return Reference.MOD_NAME + ": " + this.getClass().getSimpleName();
    }

    private static void renderStoneHUD(Minecraft minecraft, EntityPlayer player, ItemStack stack, float partialTicks) {

        float overlayScale = 2F;
        float blockScale = overlayScale / 2;
        float overlayOpacity = 0.5F;

        MovingObjectPosition rayTrace = minecraft.objectMouseOver;
        ItemStack currentBlock = null;

        if ((player.worldObj != null) && (rayTrace != null)) {
            currentBlock = TransmutationHelper.getNextBlock(player.worldObj.getBlockId(rayTrace.blockX, rayTrace.blockY, rayTrace.blockZ), player.worldObj.getBlockMetadata(rayTrace.blockX, rayTrace.blockY, rayTrace.blockZ), player.isSneaking());
        }

        GL11.glPushMatrix();
        ScaledResolution sr = new ScaledResolution(minecraft.gameSettings, minecraft.displayWidth, minecraft.displayHeight);
        GL11.glClear(256);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, sr.getScaledWidth_double(), sr.getScaledHeight_double(), 0.0D, 1000.0D, 3000.0D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000.0F);

        GL11.glPushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glEnable(GL11.GL_LIGHTING);
        
        int hudOverlayX = (int) (sr.getScaledWidth() - (16 * overlayScale));
        int hudOverlayY = (int) (sr.getScaledHeight() - (16 * overlayScale));
        int hudBlockX = (int) (sr.getScaledWidth() - (16 * overlayScale) / 2 - 8);
        int hudBlockY = (int) (sr.getScaledHeight() - (16 * overlayScale) / 2 - 8);
        
        RenderUtils.renderItemIntoGUI(minecraft.fontRenderer, minecraft.renderEngine, stack, hudOverlayX, hudOverlayY, overlayOpacity, overlayScale);
        if ((currentBlock != null) && (currentBlock.getItem() instanceof ItemBlock)) {
            RenderUtils.renderRotatingBlockIntoGUI(minecraft.fontRenderer, minecraft.renderEngine, currentBlock, hudBlockX, hudBlockY, -90, blockScale);
        }
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

}
