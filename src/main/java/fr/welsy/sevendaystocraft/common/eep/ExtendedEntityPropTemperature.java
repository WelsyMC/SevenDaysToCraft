package fr.welsy.sevendaystocraft.common.eep;

import fr.welsy.sevendaystocraft.SevenDaysToCraft;
import fr.welsy.sevendaystocraft.common.network.PacketTemperature;
import fr.welsy.sevendaystocraft.proxies.CommonProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExtendedEntityPropTemperature implements IExtendedEntityProperties {

	public final static String EXT_PROP_NAME = "ExtPropTemperature";

	private final EntityPlayer player;

	public float temperature;

	public ExtendedEntityPropTemperature(EntityPlayer player) {
		this.player = player;
		this.temperature = 37.5F;
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound temp = new NBTTagCompound();
		temp.setFloat("temperature", this.temperature);
		compound.setTag(EXT_PROP_NAME, temp);

	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		this.temperature = properties.getFloat("temperature");
	}

	@Override
	public void init(Entity entity, World world) {

	}

	public static final void register(EntityPlayer player) {
		player.registerExtendedProperties(ExtendedEntityPropTemperature.EXT_PROP_NAME,
				new ExtendedEntityPropTemperature(player));
	}

	public static final ExtendedEntityPropTemperature get(EntityPlayer player) {
		return (ExtendedEntityPropTemperature) player.getExtendedProperties(EXT_PROP_NAME);
	}
	
	public final void sync() {
		PacketTemperature packetTemp = new PacketTemperature(this.temperature);
        SevenDaysToCraft.network.sendToServer(packetTemp);
 
        if (!player.worldObj.isRemote) {
            EntityPlayerMP player1 = (EntityPlayerMP) player;
            SevenDaysToCraft.network.sendTo(packetTemp, player1);
        }
    }
	
	private static String getSaveKey(EntityPlayer player) {
        return player.getDisplayName() + ":" + EXT_PROP_NAME;
    }
	
    public static void saveProxyData(EntityPlayer player) {
    	ExtendedEntityPropTemperature playerData =ExtendedEntityPropTemperature.get(player);
        NBTTagCompound savedData = new NBTTagCompound();
 
        playerData.saveNBTData(savedData);
        CommonProxy.storeEntityData(getSaveKey(player), savedData);
    }
 
    public static void loadProxyData(EntityPlayer player) {
    	ExtendedEntityPropTemperature playerData = ExtendedEntityPropTemperature.get(player);
        NBTTagCompound savedData = CommonProxy
                .getEntityData(getSaveKey(player));
 
        if (savedData != null) {
            playerData.loadNBTData(savedData);
        }
        playerData.sync();
    }

    public void setTemperature(float newTemp) {
    	this.temperature = newTemp;
    	this.sync();
    }
    
    public void removeTemp(float old) {
    	this.temperature -= old;
    	this.sync();
    }
    
    public void addTemp(float newa) {
    	this.temperature += newa;
    	this.sync();
    }
    
    public float getTemperature() {
    	this.sync();
    	return this.temperature;
    }
}
