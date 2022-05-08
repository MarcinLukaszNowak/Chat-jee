package command;

import client.Client;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import logger.Logg;
import message.MessageReceiver;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.io.*;

public class CommandService {

    private final String BASE_SERVER_URL = "http://localhost:8080/Chat-jee-1.0-SNAPSHOT/api";
    private final String HISTORY_URL_PART = "/history";
    private final String FILE_PART_URL = "/file";

    public void joinRoom(Client client, MessageReceiver messageReceiver, String newRoomId) {
        client.setRoomId(newRoomId);
        messageReceiver.updateRoomId();
        Logg.info("Welcome in '" + newRoomId + "' room!");
    }

    public void printRoomHistory(String roomId) {
        var restClient = new ResteasyClientBuilderImpl().build();
        var roomHistoryMessages = restClient.target(BASE_SERVER_URL + HISTORY_URL_PART + "/" + roomId)
                .request()
                .get(String.class);
        Logg.info(roomHistoryMessages);
        JsonArray jsonArray = new Gson().fromJson(roomHistoryMessages, JsonArray.class);
        jsonArray.forEach(j -> Logg.info(String.valueOf(j)));
    }

    public void downloadFile(String roomId, String fileName) {
        try {
            var restClient = new ResteasyClientBuilderImpl().build();
            var file = restClient.target(BASE_SERVER_URL + "/" + FILE_PART_URL + "/" + roomId + "/" + fileName)
                    .request()
                    .get(File.class);
            File newFile = new File(System.getProperty("user.dir") + "\\" + fileName);
            file.renameTo(newFile);
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.close();
        } catch (Exception e) {
            Logg.error("Download failed. Probably no such file.");
        }
    }

    public void sendFile(String roomId, String fileFullPath) {
        fileFullPath = "D:\\java\\Lufthansa\\Chat-jee\\x.txt"; // todo to del
        File file = new File(fileFullPath);
        try (InputStream fileInputStream = new FileInputStream(file)) {
            byte[] fileBytes = new byte[(int) file.length()];
            fileInputStream.read(fileBytes, 0, fileBytes.length);

            var restClient = new ResteasyClientBuilderImpl().build();
            var response = restClient.target(BASE_SERVER_URL  + FILE_PART_URL + "/" + roomId + "/" + getFileName(fileFullPath))
                    .request()
                    .post(Entity.entity(fileInputStream, MediaType.APPLICATION_OCTET_STREAM));
            Logg.info("File downloaded.");
        } catch (FileNotFoundException e) {
            Logg.error("File not found.");
        } catch (IOException e) {
            Logg.error("Error while sending file.");
        }
    }

    public void printFileList(String roomId) {
        var restClient = new ResteasyClientBuilderImpl().build();
        var fileList = restClient.target(BASE_SERVER_URL + FILE_PART_URL + "/" + roomId)
                .request()
                .get(String.class);
        Logg.info(fileList);
    }

    private static String getFileName(String file) {
        if (file.contains("\\")) {
            return file.substring(file.lastIndexOf("\\"));
        }
        return file;
    }

}
