package me.wyzebb.enemyGravePreview;

import de.jeff_media.angelchest.AngelChestPlugin;
import me.wyzebb.enemyGravePreview.events.OpenChestEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class EnemyGravePreview extends JavaPlugin {
    public AngelChestPlugin angelChestPlugin = (AngelChestPlugin) getServer().getPluginManager().getPlugin("AngelChest");

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new OpenChestEvent(), this);
    }
}
