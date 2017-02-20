package com.jj.jjmod.gui;

import com.jj.jjmod.container.ContainerInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Gui for the player inventory */
public class GuiInventory extends GuiContainerAbstract {
    
    private static final int PLAYER_X = 51;
    private static final int PLAYER_Y = 75;
    private static final int PLAYER_SCALE = 30;
    private static final int Y_OFFSET = -50;
    private static final int TEXT_X = 97;

    public GuiInventory(ContainerInventory container) {

        super(container, "Inventory");
        this.allowUserInput = true;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        
        this.fontRendererObj.drawString(this.name, TEXT_X, 6, TEXT_COLOUR);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float ticks,
            int mouseX, int mouseY) {

        super.drawGuiContainerBackgroundLayer(ticks, mouseX, mouseY);
        drawEntityOnScreen(this.guiLeft + PLAYER_X, this.guiTop + PLAYER_Y, PLAYER_SCALE,
                this.xSize + PLAYER_X - mouseX,
                this.ySize + PLAYER_Y + Y_OFFSET - mouseY, this.mc.player);
    }

    @Override
    protected ResourceLocation getTexture() {

        return ((ContainerInventory) this.inventorySlots).getBackground();
    }
}
