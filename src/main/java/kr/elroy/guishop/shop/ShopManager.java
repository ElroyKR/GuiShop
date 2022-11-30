package kr.elroy.guishop.shop;

import org.mineacademy.fo.settings.YamlConfig;

import java.util.ArrayList;
import java.util.HashMap;

public class ShopManager extends YamlConfig {
    private static ShopManager instance;

    public static ShopManager getInstance() {
        if (instance == null) {
            instance = new ShopManager();
        }

        return instance;
    }

    private final HashMap<String, Shop> shopByName = new HashMap<>();

    public boolean isExistShopName(final String shopName) {
        return shopByName.containsKey(shopName);
    }

    public void addShop(Shop shop) {
        shopByName.put(shop.getShopName(), shop);
    }

    public boolean removeShop(Shop shop) {
        return shopByName.remove(shop.getShopName(), shop);
    }

    public Shop getShopByName(String name) {
        return shopByName.get(name);
    }

    public ArrayList<Shop> getShops() {
        return new ArrayList<>(shopByName.values());
    }

    public ArrayList<String> getShopNames() {
        return new ArrayList<>(shopByName.keySet());
    }

    private ShopManager() {
        loadConfiguration(NO_DEFAULT, "ShopData.yml");
        this.save();
    }

    @Override
    protected void onSave() {
        this.clear();

        for (String shopName : shopByName.keySet()) {
            this.set(shopName, shopByName.get(shopName));
        }
    }

    @Override
    protected void onLoad() {
        for (String shopName : this.getKeys(false)) {
            shopByName.put(shopName, get(shopName, Shop.class));
        }
    }
}