package kr.elroy.guishop;

import kr.elroy.guishop.command.AdminCommand;
import kr.elroy.guishop.listener.NpcInteractHandler;
import kr.elroy.guishop.shop.ShopManager;
import org.mineacademy.fo.plugin.SimplePlugin;

public class GuiShop extends SimplePlugin {
    @Override
    protected void onPluginStart() {
        ShopManager.getInstance();
    }

    @Override
    protected void onPluginStop() {
        ShopManager.getInstance().save();
    }

    @Override
    protected void onReloadablesStart() {
        registerCommand(new AdminCommand());
        registerEvents(new NpcInteractHandler());
    }
}