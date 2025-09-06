package vpp.vac.skyquill.module.impl.mining;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import vpp.vac.skyquill.main.Main;
import vpp.vac.skyquill.module.Category;
import vpp.vac.skyquill.module.Module;
import vpp.vac.skyquill.util.Logger;

public class Chestcounter extends Module {

	public static int count;

	public Chestcounter() {
		this.name = "Chestcounter";
		this.description = "Keeps track of how many chests you have found while powder grinding";
		this.moduleCategory = Category.MINING;
	}

	@Override
	public void onEnable() {
		this.toggled = true;
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void onDisable() {
		this.toggled = false;
		MinecraftForge.EVENT_BUS.unregister(this);
	}

	@SubscribeEvent
	public void onChatReceived(ClientChatReceivedEvent event) {
		if (this.toggled) {
			String message = event.message.getUnformattedText();
			Logger log = new Logger(Main.PREFIX, "[CHESTCOUNTER]");

			if (Main.DebugMode) {
				log.sendLog("Received message: " + message);
			}

			String[] lines = message.split("\n");
			for (String line : lines) {
				if (line.contains("You uncovered a treasure chest!")) {
					count++;
					if (Main.DebugMode) {
						log.sendLog("Found treasure chest, new count is: " + count);
					}
				}
			}
		}
	}

}
