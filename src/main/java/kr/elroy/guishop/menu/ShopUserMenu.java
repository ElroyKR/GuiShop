package kr.elroy.guishop.menu;

import kr.elroy.guishop.Settings;
import kr.elroy.guishop.shop.Shop;
import kr.elroy.guishop.shop.ShopItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.model.HookManager;
import org.mineacademy.fo.model.Replacer;

import java.util.List;

public class ShopUserMenu extends MenuPagged<ShopItem> {
    public ShopUserMenu(Shop shop) {
        super(shop.getShopItems());
        setSize(54);
        this.setTitle("&0&l" + shop.getShopName());
    }

    @Override
    protected ItemStack convertToItemStack(ShopItem shopItem) {
        List<String> lore = Settings.Menu.PRODUCT_ITEM_LORE;
        lore = Replacer.replaceArray(lore, "buy_price", String.valueOf(shopItem.getBuyPrice()), "sell_price", String.valueOf(shopItem.getSellPrice()));

        return ItemCreator.of(shopItem.getItemStack()).lore(lore).makeMenuTool();
    }

    @Override
    protected void onPageClick(Player player, ShopItem shopItem, ClickType clickType) {
        if (shopItem.getBuyPrice() == 0) {
            return;
        }

        if (clickType.equals(ClickType.LEFT)) {
            buyItem(player, shopItem, 1);
            return;
        }

        if (clickType.equals(ClickType.SHIFT_LEFT)) {
            buyItem(player, shopItem, shopItem.getItemStack().getMaxStackSize());
            return;
        }

        if (clickType.equals(ClickType.RIGHT)) {
            sellItem(player, shopItem, 1);
            return;
        }

        if (clickType.equals(ClickType.SHIFT_RIGHT)) {
            int amount = getAmount(player, shopItem.getItemStack());
            sellItem(player, shopItem, amount);
            return;
        }
    }

    private void buyItem(Player player, ShopItem shopItem, int amount) {
        final double balance = HookManager.getBalance(player);
        final int price = shopItem.getBuyPrice() * amount;
        final ItemStack itemStack = shopItem.getItemStack().clone();

        itemStack.setAmount(amount);

        if (balance < price) {
            Common.tellNoPrefix(player, Settings.Messages.INSUFFICIENT_BALANCE);
            return;
        }

        HookManager.withdraw(player, price);
        player.getInventory().addItem(itemStack);
        Common.tellNoPrefix(player, Settings.Messages.BUY_SUCCESS);
    }

    private void sellItem(Player player, ShopItem shopItem, int amount) {
        final int price = shopItem.getSellPrice() * amount;
        final ItemStack itemStack = shopItem.getItemStack().clone();
        itemStack.setAmount(amount);

        if (amount == 0 || getAmount(player, shopItem.getItemStack()) < amount) {
            Common.tellNoPrefix(player, Settings.Messages.INSUFFICIENT_ITEM_TO_SELL);
            return;
        }

        HookManager.deposit(player, price);
        player.getInventory().removeItem(itemStack);
        Common.tellNoPrefix(player, Settings.Messages.SELL_SUCCESS);
    }

    private int getAmount(Player player, ItemStack itemStack) {
        int amount = 0;

        if (itemStack == null)
            return amount;

        for (int i = 0; i < 36; i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (slot != null && slot.isSimilar(itemStack)) {
                amount += slot.getAmount();
            }
        }

        return amount;
    }
}