package me.wyzebb.enemyGravePreview.events;

import de.jeff_media.angelchest.AngelChest;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static me.wyzebb.enemyGravePreview.EnemyGravePreview.plugin;

public class OpenChestEvent implements Listener {

    @EventHandler
    public void onOpenChest(PlayerInteractEvent event) {
        Player chestOpener = event.getPlayer();

        if (event.getClickedBlock() == null) {
            return;
        }

        if (event.getClickedBlock().getType() != Material.CHEST) {
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

                itemStacks.addAll(Arrays.asList(chest.getArmorInv()));
                itemStacks.add(chest.getOffhandItem());
                itemStacks.addAll(Arrays.asList(chest.getStorageInv()));

                Inventory inv = Bukkit.createInventory(chestOpener, 9 * 4, invName);

                for (ItemStack itemStack: itemStacks) {
                    if (itemStack != null) {
                        inv.addItem(itemStack);
                    }
                }

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
