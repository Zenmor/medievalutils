package me.zenmor.zenmedievalutils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.mojang.logging.LogUtils;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;

public final class MedievalUtilsConfig {
    private static final String FILE_NAME = "zenmedievalutils.json";
    private static final Gson GSON = new GsonBuilder().setLenient().setPrettyPrinting().create();
    private static MedievalUtilsConfig instance;

    static final boolean defaultAUtoClose = false;
    static final boolean defaultWelcome = false;
    static final boolean defaultSTFUrewards = false;

    boolean aUtoClose = defaultAUtoClose;
    boolean welcome = defaultWelcome;
    boolean stfurewards = defaultSTFUrewards;

    private MedievalUtilsConfig() {
    }

    public static MedievalUtilsConfig getInstance() {
        return instance;
    }

    public static void load() {
        try {
            instance = GSON.fromJson(
                    Files.readString(FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME)),
                    MedievalUtilsConfig.class);
        } catch (IOException | JsonSyntaxException ex) {
            LogUtils.getLogger().warn("could not load the config", ex);
            instance = new MedievalUtilsConfig();
            instance.save();
        }
    }

    public void save() {
        try {
            Files.writeString(FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME), GSON.toJson(this));
        } catch (IOException ex) {
            LogUtils.getLogger().error("could not save the config", ex);
        }
    }

    public boolean isaUtoClose() {
        return aUtoClose;
    }

    public boolean iswelcomeenabled() {
        return welcome;
    }

    public boolean isstfurewardsenabled() {
        return stfurewards;
    }
}