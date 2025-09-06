package vpp.vac.skyquill.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import vpp.vac.skyquill.main.Main;

public class ContainerChecker {
	private static int debugCount = 0;

	public static boolean containsCrystalFragment() {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		Container container = player.openContainer;
		final Logger log = new Logger();

		if (container == null) {
			return false;
		}

		for (Slot slot : container.inventorySlots) {
			ItemStack stack = slot.getStack();

			if (stack != null && stack.getItem() == Items.quartz && stack.hasDisplayName()
					&& stack.getDisplayName().contains("Crystal Fragment")) {
				if (Main.DebugMode) {
					debugCount++;
					log.sendLog("Crystal fragment detected in container x" + debugCount);
					if (debugCount % 200 == 0) {
						log.sendLog("Crystal fragment detection for [" + debugCount + "] ticks, seconds: ["
								+ debugCount / 200 + "]");
					}
				}
				return true;
			}
		}

		return false;
	}
	
	public static boolean containsDiamanteHandle() {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		Container container = player.openContainer;
		final Logger log = new Logger();

		if (container == null) {
			return false;
		}

		for (Slot slot : container.inventorySlots) {
			ItemStack stack = slot.getStack();

			if (stack != null && stack.getItem() == Items.diamond_horse_armor && stack.hasDisplayName()
					&& stack.getDisplayName().contains("Diamante")) {
				if (Main.DebugMode) {
					debugCount++;
					log.sendLog("Diamante handle detected in container x" + debugCount);
					if (debugCount % 200 == 0) {
						log.sendLog("Diamante handle detection for [" + debugCount + "] ticks, seconds: ["
								+ debugCount / 200 + "]");
					}
				}
				return true;
			}
		}

		return false;
	}

}
