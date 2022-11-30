package kr.elroy.guishop.listener;

import kr.elroy.guishop.menu.ShopUserMenu;
import kr.elroy.guishop.shop.Shop;
import kr.elroy.guishop.shop.ShopManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.mineacademy.fo.model.HookManager;

public class NpcInteractHandler implements Listener {
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() == null) {
            return;
        }

        if (!HookManager.isNPC(event.getRightClicked())) {
            return;
        }

        Entity entity = event.getRightClicked();
        String name = ChatColor.stripColor(entity.getName());

        if (!name.contains("[상점]")) {
            return;
        }

        name = name.replace("[상점] ", "");

        if (!ShopManager.getInstance().isExistShopName(name)) {
            return;
        }

        Shop shop = ShopManager.getInstance().getShopByName(name);

        new ShopUserMenu(shop).displayTo(event.getPlayer());
    }
}