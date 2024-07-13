package me.zenmor.zenmedievalutils.chat;

import com.mojang.logging.LogUtils;
import me.zenmor.zenmedievalutils.config.MedievalUtilsConfig;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.regex.Pattern;

public class Detection implements ClientReceiveMessageEvents.AllowGame {

    @Override
    public boolean allowReceiveGameMessage(Text message, boolean overlay) {
        if (MedievalUtilsConfig.getInstance().iswelcomeenabled() && Pattern.compile(Pattern.quote("Welcome ") + "[^ ]*" + Pattern.quote(" to the MedievalMC Realm!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()) {
            LogUtils.getLogger().info("detected welcome message");

            assert MinecraftClient.getInstance().player != null;
            MinecraftClient.getInstance().player.networkHandler.sendChatMessage("welcome");
            return true;

        } else if (MedievalUtilsConfig.getInstance().isstfurewardsenabled() && (Pattern.compile(Pattern.quote("Dungeons » These items will be added to your rewards inventory! You'll get them when you finish the dungeon."), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())) {
            LogUtils.getLogger().info("blocked rewards messages");
            return false;
        } else if (MedievalUtilsConfig.getInstance().isstfurewardsenabled() && (Pattern.compile(Pattern.quote("Dungeons » View your rewards with /rewards"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())) {
            LogUtils.getLogger().info("blocked rewards messages");
            return false;
        } else if (MedievalUtilsConfig.getInstance().isstfurewardsenabled() && (Pattern.compile(Pattern.quote("Dungeons » The rewards you didn't take were added to your rewards inventory."), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())) {
            LogUtils.getLogger().info("blocked rewards messages");
            return false;
        } else {
            return true;
        }
    }
}