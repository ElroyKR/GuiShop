package kr.elroy.guishop.menu;

import kr.elroy.guishop.conversation.ChangePricePrompt;
import kr.elroy.guishop.shop.PriceType;
import kr.elroy.guishop.shop.Shop;
import kr.elroy.guishop.shop.ShopItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.MenuPagged;
import org.mineacademy.fo.menu.model.ItemCreator;

public class ShopAdminMenu extends MenuPagged<ShopItem> {
    private final Shop shop;

    public ShopAdminMenu(Shop shop) {
        super(shop.getShopItems());
        this.shop = shop;
        setSize(54);
        this.setTitle("&6&l" + shop.getShopName() + " &cADMIN");
    }

    @Override
    public Menu newInstance() {
        return new ShopAdminMenu(shop);
    }

    @Override
    protected ItemStack convertToItemStack(ShopItem shopItem) {
        return ItemCreator
                .of(shopItem.getItemStack())
                .lore(
                        String.format("구매가격: %d, 판매가격: %d", shopItem.getBuyPrice(), shopItem.getSellPrice()),
                        "&f&l좌클릭: &f구매가격 변경", "&f&l우클릭: &f판매가격 변경", "&f&l중간버튼 클릭: &f아이템 삭제"
                )
                .makeMenuTool();
    }

    @Override
    protected void onPageClick(Player player, ShopItem shopItem, ClickType clickType) {
        if (clickType.equals(ClickType.MIDDLE)) {
            shop.removeShopItem(shopItem);
            restartMenu();
            return;
        }

        if (clickType.equals(ClickType.LEFT)) {
            new ChangePricePrompt(true, shopItem, PriceType.BUY_PRICE).show(player);
            return;
        }

        if (clickType.equals(ClickType.RIGHT)) {
            new ChangePricePrompt(true, shopItem, PriceType.SELL_PRICE).show(player);
            return;
        }
    }
}