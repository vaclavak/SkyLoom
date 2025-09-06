package vpp.vac.skyquill.module.impl.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import vpp.vac.skyquill.module.Category;
import vpp.vac.skyquill.module.Module;
import vpp.vac.skyquill.module.ModuleManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClickGUI extends GuiScreen {

	private static final Minecraft mc = Minecraft.getMinecraft();
	private final List<Category> categories = new ArrayList<>();
	private final Map<Module, Float> toggleAnimations = new HashMap<>();
	private Category selectedCategory;
	private String searchQuery = "";

	public ClickGUI() {
		for (Category cat : Category.values()) {
			categories.add(cat);
		}
		if (!categories.isEmpty()) {
			selectedCategory = categories.get(0);
		}
		for (Module m : ModuleManager.modules) {
			toggleAnimations.put(m, m.isToggled() ? 1f : 0f);
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();

		int sidebarWidth = 100;
		Gui.drawRect(0, 0, sidebarWidth, height, 0xFF1E1E1E);

		int yOffset = 20;
		for (Category cat : categories) {
			boolean hovered = mouseX < sidebarWidth && mouseY > yOffset && mouseY < yOffset + 20;
			int bgColor = cat == selectedCategory ? 0xFF3333AA : hovered ? 0xFF2A2A2A : 0xFF1E1E1E;
			Gui.drawRect(0, yOffset, sidebarWidth, yOffset + 20, bgColor);
			drawCenteredString(mc.fontRendererObj, cat.name(), sidebarWidth / 2, yOffset + 6, 0xFFFFFFFF);
			yOffset += 22;
		}

		int searchBarX = sidebarWidth + 10;
		int searchBarY = 10;
		int searchBarWidth = width - sidebarWidth - 20;
		int searchBarHeight = 15;
		Gui.drawRect(searchBarX, searchBarY, searchBarX + searchBarWidth, searchBarY + searchBarHeight, 0xFF2A2A2A);
		drawString(mc.fontRendererObj, searchQuery.isEmpty() ? "Search..." : searchQuery, searchBarX + 5,
				searchBarY + 4, 0xFFAAAAAA);

		int moduleY = searchBarY + searchBarHeight + 10;
		for (Module module : ModuleManager.modules) {
			if (module.getCategory() == selectedCategory
					&& (searchQuery.isEmpty() || module.getName().toLowerCase().contains(searchQuery.toLowerCase()))) {

				float anim = toggleAnimations.getOrDefault(module, 0f);
				float target = module.isToggled() ? 1f : 0f;
				anim += (target - anim) * 0.2f;
				toggleAnimations.put(module, anim);

				Gui.drawRect(searchBarX, moduleY, searchBarX + searchBarWidth, moduleY + 25, 0xFF1E1E1E);
				drawString(mc.fontRendererObj, module.getName(), searchBarX + 5, moduleY + 4, 0xFFFFFFFF);
				drawString(mc.fontRendererObj, module.getDescription(), searchBarX + 5, moduleY + 14, 0xFFAAAAAA);

				int toggleWidth = 30;
				int toggleHeight = 12;
				int toggleX = searchBarX + searchBarWidth - toggleWidth - 10;
				int toggleY = moduleY + 6;

				int bgCol = 0xFF555555 + (int) ((0xFF4CAF50 - 0xFF555555) * anim); // color lerp
				Gui.drawRect(toggleX, toggleY, toggleX + toggleWidth, toggleY + toggleHeight,
						module.isToggled() ? 0xFF4CAF50 : 0xFF555555);

				int knobSize = toggleHeight - 2;
				int knobX = (int) (toggleX + 1 + anim * (toggleWidth - knobSize - 2));
				Gui.drawRect(knobX, toggleY + 1, knobX + knobSize, toggleY + 1 + knobSize, 0xFFFFFFFF);

				moduleY += 27;
			}
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		int sidebarWidth = 100;

		int yOffset = 20;
		for (Category cat : categories) {
			if (mouseX < sidebarWidth && mouseY > yOffset && mouseY < yOffset + 20) {
				selectedCategory = cat;
				return;
			}
			yOffset += 22;
		}

		int searchBarX = sidebarWidth + 10;
		int searchBarY = 10;
		int searchBarWidth = width - sidebarWidth - 20;
		int searchBarHeight = 15;
		int moduleY = searchBarY + searchBarHeight + 10;

		for (Module module : ModuleManager.modules) {
			if (module.getCategory() == selectedCategory
					&& (searchQuery.isEmpty() || module.getName().toLowerCase().contains(searchQuery.toLowerCase()))) {

				int toggleWidth = 30;
				int toggleHeight = 12;
				int toggleX = searchBarX + searchBarWidth - toggleWidth - 10;
				int toggleY = moduleY + 6;

				if (mouseX >= toggleX && mouseX <= toggleX + toggleWidth && mouseY >= toggleY
						&& mouseY <= toggleY + toggleHeight) {
					if (module.isToggled()) {
						module.onDisable();
					} else {
						module.onEnable();
					}
				}
				moduleY += 27;
			}
		}

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == Keyboard.KEY_BACK) {
			mc.displayGuiScreen(null);
			return;
		}
		if (Character.isLetterOrDigit(typedChar) || Character.isSpaceChar(typedChar)) {
			searchQuery += typedChar;
		} else if (keyCode == Keyboard.KEY_BACK && !searchQuery.isEmpty()) {
			searchQuery = searchQuery.substring(0, searchQuery.length() - 1);
		}
		super.keyTyped(typedChar, keyCode);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
