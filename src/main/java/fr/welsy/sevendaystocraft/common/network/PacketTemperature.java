package fr.welsy.sevendaystocraft.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import fr.welsy.sevendaystocraft.common.eep.ExtendedEntityPropTemperature;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class PacketTemperature implements IMessage {

	public float temp;

	public PacketTemperature() {
	}

	public PacketTemperature(float temp) {
		this.temp = temp;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.temp = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(this.temp);
	}

	public static class Handler implements IMessageHandler<PacketTemperature, IMessage> {

		@Override
		public IMessage onMessage(PacketTemperature message, MessageContext ctx) {
			if(ctx.side == Side.SERVER) {
				ExtendedEntityPropTemperature props = ExtendedEntityPropTemperature.get(ctx.getServerHandler().playerEntity);
				props.temperature = message.temp;
			}else {
				ExtendedEntityPropTemperature props = ExtendedEntityPropTemperature.get(Minecraft.getMinecraft().thePlayer);
				props.temperature = message.temp;
			}
			
			return null;
		}

	}

}