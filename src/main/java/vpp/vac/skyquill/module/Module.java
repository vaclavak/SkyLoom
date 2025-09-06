package vpp.vac.skyquill.module;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public abstract class Module {
	
	protected String name;
	protected String description;
	protected boolean toggled;
	protected Category moduleCategory;
	protected Minecraft mc = Minecraft.getMinecraft();
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public boolean isToggled() {
		return this.toggled;
	}
	
	public Category getCategory() {
		return this.moduleCategory;
	}
	
	public void onEnable() {
		this.toggled = true;
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void onDisable() {
		this.toggled = false;
		MinecraftForge.EVENT_BUS.unregister(this);
	}

}
