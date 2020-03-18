package fr.welsy.sevendaystocraft;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import fr.welsy.sevendaystocraft.common.event.EventsHandler;
import fr.welsy.sevendaystocraft.common.init.ItemMod;
import fr.welsy.sevendaystocraft.common.network.PacketTemperature;
import fr.welsy.sevendaystocraft.common.utils.References;
import fr.welsy.sevendaystocraft.proxies.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

@Mod(name = References.NAME, version = References.VERSION, modid = References.MODID)
public class SevenDaysToCraft {

	@Mod.Instance()
	public static SevenDaysToCraft instance;
	
	@SidedProxy(clientSide = References.CLIENT_PROXY, serverSide = References.SERVER_PROXY)
	public static CommonProxy proxy;
	
    public static SimpleNetworkWrapper network;

    public static CreativeTabs TAB = new CreativeTabs(References.MODID) {
		
		@Override
		public Item getTabIconItem() {
			return ItemMod.CLUB;
		}
	};
    
    @EventHandler
	public void preInit(FMLPreInitializationEvent e) {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(References.MODID);
        network.registerMessage(PacketTemperature.Handler.class, PacketTemperature.class, 0, Side.SERVER);
        network.registerMessage(PacketTemperature.Handler.class, PacketTemperature.class, 1, Side.CLIENT);

    	FMLCommonHandler.instance().bus().register(new EventsHandler());
		MinecraftForge.EVENT_BUS.register(new EventsHandler());
		
		ItemMod.init();
	}
    
    @EventHandler
    public void init(FMLInitializationEvent e) {
		proxy.registerRenderers();
    }
	
}
