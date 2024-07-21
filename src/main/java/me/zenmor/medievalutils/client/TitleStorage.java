package me.zenmor.medievalutils.client;

import net.minecraft.text.Text;

public class TitleStorage {

    private static Text currenttitle;

    public static void setCurrentTitle(Text title) {
        currenttitle = title;
    }

    public static Text getCurrentTitle() {
        return currenttitle;
    }
}
