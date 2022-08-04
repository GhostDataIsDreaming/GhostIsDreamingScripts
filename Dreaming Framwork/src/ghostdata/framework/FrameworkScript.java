package ghostdata.framework;

import ghostdata.framework.chat.listeners.*;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.wrappers.widgets.message.Message;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class FrameworkScript extends AbstractScript implements ChatListener {

    private final List<ChatListener> chatListeners = new ArrayList<>();
    private final List<PaintListener> paintListeners = new ArrayList<>();

    public final void addChatListener(ChatListener listener) {
        chatListeners.add(listener);
    }

    public final void addPainListener(PaintListener listener) {
        paintListeners.add(listener);
    }

    @Override
    public final void onAutoMessage(Message message) {
        if (chatListeners.isEmpty()) return;
        chatListeners.stream()
                .filter(chatListener -> chatListener instanceof AutoMessageChatListener)
                .forEach((l) -> l.onAutoMessage(message));
    }

    @Override
    public final void onPrivateInfoMessage(Message message) {
        if (chatListeners.isEmpty()) return;
        chatListeners.stream()
                .filter(chatListener -> chatListener instanceof AutoMessageChatListener)
                .forEach((l) -> l.onAutoMessage(message));
    }

    @Override
    public final void onClanMessage(Message message) {
        if (chatListeners.isEmpty()) return;
        chatListeners.stream()
                .filter(chatListener -> chatListener instanceof ClanMessageChatListener)
                .forEach((l) -> l.onClanMessage(message));
    }

    @Override
    public final void onMessage(Message message) {
        if (chatListeners.isEmpty()) return;
        chatListeners.stream()
                .filter(chatListener -> chatListener instanceof MessageChatListener)
                .forEach((l) -> l.onMessage(message));
    }

    @Override
    public final void onGameMessage(Message message) {
        if (chatListeners.isEmpty()) return;
        chatListeners.stream()
                .filter(chatListener -> chatListener instanceof GameMessageChatListener)
                .forEach((l) -> l.onGameMessage(message));
    }

    @Override
    public final void onPlayerMessage(Message message) {
        if (chatListeners.isEmpty()) return;
        chatListeners.stream()
                .filter(chatListener -> chatListener instanceof AutoMessageChatListener)
                .forEach((l) -> l.onAutoMessage(message));
    }

    @Override
    public final void onTradeMessage(Message message) {
        if (chatListeners.isEmpty()) return;
        chatListeners.stream()
                .filter(chatListener -> chatListener instanceof TradeMessageChatListener)
                .forEach((l) -> l.onTradeMessage(message));
    }

    @Override
    public final void onPrivateInMessage(Message message) {
        if (chatListeners.isEmpty()) return;
        chatListeners.stream()
                .filter(chatListener -> chatListener instanceof PrivateMessageChatListener)
                .forEach((l) -> l.onPrivateInMessage(message));
    }

    @Override
    public final void onPrivateOutMessage(Message message) {
        if (chatListeners.isEmpty()) return;
        chatListeners.stream()
                .filter(chatListener -> chatListener instanceof AutoMessageChatListener)
                .forEach((l) -> l.onPrivateOutMessage(message));
    }

    @Override
    public void onPaint(Graphics graphics) {
        if (paintListeners.isEmpty()) return;
        paintListeners.forEach((p) -> p.onPaint(graphics));
    }

    @Override
    public void onPaint(Graphics2D graphics) {
        if (paintListeners.isEmpty()) return;
        paintListeners.forEach((p) -> p.onPaint(graphics));
    }
}
