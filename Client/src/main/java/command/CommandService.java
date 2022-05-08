package command;

import client.Client;
import message.MessageReceiver;

public interface CommandService {

    void joinRoom(Client client, MessageReceiver messageReceiver, String newRoomId);

    void printRoomHistory(String roomId);

    void downloadFile(String roomId, String fileName);

    void sendFile(String roomId, String fileFullPath);

    void printFileList(String roomId);
}
