package me.zenmor.medievalutils.client;

import me.zenmor.medievalutils.config.MedievalConfig;
import me.zenmor.medievalutils.mixin.FishingBobber;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

import static me.zenmor.medievalutils.client.ClientChat.CONFIG;

public class FishyAlert implements ClientTickEvents.StartTick {
    @Environment(EnvType.CLIENT)
    public void onStartTick(MinecraftClient client) {
        PlayerEntity player = client.player;
        if (player == null) return;
        if (player.getMainHandStack().getItem() != Items.FISHING_ROD) return;
        if (player.fishHook == null) return;

        FishingBobberEntity fishHook = player.fishHook;
        FishingBobber fishmixin = (FishingBobber) fishHook;

        if (fishmixin.getcaughtfish()) {
            if (client.interactionManager == null) return;

            if (player.getMainHandStack().getItem() == Items.FISHING_ROD && CONFIG.qol.fishalert() && (CONFIG.qol.fishalerttype() == MedievalConfig.fishalerttypes.simple)) {
                client.player.sendMessage(Text.literal("you rod caught something").styled(style -> style.withColor(TextColor.fromRgb(CONFIG.modcolor().rgb()))), true);
                PositionedSoundInstance sound = PositionedSoundInstance.master(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f);
                client.getSoundManager().play(sound);
            } else if (player.getMainHandStack().getItem() == Items.FISHING_ROD && CONFIG.qol.fishalert() && (CONFIG.qol.fishalerttype() == MedievalConfig.fishalerttypes.medium)) {
                client.player.sendMessage(Text.literal("you rod caught something").styled(style -> style.withColor(TextColor.fromRgb(CONFIG.modcolor().rgb()))), true);

                PositionedSoundInstance sound = PositionedSoundInstance.master(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f);
                client.getSoundManager().play(sound);
            } else if (player.getMainHandStack().getItem() == Items.FISHING_ROD && CONFIG.qol.fishalert() && (CONFIG.qol.fishalerttype() == MedievalConfig.fishalerttypes.annoying)) {
                client.player.sendMessage(Text.literal("YO YORU FIHSING ROD COUGAHT ISMOETHING GET IT NWO").styled(style -> style.withColor(TextColor.fromRgb(CONFIG.modcolor().rgb()))), true);

                client.inGameHud.setTitle(Text.literal("U CAUGHT SOMETHING").styled(style -> style.withColor(TextColor.fromRgb(CONFIG.modcolor().rgb()))));
                client.inGameHud.setSubtitle(Text.literal("CHECK FIHSING ROOD").styled(style -> style.withColor(TextColor.fromRgb(CONFIG.modcolor().rgb()))));
                MinecraftClient.getInstance().getToastManager().add(new SystemToast(SystemToast.Type.PERIODIC_NOTIFICATION, Text.literal("Y OU CAUGHT SOMETINHG"), Text.literal("CHEECK RIFING ROD")));

                PositionedSoundInstance sound1 = PositionedSoundInstance.master(SoundEvents.BLOCK_ANVIL_DESTROY, 1.0f, 1000000000.0f);
                client.getSoundManager().play(sound1);

                PositionedSoundInstance sound2 = PositionedSoundInstance.master(SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE, 1.0f, 1000000000.0f);
                client.getSoundManager().play(sound2);

                PositionedSoundInstance sound3 = PositionedSoundInstance.master(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, 1.0f, 1000000000.0f);
                client.getSoundManager().play(sound3);

                PositionedSoundInstance sound4 = PositionedSoundInstance.master(SoundEvents.ENTITY_VILLAGER_CELEBRATE, 0.0f, 1000000000.0f);
                client.getSoundManager().play(sound4);

                PositionedSoundInstance sound5 = PositionedSoundInstance.master(SoundEvents.ITEM_GOAT_HORN_PLAY, 1.0f, 1000000000.0f);
                client.getSoundManager().play(sound5);

                PositionedSoundInstance sound6 = PositionedSoundInstance.master(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1000000000.0f);
                client.getSoundManager().play(sound6);

                PositionedSoundInstance sound7 = PositionedSoundInstance.master(SoundEvents.ENTITY_BEE_LOOP, 0.5f, 1000000000.0f);
                client.getSoundManager().play(sound7);
            }
        }
    }
}
