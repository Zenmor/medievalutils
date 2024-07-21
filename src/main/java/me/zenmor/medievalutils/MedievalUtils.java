package me.zenmor.medievalutils;

import me.zenmor.medievalutils.client.ClientChat;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;

public class MedievalUtils implements ModInitializer {
    @Override
    public void onInitialize() {
        ClientReceiveMessageEvents.ALLOW_GAME.register(new ClientChat());
    }
}