package message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ClientMessage {

    @Getter
    private String roomId;
    private String senderName;
    private String message;

    public String createMessageText() {
        return "[" + senderName + "]: " + message;
    }

}
