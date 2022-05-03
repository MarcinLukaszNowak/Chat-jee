package command;

import lombok.Getter;
import org.jgroups.util.Tuple;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Command {
    HELP( "help", "", "get all commands"),
    LEAVE_CHAT("q", "", "leave the chat"),
    NEW_ROOM("new_room", "roomName", "create new room named roomName"),
    ROOM_LIST("room_list", "", "list of rooms"),
    JOIN_ROOM("join_room", "roomId", "join to room with id = roomId"),
    SEND_FILE("send_file", "filePath", "send file which path = filePath"),
    DOWNLOAD_FILE("download_file", "fileName", "download file with name = fileName"),
    AVAILABLE_FILES("available_files", "", "list of files available to download");

    private static final String COMMAND_IDENTIFIER = "**";

    @Getter
    private final String commandString;
    @Getter
    private final String paramName;
    @Getter
    private final String description;

    Command(String commandString, String paramName, String description) {
        this.commandString = COMMAND_IDENTIFIER + commandString;
        this.paramName = paramName;
        this.description = description;
    }

    public static boolean isCommand(String message) {
        return message.length() >= 2 && COMMAND_IDENTIFIER.equals(message.substring(0, 2));
    }

    public boolean equalsCommand(String command) {
        return this.getCommandString().equals(command);
    }

    public static Tuple<String, String> splitToCommandAndParam(String message) {
        String command = "";
        String param = "";
        if (message.contains(" ")) {
            String[] commandParts = message.split(" ", 2);
            command = commandParts[0];
            param = commandParts[1];
        } else {
            command = message;
        }
        return new Tuple<>(command, param);
    }

    public static String getAllCommands() {
        return "Availble commands: \n" + Arrays.stream(Command.values())
                .map(v -> v + "\n")
                .collect(Collectors.joining());
    }

    @Override
    public String toString() {
        return commandString
                + (paramName.equals("") ? "" : " " + paramName + " ")
                + " - "
                + description;
    }

}
