package de.saschat.descendre;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import org.lwjgl.glfw.GLFW;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class DescendreClient implements ClientModInitializer {
    public static KeyBinding dismount;

    @Override
    public void onInitializeClient() {
        dismount = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.descendre.dismount",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            "key.categories.movement"
        ));
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (dismount.wasPressed()) {
                if (client.player != null)
                    if (client.player.hasVehicle()) {
                        client.getNetworkHandler().sendPacket(new PlayerInputC2SPacket(client.player.sidewaysSpeed, client.player.forwardSpeed, client.player.input.jumping, true));
                        client.player.stopRiding();
                    }

            }
        });
    }
}
