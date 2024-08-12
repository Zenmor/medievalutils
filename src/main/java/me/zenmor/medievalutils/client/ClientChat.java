package me.zenmor.medievalutils.client;

import com.mojang.logging.LogUtils;
import me.zenmor.medievalutils.config.MyConfig;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.regex.Pattern;

public class ClientChat implements ClientReceiveMessageEvents.AllowGame {
    public static final MyConfig CONFIG = MyConfig.createAndLoad();
    private final Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> welcomeTask;

    public ClientChat() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (welcomeTask != null) {
                welcomeTask.cancel(true);
            }
            scheduler.shutdown();
        }));
    }

    @Override
    public boolean allowReceiveGameMessage(Text message, boolean overlay) {
        String serverip = getserverip();
        if (serverip == null) return true;

        if (serverip.equalsIgnoreCase("localhost:25569") || serverip.equalsIgnoreCase("play.medievalmc.co") || serverip.equalsIgnoreCase("play.huabacraft.com") || serverip.equalsIgnoreCase("medievalmc.co")) {
            if (CONFIG.stfu.stfudaily() && Pattern.compile(Pattern.quote("MedievalMC » ") + "[^ ]*" + Pattern.quote(" has claimed their daily tribute with /daily!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()) {
                if (CONFIG.debugging()) {
                    LogUtils.getLogger().info("blocked daily message");
                }
                return false;
            } else if (CONFIG.stfu.stfufish() && (Pattern.compile(Pattern.quote("The Silver Hunt has begun!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())
                    || (CONFIG.stfu.stfufish() && (Pattern.compile(Pattern.quote("Fishing Tournament"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()))
                    || (CONFIG.stfu.stfufish() && (Pattern.compile(Pattern.quote("Catch 3 Silver Fish as quickly as possible!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()))
                    || (CONFIG.stfu.stfufish() && (Pattern.compile(Pattern.quote("Catch The Most Bronze Fish as possible!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()))
                    || (CONFIG.stfu.stfufish() && (Pattern.compile(Pattern.quote("Catch 3 Gold Fish as quickly as possible!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()))
                    || (CONFIG.stfu.stfufish() && (Pattern.compile(Pattern.quote("Rewards:"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()))
                    || (CONFIG.stfu.stfufish() && (Pattern.compile(Pattern.quote("1 - Medieval Key"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()))
                    || (CONFIG.stfu.stfufish() && (Pattern.compile(Pattern.quote("2-10 - Universal Key"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()))
            ) {
                if (CONFIG.debugging()) {
                    LogUtils.getLogger().info("blocked fish message");
                }
                return false;
            } else if (CONFIG.stfu.stfufish() && (Pattern.compile(Pattern.quote("Dungeons » These items will be added to your rewards inventory! You'll get them when you finish the dungeon."), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())
                    || (CONFIG.stfu.stfufish() && (Pattern.compile(Pattern.quote("Dungeons » The rewards you didn't take were added to your rewards inventory."), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()))
                    || (CONFIG.stfu.stfufish() && (Pattern.compile(Pattern.quote("Dungeons » View your rewards with /rewards"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()))
            ) {
                if (CONFIG.debugging()) {
                    LogUtils.getLogger().info("blocked reward message");
                }
                return false;
            } else if (CONFIG.stfu.stfuskipnight() && Pattern.compile(Pattern.quote("[Vote] ") + "[^ ]*" + Pattern.quote(" has started a vote to skip the night!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()) {
                if (CONFIG.qol.autoskipnight() && CONFIG.stfu.stfuskipnight() && MinecraftClient.getInstance().player != null) {
                    MinecraftClient.getInstance().player.networkHandler.sendCommand("skipnight yes");
                    return false;
                }
                if (CONFIG.debugging()) {
                    LogUtils.getLogger().info("blocked skipnight message");
                }
                return false;

            } else if (!CONFIG.stfu.stfuskipnight() && CONFIG.qol.autoskipnight() && Pattern.compile(Pattern.quote("[Vote] ") + "[^ ]*" + Pattern.quote(" has started a vote to skip the night!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()) {
                assert MinecraftClient.getInstance().player != null;
                MinecraftClient.getInstance().player.networkHandler.sendCommand("skipnight yes");
                if (CONFIG.debugging()) {
                    LogUtils.getLogger().info("voted on message");
                }
                return true;
                //block their chatgpt tip messages that are annoying asf
            } else if (CONFIG.stfu.stfutips() && (Pattern.compile(Pattern.quote("+--------------------------------------------------+"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())
                    || CONFIG.stfu.stfutips() && (Pattern.compile(Pattern.quote("MedievalMC: Confused on how to get started? Check out /help for a guide through most of our features!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())
                    || CONFIG.stfu.stfutips() && (Pattern.compile(Pattern.quote("MedievalMC: Want to obtain some amazing items? /warp darkzone but be aware of the challenge that awaits!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())
                    || CONFIG.stfu.stfutips() && (Pattern.compile(Pattern.quote("MedievalMC: Make sure you remember to claim your /daily each time you login!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())
                    || CONFIG.stfu.stfutips() && (Pattern.compile(Pattern.quote("MedievalMC: Looking for massive upgrades and unique items? Then look no further than /blackmarket!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())
                    || CONFIG.stfu.stfutips() && (Pattern.compile(Pattern.quote("MedievalMC: Think you are ready to battle the elements? Well if so check out /bosses and test yourself!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())
                    || CONFIG.stfu.stfutips() && (Pattern.compile(Pattern.quote("Votes » Don't forget to vote for our server!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())
            ) {
                if (CONFIG.debugging()) {
                    LogUtils.getLogger().info("blocked tip message");
                }
                return false;
            } else if (CONFIG.stfu.stfutrapped() && (Pattern.compile(Pattern.quote("Are you trapped in someone's land claim?"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find())) {
                if (CONFIG.debugging()) {
                    LogUtils.getLogger().info("blocked trapped message");
                }
                return false;
            } else if (CONFIG.stfu.stfuvotes() && Pattern.compile(Pattern.quote("MedievalMC » ") + "[^ ]*" + Pattern.quote(" has voted!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()
                    || CONFIG.stfu.stfuvotes() && Pattern.compile(Pattern.quote("MedievalMC » Party will start soon"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()
                    || CONFIG.stfu.stfuvotes() && Pattern.compile(Pattern.quote("MedievalMC » ") + "[^ ]*" + Pattern.quote(" Has just received an additional x3 Vote Keys from voting!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()
                    || CONFIG.stfu.stfuvotes() && Pattern.compile(Pattern.quote("MedievalMC » ") + "[^ ]*" + Pattern.quote(" Has just received a Medieval Key from voting!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()
            ) {
                if (CONFIG.debugging()) {
                    LogUtils.getLogger().info("blocked vote message");
                }
                return false;
            } else if (CONFIG.stfu.stfuworldguard() && Pattern.compile(Pattern.quote("Hey! Sorry, but you can't"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()) {
                if (CONFIG.debugging()) {
                    LogUtils.getLogger().info("blocked worldguard message");
                }
                return false;
            } else if (Pattern.compile(Pattern.quote("Pocket Pets » You cannot level up this pet for another 20m!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()) {
                if (CONFIG.debugging()) {
                    LogUtils.getLogger().info("detected pet message");
                }

                if (welcomeTask != null) {
                    welcomeTask.cancel(true);
                }

                welcomeTask = scheduler.schedule(() -> {
                    if (MinecraftClient.getInstance().player != null) {
                        MinecraftClient.getInstance().player.sendMessage(Text.literal("you can use your pet with 20m cooldown again").styled(style -> style.withColor(TextColor.fromRgb(0xA4BEF3))), false);
                    }
                }, 20 * 10000L, TimeUnit.MILLISECONDS);
                return true;
            } else if (CONFIG.qol.autowelcome() && Pattern.compile(Pattern.quote("Welcome ") + "[^ ]*" + Pattern.quote(" to the MedievalMC Realm!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()) {
                if (CONFIG.debugging()) {
                    LogUtils.getLogger().info("detected welcome message");
                }

                if (welcomeTask != null) {
                    welcomeTask.cancel(true);
                }

                welcomeTask = scheduler.schedule(() -> {
                    if (MinecraftClient.getInstance().player != null) {
                        MinecraftClient.getInstance().player.networkHandler.sendChatMessage("welcome");
                    }
                }, CONFIG.qol.autowelcomedelay() * 1000L, TimeUnit.MILLISECONDS);
                return true;
            } else if (CONFIG.qol.autoclaimdaily() && Pattern.compile(Pattern.quote("Daily » You have a Daily Reward waiting to be claimed!"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()) {
                if (CONFIG.debugging()) {
                    LogUtils.getLogger().info("detected claim message");
                }
                assert MinecraftClient.getInstance().player != null;
                MinecraftClient.getInstance().player.networkHandler.sendChatCommand("daily");
            } else if (CONFIG.stfu.stfuryan() && Pattern.compile(Pattern.quote("Lordrj2 » "), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()) {
                if (CONFIG.debugging()) {
                    LogUtils.getLogger().info("detected ryan message, blocking it");
                }
                if (MinecraftClient.getInstance().player != null) {
                    MinecraftClient.getInstance().player.sendMessage(Text.literal("message from ryan removed").styled(style -> style.withColor(TextColor.fromRgb(0xA4BEF3))), false);
                }
                return false;
            } else if (CONFIG.qol.skiphub() && Pattern.compile(Pattern.quote("Join a realm by clicking on your"), Pattern.CASE_INSENSITIVE).matcher(message.getString()).find()) {
                if (CONFIG.debugging()) {
                    LogUtils.getLogger().info("detected hub message, skiipping hub");
                }
                if (MinecraftClient.getInstance().player != null) {
                    MinecraftClient.getInstance().player.networkHandler.sendCommand("server survival");
                }

                return true;
            }
        } else {
            return true;
        }
        return true;
    }

    private String getserverip() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null && client.getCurrentServerEntry() != null) {
            return client.getCurrentServerEntry().address;
        }
        return null;
    }
}
