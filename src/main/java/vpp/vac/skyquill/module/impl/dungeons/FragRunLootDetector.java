package vpp.vac.skyquill.module.impl.dungeons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import vpp.vac.skyquill.main.Main;
import vpp.vac.skyquill.module.Category;
import vpp.vac.skyquill.util.ContainerChecker;
import vpp.vac.skyquill.util.FragType;

public class FragRunLootDetector extends vpp.vac.skyquill.module.Module {

	private static int scale = 5;
	private String textHandle = "DIAMANTE HANDLE";
	private String textEye = "L.A.S.R EYE";
	private boolean shouldRender;
	private FragType frag;
	private int Tickcount;
	private int XHandle;
	private int XEye;
	private int Y;
	private FontRenderer fr = mc.fontRendererObj;
	private ScaledResolution sr = new ScaledResolution(mc);

	public FragRunLootDetector() {
		this.name = "Frag run loot detector";
		this.moduleCategory = Category.DUNGEONS;
		this.description = "Sends a notification when you get a diamante handle or a LASR eye";
	}

	@Override
	public void onEnable() {
		this.toggled = true;
		MinecraftForge.EVENT_BUS.register(this);
		updateScreenCoords();
	}

	@Override
	public void onDisable() {
		this.toggled = false;
		MinecraftForge.EVENT_BUS.unregister(this);
	}

	@SubscribeEvent
	public void onItemPickup(EntityItemPickupEvent event) {
		EntityPlayer player = event.entityPlayer;
		ItemStack stack = event.item.getEntityItem();

		if (stack.getItem() == Items.diamond_horse_armor && stack.hasDisplayName()) {
			String displayName = stack.getDisplayName().toLowerCase();

			if (displayName.contains("diamante's handle".toLowerCase())) {
				if (Main.DebugMode) {
					System.out.println("Diamante's handle picked up");
				}
				frag = FragType.HANDLE;
				shouldRender = true;

				if (Main.DebugMode) {
					System.out.println("FRAG DETECTOR STATUS: shouldRender: " + shouldRender + ", fragtype: " + frag);
				}
			}
		} else {
			if (stack.getItem() == Items.ender_eye && stack.hasDisplayName()) {
				String displayName = stack.getDisplayName().toLowerCase();

				if (displayName.contains("l.a.s.r's eye".toLowerCase())) {
					if (Main.DebugMode) {
						System.out.println("L.A.S.R eye picked up");
					}
					frag = FragType.EYE;
					shouldRender = true;

					if (Main.DebugMode) {
						System.out
								.println("FRAG DETECTOR STATUS: shouldRender: " + shouldRender + ", fragtype: " + frag);
					}
				}
			}
		}
	}

	private void updateScreenCoords() {
		ScaledResolution sr = new ScaledResolution(mc);

		XHandle = (sr.getScaledWidth() / 2) - (fr.getStringWidth(textHandle) * scale / 2);
		XEye = (sr.getScaledWidth() / 2) - (fr.getStringWidth(textEye) * scale / 2);
		Y = 10;
	}

	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Post event) {
		if (mc.thePlayer == null || mc.theWorld == null)
			return;
		if (!shouldRender)
			return;

		if (shouldRender) {
			if (Tickcount < 1200) {
				if (frag == FragType.EYE) {
					GlStateManager.pushMatrix();
					GlStateManager.scale(scale, scale, scale);

					fr.drawStringWithShadow(textEye, XEye / scale, Y, 0xC41E12);

					GlStateManager.popMatrix();
					Tickcount++;
				} else {
					// For anyone wondering, the following enum check is unnecessary, but i future
					// proofed it in case the other frags are added
					if (frag == FragType.HANDLE) {
						GlStateManager.pushMatrix();
						GlStateManager.scale(scale, scale, scale);

						fr.drawStringWithShadow(textHandle, XHandle / scale, Y, 0xC41E12);

						GlStateManager.popMatrix();
						Tickcount++;
					}
				}
			} else {
				shouldRender = false;
				Tickcount = 0;
			}

		}

	}

}
