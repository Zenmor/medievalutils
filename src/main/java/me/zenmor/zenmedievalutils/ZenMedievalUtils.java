package me.zenmor.zenmedievalutils;

import me.zenmor.zenmedievalutils.chat.Detection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZenMedievalUtils implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("modid");
    //public static final Identifier DREAMSPEEDRUN_ID = Identifier.of("zenmedievalutils", "dreamspeedrun");
    //public static SoundEvent DREAMSPEEDRUN_EVENT = SoundEvent.of(DREAMSPEEDRUN_ID);

    public static boolean iszentimerloaded() {
        if (FabricLoader.getInstance().isModLoaded("zentimer")) {
            LOGGER.info("zentimer detected1! bad");
            return true;
        }
        return false;
    }

    @Override
    public void onInitialize() {
        ClientReceiveMessageEvents.ALLOW_GAME.register(new Detection());
        //Registry.register(Registries.SOUND_EVENT, ZenMedievalUtils.DREAMSPEEDRUN_ID, DREAMSPEEDRUN_EVENT);

        boolean iszentimerloaded = iszentimerloaded();
        if (iszentimerloaded) {
            LOGGER.warn("zentimer bad, please rmeove aspa!! not compatible with this mdo");
        }
    }
}
