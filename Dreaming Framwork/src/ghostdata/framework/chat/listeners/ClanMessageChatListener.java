package ghostdata.framework.chat.listeners;

import ghostdata.framework.chat.ChatListener;
import org.dreambot.api.wrappers.widgets.message.Message;

public interface ClanMessageChatListener extends ChatListener {

    void onClanMessage(Message message);
}
