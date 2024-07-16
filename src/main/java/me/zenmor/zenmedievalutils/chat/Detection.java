package me.zenmor.zenmedievalutils.chat;

import com.mojang.logging.LogUtils;
import me.zenmor.zenmedievalutils.config.MedievalUtilsConfig;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.regex.Pattern;

public class Detection implements ClientReceiveMessageEvents.AllowGame {

    private final Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> welcomeTask;

    @Override
    public boolean allowReceiveGameMessage(Text message, boolean overlay) {
        if (MedievalUtilsConfig.getInstance().iswelcomeenabled() && Pattern.compile(Pattern.quote("Welcome ") + "[^ ]*" + Pattern.quote(" to the MedievalMC Realm!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()) {
            LogUtils.getLogger().info("sent welcome message");
            if (welcomeTask != null) {
                welcomeTask.cancel(true);
            }
            welcomeTask = scheduler.schedule(() -> {
                assert MinecraftClient.getInstance().player != null;
                MinecraftClient.getInstance().player.networkHandler.sendChatMessage("welcome");
            }, MedievalUtilsConfig.getInstance().getwelcomedelay() * 1000, TimeUnit.MILLISECONDS);
            return true;

        } else if (MedievalUtilsConfig.getInstance().isstfurewardsenabled() && (Pattern.compile(Pattern.quote("Dungeons » These items will be added to your rewards inventory! You'll get them when you finish the dungeon."), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())
                || MedievalUtilsConfig.getInstance().isstfurewardsenabled() && (Pattern.compile(Pattern.quote("Dungeons » The rewards you didn't take were added to your rewards inventory."), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())
                || MedievalUtilsConfig.getInstance().isstfurewardsenabled() && (Pattern.compile(Pattern.quote("Dungeons » View your rewards with /rewards"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())) {
            LogUtils.getLogger().info("blocked rewards messages");
            return false;
        } else if
        (MedievalUtilsConfig.getInstance().isstfutipsenabled() && (Pattern.compile(Pattern.quote("+--------------------------------------------------+"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())
                        || MedievalUtilsConfig.getInstance().isstfutipsenabled() && (Pattern.compile(Pattern.quote("MedievalMC: Confused on how to get started? Check out /help for a guide through most of our features!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())
                        || MedievalUtilsConfig.getInstance().isstfutipsenabled() && (Pattern.compile(Pattern.quote("MedievalMC: Want to obtain some amazing items? /warp darkzone but be aware of the challenge that awaits!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())
                        || MedievalUtilsConfig.getInstance().isstfutipsenabled() && (Pattern.compile(Pattern.quote("MedievalMC: Make sure you remember to claim your /daily each time you login!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())
                        || MedievalUtilsConfig.getInstance().isstfutipsenabled() && (Pattern.compile(Pattern.quote("MedievalMC: Looking for massive upgrades and unique items? Then look no further than /blackmarket!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())
                        || MedievalUtilsConfig.getInstance().isstfutipsenabled() && (Pattern.compile(Pattern.quote("MedievalMC: Think you are ready to battle the elements? Well if so check out /bosses and test yourself!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())
                        || MedievalUtilsConfig.getInstance().isstfutipsenabled() && (Pattern.compile(Pattern.quote("Votes » Don't forget to vote for our server!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())) {
            //i swear they made these tips with chatgpt lmao
            LogUtils.getLogger().info("blocked tip messages");
            return false;
        } else if (MedievalUtilsConfig.getInstance().isautoreadyenabled() && (Pattern.compile(Pattern.quote("Dungeons » Dungeon 'sunken' is now available"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())) {
            MinecraftClient.getInstance().player.networkHandler.sendChatCommand("ready");
            LogUtils.getLogger().info("auto dungeon readyied");
            return true;
        } else if (MedievalUtilsConfig.getInstance().isautoleaveenabled() && (Pattern.compile(Pattern.quote("Events » You are now a spectator. Use /event leave to leave spectator mode"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())) {
            MinecraftClient.getInstance().player.networkHandler.sendChatCommand("event leave");
            LogUtils.getLogger().info("left event auto");
            return true;
        } else if (MedievalUtilsConfig.getInstance().isstfuvotesenabled() && Pattern.compile(Pattern.quote("MedievalMC » ") + "[^ ]*" + Pattern.quote(" has voted!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find() || MedievalUtilsConfig.getInstance().isstfuvotesenabled() && Pattern.compile(Pattern.quote("MedievalMC » Party will start soon"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()) {
            LogUtils.getLogger().info("blocked vote message");
            return false;
        } else if (MedievalUtilsConfig.getInstance().isstfuskipnightenabled() && Pattern.compile(Pattern.quote("[Vote] ") + "[^ ]*" + Pattern.quote(" has started a vote to skip the night!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find() || MedievalUtilsConfig.getInstance().isstfuskipnightenabled() && Pattern.compile(Pattern.quote("Please vote: [Yes] [No]"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find() || MedievalUtilsConfig.getInstance().isstfuskipnightenabled() && Pattern.compile(Pattern.quote("[Vote] 10 seconds let to vote!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find() || MedievalUtilsConfig.getInstance().isstfuskipnightenabled() && Pattern.compile(Pattern.quote("[Vote] Vote passed! Skipping the night."), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()) {
            LogUtils.getLogger().info("blocked skipnight message");
            return false;
        } else if (MedievalUtilsConfig.getInstance().isstfudailyenabled() && Pattern.compile(Pattern.quote("MedievalMC » ") + "[^ ]*" + Pattern.quote(" has claimed their daily tribute with /daily!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()) {
            LogUtils.getLogger().info("blocked daily message");
            return false;
        } else if (MedievalUtilsConfig.getInstance().isstfuworldguardenabled() && Pattern.compile(Pattern.quote("Hey! Sorry, but you can't"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()) {
            LogUtils.getLogger().info("blocked worldguard message");
            return false;
        } else if (MedievalUtilsConfig.getInstance().isstfuryanenabled() && Pattern.compile(Pattern.quote("Lordrj2 » "), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()) {
            MinecraftClient.getInstance().player.sendMessage(Text.literal("message from ryan removed").styled(style -> style.withColor(TextColor.fromRgb(0xA4BEF3))), false);
            return false;
        } else if (MedievalUtilsConfig.getInstance().isskiphubenabled() && Pattern.compile(Pattern.quote("Join a realm by clicking on your"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()) {
            LogUtils.getLogger().info("skipped hub");

            assert MinecraftClient.getInstance().player != null;
            MinecraftClient.getInstance().player.networkHandler.sendCommand("server survival");
            return true;
        } else if (MedievalUtilsConfig.getInstance().isautoskipnightenabled() && Pattern.compile(Pattern.quote("[Vote] ") + "[^ ]*" + Pattern.quote(" has started a vote to skip the night!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()) {
            if (MedievalUtilsConfig.getInstance().isstfuskipnightenabled()) {
                LogUtils.getLogger().info("blocked skipnight message & voted");
                MinecraftClient.getInstance().player.networkHandler.sendCommand("skipnight yes");
                return false;
            } else {
                LogUtils.getLogger().info("voted on skipnight message");
                MinecraftClient.getInstance().player.networkHandler.sendCommand("skipnight yes");
                return true;
            }
        } else if (MedievalUtilsConfig.getInstance().isautoclaimdailyenabled() && Pattern.compile(Pattern.quote("Daily » You have a Daily Reward waiting to be claimed!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()) {
            LogUtils.getLogger().info("auto claime daily");
            MinecraftClient.getInstance().player.networkHandler.sendCommand("daily claim");

            return true;
        } else {
            return true;
        }
    }
}