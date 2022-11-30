package kr.elroy.guishop.command;

import kr.elroy.guishop.menu.ShopAdminMenu;
import kr.elroy.guishop.menu.ShopUserMenu;
import kr.elroy.guishop.shop.Shop;
import kr.elroy.guishop.shop.ShopItem;
import kr.elroy.guishop.shop.ShopManager;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.command.SimpleCommand;

import java.util.Arrays;
import java.util.List;

public class AdminCommand extends SimpleCommand {
    private static final ShopManager shopManager = ShopManager.getInstance();

    public AdminCommand() {
        super("상점");
        setPermission("op.op");
        setPermissionMessage("권한이 없습니다.");
    }

    @Override
    protected void onCommand() {
        checkConsole();

        if (args.length < 1) {
            tellCommandInfo();
            return;
        }

        final String argument = args[0];

        if (argument.equals("목록")) {
            checkBoolean(args.length == 1, "/상점 목록");
            tellShopList();
            return;
        }

        if (argument.equals("열기")) {
            checkBoolean(args.length == 2, "/상점 열기 <상점이름>");
            openShopCommand();
            return;
        }

        if (argument.equals("생성")) {
            checkBoolean(args.length == 2, "/상점 생성 <상점이름>");
            createShopCommand();
            return;
        }

        if (argument.equals("제거")) {
            checkBoolean(args.length == 2, "/상점 제거 <상점이름>");
            removeShopCommand();
            return;
        }


        if (argument.equals("관리")) {
            checkBoolean(args.length == 2, "/상점 관리 <상점이름>");
            openSettingGui();
            return;
        }

        if (argument.equals("아이템등록")) {
            checkBoolean(args.length == 4, "/상점 아이템등록 <상점이름> <판매가격> <구매가격>");
            addShopItemCommand();
            return;
        }

        if (argument.equalsIgnoreCase("NPC")) {
            checkBoolean(args.length == 2, "/상점 NPC <상점이름>");
            createNPC();
            return;
        }

        tellCommandInfo();
    }

    private void createNPC() {
        final String shopName = args[1];
        checkBoolean(shopManager.isExistShopName(shopName), "존재하지 않는 이름입니다.");

        CitizensAPI.getNPCRegistry()
                .createNPC(EntityType.PLAYER, "&6&l[상점] &f" + shopName, getPlayer().getLocation());
    }

    private void tellCommandInfo() {
        tellInfo("/상점 목록 - 상점 목록을 확인합니다.");
        tellInfo("/상점 열기 <상점이름>");
        tellInfo("/상점 생성 <상점이름> - 상점을 생성합니다.");
        tellInfo("/상점 제거 <상점이름> - 상점을 제거합니다.");
        tellInfo("/상점 NPC <상점이름> - 상점의 NPC를 현재 위치에 소환합니다.");
        tellInfo("/상점 아이템등록 <상점이름> <판매가격> <구매가격> - 상점에 아이템을 <가격> 으로 등록합니다.");
        tellInfo("/상점 관리 <상점이름> - 가격을 변경하거나 삭제 할 수 있는 관리 GUI를 엽니다.");
    }

    private void openSettingGui() {
        final Shop shop = shopManager.getShopByName(args[1]);
        checkBoolean(shop != null, "존재하지 않는 상점입니다.");

        new ShopAdminMenu(shop).displayTo(getPlayer());
    }

    private void tellShopList() {
        tellInfo("-- 상점 목록 --");

        for (final Shop shop : shopManager.getShops()) {
            tellInfo(shop.getShopName());
        }
    }

    private void openShopCommand() {
        final String shopName = args[1];
        checkBoolean(shopManager.isExistShopName(shopName), "존재하지 않는 이름입니다.");
        new ShopUserMenu(shopManager.getShopByName(shopName)).displayTo(getPlayer());
    }

    private void createShopCommand() {
        final String shopName = args[1];

        checkBoolean(!shopManager.isExistShopName(shopName), "이미 존재하는 이름입니다.");

        final Shop shop = new Shop(shopName);
        shopManager.addShop(shop);

        tellSuccess("성공적으로 추가하였습니다.");
    }

    private void removeShopCommand() {
        final String shopName = args[1];
        checkBoolean(shopManager.isExistShopName(shopName), "존재하지 않는 이름입니다.");

        if (shopManager.removeShop(shopManager.getShopByName(shopName))) {
            tellSuccess("성공적으로 삭제하였습니다.");
            return;
        }

        tellError("삭제를 실패했습니다.");
    }

    private void addShopItemCommand() {
        final Shop shop = shopManager.getShopByName(args[1]);

        checkBoolean(shop != null, "존재하지 않는 상점이름입니다.");

        final int sellPrice = findNumber(2, 0, Integer.MAX_VALUE,
                "판매가격은 0 이상의 정수로 입력해주세요.");
        final int buyPrice = findNumber(3, 0, Integer.MAX_VALUE,
                "구매가격 0 이상의 정수로 입력해주세요.");

        final ItemStack itemStack = getPlayer().getInventory().getItemInMainHand().clone();
        checkBoolean(!itemStack.getType().equals(Material.AIR), "손에 아이템을 들어주세요.");
        itemStack.setAmount(1);

        final ShopItem shopItem = new ShopItem(itemStack, buyPrice, sellPrice);
        shop.addShopItem(shopItem);

        tellSuccess("성공적으로 아이템을 추가 하였습니다.");
    }

    @Override
    protected List<String> tabComplete() {
        if (args.length == 1) {
            return Arrays.asList("목록", "생성", "제거", "NPC", "아이템목록", "아이템등록", "아이템삭제");
        }

        final List<String> argument1 = Arrays.asList("제거", "NPC", "아이템목록", "아이템등록", "아이템삭제");

        if (args.length == 2 && argument1.contains(args[0])) {
            return shopManager.getShopNames();
        }

        return null;
    }
}