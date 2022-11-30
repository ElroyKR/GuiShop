package kr.elroy.guishop.conversation;

import kr.elroy.guishop.shop.PriceType;
import kr.elroy.guishop.shop.ShopItem;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.Valid;
import org.mineacademy.fo.conversation.SimplePrompt;
import org.mineacademy.fo.remain.CompSound;

public class ChangePricePrompt extends SimplePrompt {
    private final ShopItem shopItem;
    private final PriceType priceType;

    public ChangePricePrompt(boolean openMenu, ShopItem shopItem, PriceType priceType) {
        super(openMenu);
        this.shopItem = shopItem;
        this.priceType = priceType;
    }

    protected String getPrompt(ConversationContext context) {
        return "&6채팅창에 변경하실 가격을 입력 해주세요.";
    }

    @Override
    protected boolean isInputValid(ConversationContext context, String input) {
        if (!Valid.isInteger(input))
            return false;

        int level = Integer.parseInt(input);
        return level > 0;
    }

    @Override
    protected String getFailedValidationText(ConversationContext context, String invalidInput) {
        return String.format("'%s'는 올바르지 않은 입력 입니다. 0 보다 큰 정수만 입력 해주세요.", invalidInput);
    }

    @Nullable
    @Override
    protected Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
        int price = Integer.parseInt(input);

        switch (priceType) {
            case SELL_PRICE:
                shopItem.setSellPrice(price);
                break;
            case BUY_PRICE:
                shopItem.setBuyPrice(price);
                break;
            default:
                assert false : "올바르지 않은 타입입니다.";
                break;
        }

        CompSound.BLOCK_NOTE_BLOCK_PLING.play(getPlayer(context));
        tell("&6가격을 성공적으로 변경하였습니다.");
        return END_OF_CONVERSATION;
    }
}