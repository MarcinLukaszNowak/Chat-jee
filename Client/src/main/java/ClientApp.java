import client.Client;
import connection.ServerConnector;
import message.MessageReceiver;

import java.util.Scanner;

public class ClientApp {

    public static void main(String[] args) {
        System.out.println("Type your name: ");
        Scanner scanner = new Scanner(System.in);

        Client client = new Client(scanner.nextLine());
        System.out.println("Hello: " + client.getName() + "!");
        client.setRoomId("xd"); // todo

        ServerConnector serverConnector = new ServerConnector();
        boolean connected = serverConnector.connectWithServer();

        if (connected) {
            MessageReceiver messageReceiver = new MessageReceiver(serverConnector.getJmsContext(), serverConnector.getTopic(), client);
            ConsoleMessageHandler consoleMessageHandler = new ConsoleMessageHandler(client, serverConnector.getJmsContext(), serverConnector.getTopic(), messageReceiver);
            new Thread(consoleMessageHandler).start();
            new Thread(messageReceiver).start();
        }
    }

}
