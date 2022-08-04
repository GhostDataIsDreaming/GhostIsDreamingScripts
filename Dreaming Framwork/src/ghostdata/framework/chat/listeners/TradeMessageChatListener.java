package ghostdata.framework.chat.listeners;

import ghostdata.framework.chat.ChatListener;
import org.dreambot.api.wrappers.widgets.message.Message;

public interface TradeMessageChatListener extends ChatListener {

    void onTradeMessage(Message messsage);
}
