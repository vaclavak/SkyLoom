package vpp.vac.skyquill.main;

import net.minecraft.init.Blocks;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import vpp.vac.skyquill.command.Skyquill;
import vpp.vac.skyquill.util.Keybinds;
import vpp.vac.skyquill.util.Logger;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main {
	public static final String MODID = "skyquill";
	public static final String VERSION = "1.0";
	public static boolean DebugMode = true;
	public static final String PREFIX = "[SKYQUILL] ";
	public static final String DEV_VERSION = "A13";

	@EventHandler
	public void init(FMLInitializationEvent event) {
		Logger log = new Logger();
		if (DebugMode) {
			log.sendLog(
					"Debug Mode is enabled! If you did not enable it, please contact support and report the issue \n Debug mode may mess things up!");
		}
		log.sendLog("Init");
		MinecraftForge.EVENT_BUS.register(new Keybinds());
		log.sendLog("Registered keybinds");
        ClientCommandHandler.instance.registerCommand(new Skyquill());
        log.sendLog("Registered Skyquill general command");
	}

}
