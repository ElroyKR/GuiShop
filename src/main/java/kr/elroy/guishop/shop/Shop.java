package kr.elroy.guishop.shop;

import lombok.Getter;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;

import java.util.ArrayList;

@Getter
public class Shop implements ConfigSerializable {
    private final String shopName;
    private final ArrayList<ShopItem> shopItems;

    public Shop(final String shopName) {
        this.shopName = shopName;
        this.shopItems = new ArrayList<>();
    }

    private Shop(String shopName, ArrayList<ShopItem> shopItems) {
        this.shopName = shopName;
        this.shopItems = shopItems;
    }

    public void addShopItem(final ShopItem shopItem) {
        shopItems.add(shopItem);
        ShopManager.getInstance().save();
    }

    public void removeShopItem(final ShopItem shopItem) {
        shopItems.remove(shopItem);
        ShopManager.getInstance().save();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shop)) return false;

        Shop shop = (Shop) o;

        if (!shopName.equals(shop.shopName)) return false;
        return shopItems.equals(shop.shopItems);
    }

    @Override
    public int hashCode() {
        int result = shopName.hashCode();
        result = 31 * result + shopItems.hashCode();
        return result;
    }

    @Override
    public SerializedMap serialize() {
        final SerializedMap map = new SerializedMap();

        map.put("ShopName", shopName);
        map.put("ShopItems", shopItems);

        return map;
    }

    @SuppressWarnings("unused")
    public static Shop deserialize(final SerializedMap map) {
        final ArrayList<ShopItem> shopItems = new ArrayList<>(map.getList("ShopItems", ShopItem.class));
        return new Shop(map.getString("ShopName"), shopItems);
    }
}