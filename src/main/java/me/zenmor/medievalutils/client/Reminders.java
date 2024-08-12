package me.zenmor.medievalutils.client;

import com.mojang.logging.LogUtils;
import me.zenmor.medievalutils.config.MedievalConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static me.zenmor.medievalutils.client.ClientChat.CONFIG;

public class Reminders implements ClientModInitializer {
    private static final ZoneId BESTTIMEZONE = ZoneId.of("America/Chicago");
    private static final LocalTime[] KOTHTIMES = {
            LocalTime.of(23, 45),
            LocalTime.of(2, 45),
            LocalTime.of(5, 45),
            LocalTime.of(8, 45),
            LocalTime.of(11, 45),
            LocalTime.of(14, 45),
            LocalTime.of(17, 45),
            LocalTime.of(20, 45)
    };
    private static final boolean[] REMINDERS_SENT = new boolean[KOTHTIMES.length];

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world != null && client.player != null) {
                ZonedDateTime currentTime = ZonedDateTime.now(BESTTIMEZONE);
                for (int i = 0; i < KOTHTIMES.length; i++) {
                    LocalTime targetTime = KOTHTIMES[i];
                    if (CONFIG.qol.kothreminder() && currentTime.getHour() == targetTime.getHour() && currentTime.getMinute() == targetTime.getMinute() && !REMINDERS_SENT[i] && MinecraftClient.getInstance().player != null) {
                        if (CONFIG.debugging()) {
                            LogUtils.getLogger().info("KOTH REMINDER BEING SENT!!!");
                        }

                        if (CONFIG.qol.kothremindertypes() == MedievalConfig.kothremindertypes.title) {
                            client.inGameHud.setTitle(Text.literal("REMINDER!!1").styled(style -> style.withColor(TextColor.fromRgb(CONFIG.modcolor().rgb()))));
                            client.inGameHud.setSubtitle(Text.literal("KOTH in 5 mins").styled(style -> style.withColor(TextColor.fromRgb(CONFIG.modcolor().rgb()))));
                        } else if (CONFIG.qol.kothremindertypes() == MedievalConfig.kothremindertypes.chat) {
                            client.player.sendMessage(Text.literal("REMIDER! koth in g5 mins").styled(style -> style.withColor(TextColor.fromRgb(CONFIG.modcolor().rgb()))), true);
                        } else if (CONFIG.qol.kothremindertypes() == MedievalConfig.kothremindertypes.toast) {
                            MinecraftClient.getInstance().getToastManager().add(new SystemToast(SystemToast.Type.PERIODIC_NOTIFICATION, Text.literal("REMINDER"), Text.literal("KOTH in 5 MINS")));
                        }

                        PositionedSoundInstance sound = PositionedSoundInstance.master(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f);
                        client.getSoundManager().play(sound);

                        REMINDERS_SENT[i] = true;
                    }
                }
            }
        });
    }
}