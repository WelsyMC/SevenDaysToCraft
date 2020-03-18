package fr.welsy.sevendaystocraft.common.event;

import static org.lwjgl.opengl.GL11.glColor4d;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScaled;
import static org.lwjgl.opengl.GL11.glTranslated;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.welsy.sevendaystocraft.SevenDaysToCraft;
import fr.welsy.sevendaystocraft.client.gui.GuiMainMenuS;
import fr.welsy.sevendaystocraft.common.eep.ExtendedEntityPropTemperature;
import fr.welsy.sevendaystocraft.common.init.ItemMod;
import fr.welsy.sevendaystocraft.common.utils.References;
import fr.welsy.sevendaystocraft.proxies.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class EventsHandler {

	public static final ResourceLocation widgets = new ResourceLocation(References.MODID, "textures/gui/widgets.png");
	public static final ResourceLocation health = new ResourceLocation(References.MODID, "textures/gui/health.png");
	public static final ResourceLocation food = new ResourceLocation(References.MODID, "textures/gui/food.png");

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void guiOpen(GuiOpenEvent e) {
		if (e.gui != null && e.gui instanceof GuiMainMenu) {
			e.gui = new GuiMainMenuS();
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void guiOpen(RenderGameOverlayEvent.Pre e) {
		if (e.type == ElementType.HEALTH || e.type == ElementType.FOOD || e.type == ElementType.EXPERIENCE) {
			e.setCanceled(true);
		}

		if (e.type == ElementType.HEALTH) {
			this.renderhud(e);
		}
	}

	@SideOnly(Side.CLIENT)
	private void renderhud(RenderGameOverlayEvent.Pre e) {
		ScaledResolution r = e.resolution;

		EntityPlayer p = Minecraft.getMinecraft().thePlayer;
		double percentage = 0;

		// FOOD
		Gui.drawRect(5, r.getScaledHeight() - 50, 105, r.getScaledHeight() - 30, 0x88222222);
		percentage = (p.getFoodStats().getFoodLevel() / 20D);
		percentage *= 103 - 7;
		if (percentage < 0)
			percentage = 0;

		Gui.drawRect(7, r.getScaledHeight() - 48, (int) percentage + 7, r.getScaledHeight() - 32, 0xFFB58500);

		glPushMatrix();
		glTranslated(10, r.getScaledHeight() - 33 - 13, 28);
		glScaled(0.045, 0.045, 0.045);
		Minecraft.getMinecraft().getTextureManager().bindTexture(food);
		glColor4d(1, 1, 1, 1);
		Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(0, 0, 0, 0, 256, 256);
		glPopMatrix();

		// HEALTH
		Gui.drawRect(5, r.getScaledHeight() - 25, 105, r.getScaledHeight() - 5, 0x88222222);

		percentage = (p.getHealth() / p.getMaxHealth());
		percentage *= 103 - 7;
		if (percentage < 0)
			percentage = 0;
		Gui.drawRect(7, r.getScaledHeight() - 23, (int) percentage + 7, r.getScaledHeight() - 7, 0xFF880000);

		glPushMatrix();
		glTranslated(10, r.getScaledHeight() - 8 - 13, 28);
		glScaled(0.045, 0.045, 0.045);
		glColor4d(1, 1, 1, 1);

		Minecraft.getMinecraft().getTextureManager().bindTexture(health);
		Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(0, 0, 0, 0, 256, 256);
		glPopMatrix();

		// DAY / TEMPERATURE

		long time = p.worldObj.getWorldTime();
		int hours = (int) Math.floor(time / 1000);
		int minutes = (int) ((time % 1000) / 1000.0 * 60);

		
		ExtendedEntityPropTemperature props = ExtendedEntityPropTemperature.get(p);
		String text = "Day: " + Math.floor(p.worldObj.getTotalWorldTime() / 24000) + " | Temperature: " + String.format("%.2f", props.getTemperature()) + "°C | " + hours
				+ ":" + minutes;
		
		int tWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
		Minecraft.getMinecraft().fontRenderer.drawString(text, (r.getScaledWidth() - tWidth) / 2, 20, 0xFFFFFFFF, true);

		// EXPERIENCE BAR
		Gui.drawRect(r.getScaledWidth() / 2 - 90, r.getScaledHeight() - 30, r.getScaledWidth() / 2 + 90,
				r.getScaledHeight() - 24, 0x88222222);
		
		if(props.getTemperature() <= 30.0F) {
			glPushMatrix();
			glColor4d(1, 1, 1, 1);
			glTranslated(5, (r.getScaledHeight() - 32) / 2, 0);
			glScaled(0.20, 0.20, 0.20);
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(References.MODID, "textures/gui/cold.png"));
			Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(0, 0, 0, 0, 256, 256);
			glPopMatrix();
		}

	}

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {

		if (event.entity instanceof EntityPlayer
				&& ExtendedEntityPropTemperature.get((EntityPlayer) event.entity) == null)
			ExtendedEntityPropTemperature.register((EntityPlayer) event.entity);
	}

	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event) {
		if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
			NBTTagCompound playerData = new NBTTagCompound();
			((ExtendedEntityPropTemperature) (event.entity
					.getExtendedProperties(ExtendedEntityPropTemperature.EXT_PROP_NAME))).saveNBTData(playerData);
			CommonProxy.storeEntityData(((EntityPlayer) event.entity).getDisplayName(), playerData);
			ExtendedEntityPropTemperature.saveProxyData((EntityPlayer) event.entity);
		} else {

		}
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
			NBTTagCompound playerData = CommonProxy.getEntityData(((EntityPlayer) event.entity).getDisplayName());
			if (playerData != null) {
				((ExtendedEntityPropTemperature) (event.entity
						.getExtendedProperties(ExtendedEntityPropTemperature.EXT_PROP_NAME))).loadNBTData(playerData);
			}

			((ExtendedEntityPropTemperature) (event.entity
					.getExtendedProperties(ExtendedEntityPropTemperature.EXT_PROP_NAME))).sync();
		}
	}
	
	@SubscribeEvent
	public void onEntityTick(TickEvent.PlayerTickEvent e) {
		if(e.player != null) {
			EntityPlayer p = e.player;
			ExtendedEntityPropTemperature props = ExtendedEntityPropTemperature.get(p);
			if(p.worldObj.getTotalWorldTime() % 60 == 0) {
				if(!p.worldObj.isRemote) {
					if(p.isWet()) {
						props.removeTemp(0.25F);
					}else {
						if(p.worldObj.isRaining() && !p.worldObj.canBlockSeeTheSky((int) Math.floor(p.posX), (int) Math.floor(p.posY), (int) Math.floor(p.posZ))) {
							props.removeTemp(0.25F);
						}else {
							double l = p.worldObj.getBlockLightValue((int) Math.floor(p.posX), (int) Math.floor(p.posY), (int) Math.floor(p.posZ)) ;
							if(l > 5) {
								if(props.getTemperature() < 37.5F) {
									props.addTemp(0.25F);
								}							}else {
								if(p.inventory.armorItemInSlot(0) != null ||
										p.inventory.armorItemInSlot(1) != null ||
										p.inventory.armorItemInSlot(2) != null ||
										p.inventory.armorItemInSlot(3) != null) {
									if(props.getTemperature() < 37.5F) {
										props.addTemp(0.25F);
									}
								}else {
								}
							}
						}
					}
					
					if(props.getTemperature() <= 28.0F) {
						p.attackEntityFrom(DamageSource.magic, 0.5F);
					}
				}
				
				
			}
			
		}
	}
}
