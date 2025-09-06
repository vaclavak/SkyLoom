package vpp.vac.skyquill.module.impl.misc;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vpp.vac.skyquill.main.Main;
import vpp.vac.skyquill.module.Category;
import vpp.vac.skyquill.module.Module;
import vpp.vac.skyquill.util.Logger;

public class BlackHoleReadyAlert extends Module {
	
	private int count = 0;
	private boolean shouldRender;
	private FontRenderer fr = mc.fontRendererObj;
	private int scale = 5;
	private int X;
	private int Y;
	private ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
	private String text = "BLACK HOLE READY";
	private EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
	private ArrayList<String> WatcherMessages = new ArrayList<>();
	
	
	
	public BlackHoleReadyAlert() {
		this.name = "Black Hole Ready Alert";
		this.description = "Displays an alert on your screen when your black hole is ready to be deployed";
		this.moduleCategory = Category.HUNTING;
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
			Logger log = new Logger(Main.PREFIX, "BLACK_HOLE_READY_ALERT");

			if (Main.DebugMode) {
				log.sendLog("Received message: " + message);
			}

			String[] lines = message.split("\n");
			for (String line : lines) {
				if (line.contains("You caught")) {
					shouldRender = true;
					if (Main.DebugMode) {
						log.sendLog("Black hole is ready" + count);
						System.out.println("shouldRender boolean status: " + shouldRender);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Text event) {
		if (!this.toggled || !shouldRender)
			return;

		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
		int screenWidth = sr.getScaledWidth();
		int screenHeight = sr.getScaledHeight();
		int textWidth = fr.getStringWidth(text);
		int textHeight = fr.FONT_HEIGHT;
		int scaledTextWidth = Math.round(textWidth * scale);
		int scaledTextHeight = Math.round(textHeight * scale);

		int posX = (screenWidth - scaledTextWidth) / 2;
		int posY = (screenHeight - scaledTextHeight) / 2;

		if (count < 40) {
			GlStateManager.pushMatrix();
			GlStateManager.scale(scale, scale, scale);
			fr.drawStringWithShadow(text, posX / scale, posY / scale - 5, 0x12de2d);
			GlStateManager.popMatrix();
			count++;
		} else {
			count = 0;
			shouldRender = false;
		}
	}

}
