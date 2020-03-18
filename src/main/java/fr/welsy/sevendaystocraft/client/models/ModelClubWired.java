package fr.welsy.sevendaystocraft.client.models;

import fr.welsy.sevendaystocraft.common.utils.References;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelClubWired {

	private IModelCustom model;
	
	public ModelClubWired() {
		model = AdvancedModelLoader.loadModel(new ResourceLocation(References.MODID, "models/club_wired.obj"));
	}
	
	public void render(String part) {
		model.renderPart(part);
	}
	
	public void renderAll() {
		model.renderAll();
	}
}
