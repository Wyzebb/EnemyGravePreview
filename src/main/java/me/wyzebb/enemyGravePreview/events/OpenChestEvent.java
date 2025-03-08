package me.wyzebb.enemyGravePreview.events;

import de.jeff_media.angelchest.AngelChest;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static me.wyzebb.enemyGravePreview.EnemyGravePreview.plugin;

public class OpenChestEvent implements Listener {

    final File angelChestCfgFile = new File(Bukkit.getPluginManager().getPlugin("AngelChest").getDataFolder(), "config.yml");

    final FileConfiguration config = YamlConfiguration.loadConfiguration(angelChestCfgFile);

    @EventHandler
    public void onOpenChest(PlayerInteractEvent event) {
        Player chestOpener = event.getPlayer();

        if (event.getClickedBlock() == null) {
            return;
        }

        String mat = config.getString("material");

        if (Objects.equals(mat, "head:player")) {
            mat = "PLAYER_HEAD";
        } else {
            mat = mat.toUpperCase();
        }

        if (event.getClickedBlock().getType() != Material.valueOf(mat)) {
            return;
        }

        AngelChest chest = plugin.angelChestPlugin.getAngelChestAtBlock(event.getClickedBlock());

        if (chest == null) {
            return;
        }

        OfflinePlayer chestOwner = chest.getPlayer();

        if (chest.isProtected()) {
            if (!Objects.equals(chestOwner.getName(), chestOpener.getName())) {
                // The player is not the chest owner
                String invName = chestOwner.getName() + "'s Grave";

                List<ItemStack> itemStacks = new ArrayList<>();

                itemStacks.addAll(Arrays.asList(chest.getStorageInv()));

                Inventory inv = Bukkit.createInventory(chestOpener, 9 * 5, invName);

                for (ItemStack itemStack: itemStacks) {
                    if (itemStack != null) {
                        inv.addItem(itemStack);
                    }
                }

                for (int i = 1; i <= Arrays.asList(chest.getArmorInv()).toArray().length; i++) {
                    inv.setItem(40 - i, Arrays.asList(chest.getArmorInv()).get(i - 1));
                }

                inv.setItem(44, chest.getOffhandItem());

                chestOpener.openInventory(inv);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().contains("'s Grave")) {
            return;
        }

        event.setCancelled(true);
    }
}
