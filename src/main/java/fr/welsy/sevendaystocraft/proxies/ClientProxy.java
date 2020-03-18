package fr.welsy.sevendaystocraft.proxies;

import fr.welsy.sevendaystocraft.client.itemrenderer.ItemRendererCustom;
import fr.welsy.sevendaystocraft.common.init.ItemMod;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy{

	@Override
	public void registerRenderers() {
		super.registerRenderers();
		MinecraftForgeClient.registerItemRenderer(ItemMod.CLUB, (IItemRenderer)new ItemRendererCustom());
		MinecraftForgeClient.registerItemRenderer(ItemMod.CLUB_WIRED, (IItemRenderer)new ItemRendererCustom());
		System.out.println("RENDER");
	}

	
}
