package vpp.vac.skyquill.module.impl.trading;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import vpp.vac.skyquill.module.Category;
import vpp.vac.skyquill.module.Module;
import vpp.vac.skyquill.util.ContainerChecker;

public class AntiOverfluxScam extends Module {

	private static final String text = "SCAM";

	private final Minecraft mc = Minecraft.getMinecraft();
	private final FontRenderer fr = mc.fontRendererObj;
	private int X;
	private int Y;
	private final int scale = 5;
	private int count = 0;
	private boolean shouldRender = false;

	public AntiOverfluxScam() {
		this.name = "AntiScam - Overflux";
		this.description = "ATTEMPTS to prevent the overflux scam, where the scammer replaces the overflux core with a crystal fragment";
		this.moduleCategory = Category.TRADING;
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
		shouldRender = false;
		count = 0;
	}

	private void updateScreenCoords() {
		ScaledResolution sr = new ScaledResolution(mc);

		X = (sr.getScaledWidth() / 2) - (fr.getStringWidth(text) * scale / 2);
		Y = 10;
	}

	
	//For anyone viewing this code wondering why its being rendered only every 2 ticks, this was an attempt of optimalization, but it doesnt really make any difference
	@SubscribeEvent
	public void onTick(TickEvent.PlayerTickEvent event) {
		if (!toggled || event.phase != TickEvent.Phase.END)
			return;

		
		if (ContainerChecker.containsCrystalFragment()) {
			count++;
			if (count >= 2) {
				shouldRender = true;
				count = 0;
			}
		} else {
			shouldRender = false;
			count = 0;
		}
	}

	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Post event) {
		if (!shouldRender)
			return;

		if (mc.currentScreen instanceof GuiChest) {

			GlStateManager.pushMatrix();
			GlStateManager.scale(scale, scale, scale);

			fr.drawString(text, X / scale, Y, 0xC41E12);

			GlStateManager.popMatrix();
		}
	}

}
