package fr.welsy.sevendaystocraft.common.items.impl;

import fr.welsy.sevendaystocraft.SevenDaysToCraft;
import fr.welsy.sevendaystocraft.common.utils.References;
import net.minecraft.item.Item;

public class ItemBase extends Item{

	public String name;
	
	public ItemBase(String name) {
		this.name = name;
		this.setUnlocalizedName(name);
		this.setTextureName(References.RESC + name);
		this.setCreativeTab(SevenDaysToCraft.TAB);
	}
}
