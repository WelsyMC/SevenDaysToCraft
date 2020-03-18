package fr.welsy.sevendaystocraft.client.gui;

import org.lwjgl.opengl.GL11;

import fr.welsy.sevendaystocraft.client.gui.components.GuiButtonTextured;
import fr.welsy.sevendaystocraft.common.utils.References;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.util.ResourceLocation;

public class GuiMainMenuS extends GuiScreen {

	public static final ResourceLocation background = new ResourceLocation(References.MODID,
			"textures/gui/background.png");
	public static final ResourceLocation logo = new ResourceLocation(References.MODID, "textures/gui/logo.png");

	@Override
	public void initGui() {
		super.initGui();
		buttonList.clear();
		this.buttonList.add(new GuiButtonTextured(0, (this.width - 100) / 2, this.height / 2 - 45, 100, 40,
				"Singleplayer", new ResourceLocation(References.MODID, "textures/gui/paper_button.png"), null));
		this.buttonList.add(new GuiButtonTextured(1, (this.width - 100) / 2, this.height / 2 + 5, 100, 40,
				"Multiplayer", new ResourceLocation(References.MODID, "textures/gui/paper_button.png"), null));
		this.buttonList.add(new GuiButtonTextured(2, 0, 0, 100, 40, "Settings",
				new ResourceLocation(References.MODID, "textures/gui/paper_button.png"), null));

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float ticks) {
		this.mc.getTextureManager().bindTexture(background);
		Gui.func_152125_a(0, 0, 0, 0, 1, 1, this.width, this.height, 1, 1);

		GL11.glEnable(GL11.GL_BLEND);
		this.mc.getTextureManager().bindTexture(logo);
		Gui.func_152125_a((this.width - 160) / 2, 5, 0, 0, 1, 1, 160, 90, 1, 1);
		GL11.glDisable(GL11.GL_BLEND);

		super.drawScreen(mouseX, mouseY, ticks);

	}

	@Override
	protected void actionPerformed(GuiButton b) {
		switch (b.id) {
		case 0:
			mc.displayGuiScreen(new GuiSelectWorld(this));
			break;
		case 1:
			mc.displayGuiScreen(new GuiMultiplayer(this));
			break;
		case 2:
			mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
			break;
		}
	}
}
