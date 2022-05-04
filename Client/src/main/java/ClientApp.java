import client.Client;
import configuration.Conf;
import connection.ServerConnector;
import logger.Logg;
import message.MessageReceiver;

import java.util.Scanner;

public class ClientApp {

    public static void main(String[] args) {
        Logg.info("Type your name: ");
        Scanner scanner = new Scanner(System.in);

        Client client = new Client(scanner.nextLine());
        Logg.info("Hello: " + client.getName() + "!");
        client.setRoomId(Conf.MAIN_ROOM);
        Logg.info("Joined room: " + Conf.MAIN_ROOM);

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
