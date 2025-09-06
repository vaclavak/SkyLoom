package vpp.vac.skyquill.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import vpp.vac.skyquill.module.impl.render.*;

public class Keybinds {
    private static final KeyBinding clickGuiKey = new KeyBinding("Skyquill GUI", Keyboard.KEY_RSHIFT, "Skyquill");

    public Keybinds() {
        ClientRegistry.registerKeyBinding(clickGuiKey);
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (clickGuiKey.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new ClickGUI());
        }
    }
}
