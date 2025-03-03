package me.wyzebb.enemyGravePreview.events;

import de.jeff_media.angelchest.AngelChest;
import de.jeff_media.angelchest.events.AngelChestOpenEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OpenChestEvent implements Listener {
    private String invName = "Player's Grave";

    @EventHandler
    public void onOpenChest(AngelChestOpenEvent event) {
        AngelChest chest = event.getAngelChest();
        OfflinePlayer chestOwner = chest.getPlayer();
        OfflinePlayer chestOpener = event.getPlayer();

        if (chest.isProtected()) {
            if (chestOwner != chestOpener) {
                // ENEMY
                invName = chestOwner.getName() + "'s Grave";
                Player player = (Player) chestOpener;

                List<ItemStack> itemStacks = new ArrayList<>();

                itemStacks.addAll(Arrays.asList(chest.getArmorInv()));
                itemStacks.add(chest.getOffhandItem());
                itemStacks.addAll(Arrays.asList(chest.getStorageInv()));

                Inventory inv = Bukkit.createInventory(player, (9 * 3) + 5, invName);

                for (ItemStack itemStack: itemStacks) {
                    inv.addItem(itemStack);
                }

                player.openInventory(inv);
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
