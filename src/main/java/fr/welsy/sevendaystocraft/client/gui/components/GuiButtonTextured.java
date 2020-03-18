package fr.welsy.sevendaystocraft.client.gui.components;

import org.lwjgl.opengl.GL11;

import fr.welsy.sevendaystocraft.common.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiButtonTextured extends GuiButton {

	public ResourceLocation texture, hovered;
	public boolean hasSoundPlayed = false;
	
	public GuiButtonTextured(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_,
			String p_i1021_6_, ResourceLocation texture, ResourceLocation hovered) {
		super(p_i1021_1_, p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
		this.texture = texture;
		this.hovered = hovered;
	}

	public GuiButtonTextured(int p_i1020_1_, int p_i1020_2_, int p_i1020_3_, String p_i1020_4_,
			ResourceLocation texture, ResourceLocation hovered) {
		super(p_i1020_1_, p_i1020_2_, p_i1020_3_, p_i1020_4_);
		this.texture = texture;
		this.hovered = hovered;
	}

	@Override
	public void drawButton(Minecraft mc, int p_146112_2_, int p_146112_3_) {
		
        this.field_146123_n = p_146112_2_ >= this.xPosition && p_146112_3_ >= this.yPosition && p_146112_2_ < this.xPosition + this.width && p_146112_3_ < this.yPosition + this.height;

        if(this.field_146123_n) {
        	if(!hasSoundPlayed) {
        		hasSoundPlayed = true;
                mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation(References.MODID, "paper"), 1.0F));
        	}
        }else {
        	hasSoundPlayed = false;
        }
        GL11.glEnable(GL11.GL_BLEND);
        mc.getTextureManager().bindTexture(texture);
		Gui.func_152125_a(xPosition, yPosition, 0, 0, 1, 1, this.width, this.height, 1, 1);
        GL11.glDisable(GL11.GL_BLEND);
        
        this.drawCenteredString(mc.fontRenderer, this.displayString, xPosition + width / 2, yPosition + (height - mc.fontRenderer.FONT_HEIGHT )/ 2, this.field_146123_n ? 0xFF8844FF : 0xFFFFFFFF);
        GL11.glColor4f(1, 1, 1, 1);

	}

}
