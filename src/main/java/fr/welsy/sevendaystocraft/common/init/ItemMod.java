package fr.welsy.sevendaystocraft.common.init;

import cpw.mods.fml.common.registry.GameRegistry;
import fr.welsy.sevendaystocraft.common.items.ItemClub;
import fr.welsy.sevendaystocraft.common.items.ItemClubWired;
import fr.welsy.sevendaystocraft.common.items.impl.ItemBase;

public class ItemMod {

	public static final ItemBase CLUB = new ItemClub();
	public static final ItemBase CLUB_WIRED = new ItemClubWired();

	private static final ItemBase[] ITEMS = {
			CLUB,
			CLUB_WIRED
	};
	
	public static void init() {
		for(ItemBase it : ITEMS){
			GameRegistry.registerItem(it, it.name);
		}
	}
	
}
