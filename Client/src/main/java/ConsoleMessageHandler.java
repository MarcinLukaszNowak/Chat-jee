import command.Command;
import configuration.Conf;
import logger.Logg;
import lombok.AllArgsConstructor;
import client.Client;
import message.ClientMessage;
import message.MessageReceiver;
import message.MessageSender;
import org.jgroups.util.Tuple;

import javax.jms.JMSContext;
import javax.jms.Topic;
import java.util.Scanner;

@AllArgsConstructor
public class ConsoleMessageHandler implements Runnable {

    private Client client;
    private JMSContext jmsContext;
    private Topic topic;
    private MessageReceiver messageReceiver;

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
            joinRoom(param);
        } else if (Command.JOIN_MAIN_ROOM.equalsCommand(command)) {
            joinRoom(Conf.MAIN_ROOM);
        } else if (Command.LEAVE_CHAT.equalsCommand(command)) {
//            leaveChat();
        } else if (Command.AVAILABLE_FILES.equalsCommand(command)) {
//            sendFileList();
        } else {
            Logg.warn("command: '" + commandAndParam.getVal1() + "' doesn't exist. Type '" + Command.HELP.getCommandString() + "' to get list of commands");
        }
    }

    private void joinRoom(String param) {
        client.setRoomId(param);
        messageReceiver.updateRoomId();
        Logg.info("Welcome in '" + param + "' room!");
    }

}
