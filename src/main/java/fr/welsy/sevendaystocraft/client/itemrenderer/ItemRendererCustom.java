package fr.welsy.sevendaystocraft.client.itemrenderer;

import org.lwjgl.opengl.GL11;

import fr.welsy.sevendaystocraft.client.models.ModelClub;
import fr.welsy.sevendaystocraft.client.models.ModelClubWired;
import fr.welsy.sevendaystocraft.common.init.ItemMod;
import fr.welsy.sevendaystocraft.common.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRendererCustom implements IItemRenderer {

	protected ModelClub clubM = new ModelClub();
	protected ModelClubWired clubWM = new ModelClubWired();

	protected static final ResourceLocation club = new ResourceLocation(References.MODID, "textures/items/club.png");
	protected static final ResourceLocation club_wired = new ResourceLocation(References.MODID,
			"textures/items/club_wired.png");

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.EQUIPPED || type == ItemRenderType.INVENTORY
				|| type == ItemRenderType.ENTITY;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
		case EQUIPPED_FIRST_PERSON: {
			if(item.getItem() == ItemMod.CLUB) {
				GL11.glPushMatrix();
				GL11.glTranslated(0.5, 0.8, -0.0625);
				Minecraft.getMinecraft().renderEngine.bindTexture(club);
				clubM.renderAll();
				GL11.glPopMatrix();
			}else if(item.getItem() == ItemMod.CLUB_WIRED) {
				GL11.glPushMatrix();
				GL11.glTranslated(0.5, 0.8, -0.0625);
				Minecraft.getMinecraft().renderEngine.bindTexture(club_wired);
				clubWM.renderAll();
				GL11.glPopMatrix();
			}
			
			break;
		}
		case EQUIPPED: {
			if(item.getItem() == ItemMod.CLUB) {
				GL11.glPushMatrix();
				GL11.glTranslated(0.5, 0.8, -0.0625);
				Minecraft.getMinecraft().renderEngine.bindTexture(club);
				clubM.renderAll();
				GL11.glPopMatrix();
			}else if(item.getItem() == ItemMod.CLUB_WIRED) {
				GL11.glPushMatrix();
				GL11.glTranslated(0.5, 0.8, -0.0625);
				Minecraft.getMinecraft().renderEngine.bindTexture(club_wired);
				clubWM.renderAll();
				GL11.glPopMatrix();
			}
			
			break;
		}
		case INVENTORY: {
			if(item.getItem() == ItemMod.CLUB) {
				GL11.glPushMatrix();
				GL11.glTranslated(8, 8, 0);
				GL11.glRotated(45, 0, 0, 1);
				GL11.glScaled(15, 15, 15);
				Minecraft.getMinecraft().renderEngine.bindTexture(club);
				clubM.renderAll();
				GL11.glPopMatrix();
			}else if(item.getItem() == ItemMod.CLUB_WIRED) {
				GL11.glPushMatrix();
				GL11.glTranslated(8, 8, 0);
				GL11.glRotated(45, 0, 0, 1);
				GL11.glScaled(15, 15, 15);
				Minecraft.getMinecraft().renderEngine.bindTexture(club_wired);
				clubWM.renderAll();
				GL11.glPopMatrix();
			}
			
			break;
		}
		case ENTITY: {
			if(item.getItem() == ItemMod.CLUB) {
				GL11.glPushMatrix();
				GL11.glTranslated(0.0, 0.4, 0);
				Minecraft.getMinecraft().renderEngine.bindTexture(club);
				clubM.renderAll();
				GL11.glPopMatrix();
			}else if(item.getItem() == ItemMod.CLUB_WIRED) {
				GL11.glPushMatrix();
				GL11.glTranslated(0.0, 0.4, 0);
				Minecraft.getMinecraft().renderEngine.bindTexture(club_wired);
				clubWM.renderAll();
				GL11.glPopMatrix();
			}
			
			break;
		}
		default:
			break;
		}
	}

}
