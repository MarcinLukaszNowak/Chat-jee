import command.Command;
import command.CommandService;
import configuration.Conf;
import logger.Logg;
import client.Client;
import message.ClientMessage;
import message.MessageReceiver;
import message.MessageSender;
import org.jgroups.util.Tuple;

import javax.jms.JMSContext;
import javax.jms.Topic;
import java.util.Scanner;

public class ConsoleMessageHandler implements Runnable {

    private CommandService  commandService = new CommandService();

    private Client client;
    private JMSContext jmsContext;
    private Topic topic;
    private MessageReceiver messageReceiver;

    public ConsoleMessageHandler(Client client, JMSContext jmsContext, Topic topic, MessageReceiver messageReceiver) {
        this.client = client;
        this.jmsContext = jmsContext;
        this.topic = topic;
        this.messageReceiver = messageReceiver;
    }

    @Override
    public void run() {
        MessageSender messageSender = new MessageSender(jmsContext, topic);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.hasNextLine()) {
                String clientInput = scanner.nextLine();
                if (Command.isCommand(clientInput)) {
                    handleCommand(clientInput);
                } else {
                    ClientMessage clientMessage = new ClientMessage(client.getRoomId(), client.getName(), clientInput);
                    messageSender.sendMessage(clientMessage);
                }
            }
        }
    }

    public void handleCommand(String message) {
        Tuple<String, String> commandAndParam = Command.splitToCommandAndParam(message);
        String command = commandAndParam.getVal1();
        String param = commandAndParam.getVal2();
        if (Command.HELP.equalsCommand(command)) {
            Logg.info(Command.getAllCommands());
        } else if (Command.JOIN_ROOM.equalsCommand(command)) {
            commandService.joinRoom(client, messageReceiver, param);
        } else if (Command.JOIN_MAIN_ROOM.equalsCommand(command)) {
            commandService.joinRoom(client, messageReceiver,Conf.MAIN_ROOM);
        } else if (Command.ROOM_HISTORY.equalsCommand(command)) {
            commandService.printRoomHistory(client.getRoomId());
        } else if (Command.DOWNLOAD_FILE.equalsCommand(command)) {
            commandService.downloadFile(client.getRoomId(), param);
        }  else if (Command.SEND_FILE.equalsCommand(command)) {
            commandService.sendFile(client.getRoomId(), param);
        } else if (Command.AVAILABLE_FILES.equalsCommand(command)) {
            commandService.printFileList(client.getRoomId());
        } else {
            Logg.warn("command: '" + commandAndParam.getVal1() + "' doesn't exist. Type '" + Command.HELP.getCommandString() + "' to get list of commands");
        }
    }







}
