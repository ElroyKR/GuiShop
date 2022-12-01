package kr.elroy.guishop;

import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.settings.SimpleSettings;

import java.util.List;

public class Settings extends SimpleSettings {
    @Override
    protected int getConfigVersion() {
        return 2;
    }

    public final static class Messages {
        public static String BUY_SUCCESS;
        public static String SELL_SUCCESS;
        public static String INSUFFICIENT_BALANCE;
        public static String INSUFFICIENT_ITEM_TO_SELL;

        private static void init() {
            setPathPrefix("Messages");
            BUY_SUCCESS = getString("Buy_Success");
            SELL_SUCCESS = getString("Sell_Success");
            INSUFFICIENT_BALANCE = getString("Insufficient_Balance");
            INSUFFICIENT_ITEM_TO_SELL = getString("Insufficient_Item_To_Sell");
        }
    }

    public final static class Menu {
        public static List<String> PRODUCT_ITEM_LORE;
        public static Boolean INFO_ITEM_ENABLED;
        public static String INFO_ITEM_NAME;
        public static List<String> INFO_ITEM_LORE;
        public static CompMaterial INFO_ITEM_MATERIAL;

        private static void init() {
            setPathPrefix("Menu");
            PRODUCT_ITEM_LORE = getList("ProductItem_Lore", String.class);
            INFO_ITEM_ENABLED = getBoolean("InfoItem.Enable");
            INFO_ITEM_MATERIAL = getMaterial("InfoItem.Material");
            INFO_ITEM_NAME = getString("InfoItem.Name");
            INFO_ITEM_LORE = getList("InfoItem.Lore", String.class);
        }
    }
}