package vpp.vac.skyquill.module.impl.settings;

import vpp.vac.skyquill.main.Main;
import vpp.vac.skyquill.module.Category;
import vpp.vac.skyquill.module.Module;

public class DeveloperMode extends Module{
	
	public DeveloperMode() {
		this.name = "Developer Mode";
		this.moduleCategory = Category.SETTINGS;
		this.description = "Developer mode mostly enables debug messages in the console, but may mess other things up if enabled!";
	}
	
	@Override
	public void onEnable() {
		this.toggled = true;
		Main.DebugMode = true;
	}
	
	@Override
	public void onDisable() {
		this.toggled = false;
		Main.DebugMode = false;
	}

}
