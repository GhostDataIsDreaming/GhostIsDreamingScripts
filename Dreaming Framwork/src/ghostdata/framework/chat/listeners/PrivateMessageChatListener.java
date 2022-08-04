package ghostdata.framework.chat.listeners;

import ghostdata.framework.chat.ChatListener;
import org.dreambot.api.wrappers.widgets.message.Message;

public interface PrivateMessageChatListener extends ChatListener {

    void onPrivateInMessage(Message message);

    void onPrivateOutMessage(Message message);
}
