package kr.elroy.guishop.shop;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.model.ConfigSerializable;

@Getter
public class ShopItem implements ConfigSerializable {
    private final ItemStack itemStack;
    private int buyPrice;
    private int sellPrice;

    public ShopItem(final ItemStack itemStack,
                    final int buyPrice,
                    final int sellPrice) {
        this.itemStack = itemStack;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShopItem)) return false;

        ShopItem shopItem = (ShopItem) o;

        if (buyPrice != shopItem.buyPrice) return false;
        if (sellPrice != shopItem.sellPrice) return false;
        return itemStack.equals(shopItem.itemStack);
    }

    @Override
    public int hashCode() {
        int result = itemStack.hashCode();
        result = 31 * result + buyPrice;
        result = 31 * result + sellPrice;
        return result;
    }

    @Override
    public SerializedMap serialize() {
        final SerializedMap map = new SerializedMap();

        map.put("ItemStack", itemStack);
        map.put("BuyPrice", buyPrice);
        map.put("SellPrice", sellPrice);

        return map;
    }

    @SuppressWarnings("unused")
    public static ShopItem deserialize(final SerializedMap map) {
        final ItemStack itemStack = map.getItemStack("ItemStack");
        final int buyPrice = map.getInteger("BuyPrice");
        final int sellPrice = map.getInteger("SellPrice");

        return new ShopItem(itemStack, buyPrice, sellPrice);
    }
}