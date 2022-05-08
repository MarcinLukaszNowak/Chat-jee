package com.example.chatjee.service;

import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;

@ApplicationScoped
public class FileServiceImpl implements FileService {

    @Override
    public String getFileList(String roomId) {
        File[] files = new File(getMainFolder() + "\\" + roomId).listFiles();
        String fileList = "Files:\n";
        if (files != null) {
            fileList += Arrays.stream(files)
                    .map(File::getName)
                    .collect(Collectors.joining("\n"));
        } else {
            fileList += "<empty>";
        }
        return fileList;
    }

    @Override
    public File getFileFromRoom(String roomId, String fileName) throws FileNotFoundException {
        File file = new File(getMainFolder() + "\\" + roomId + "\\" + fileName);
        if (file.exists()) {
            return file;
        } else {
            throw new FileNotFoundException();
        }
    }

    @Override
    public boolean saveFileInRoom(String roomId, String fileName, InputStream inputStream) {
        File folderPath = new File(getMainFolder() + "\\" + roomId);
        if (!folderPath.exists()) {
            folderPath.mkdirs();
        }
        String fileFullPath = folderPath + "\\"  +  fileName;
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileFullPath)) {
            byte[] buffer = new byte[2048];
            int read = 0;
            while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
                fileOutputStream.write(buffer, 0, read);
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getMainFolder() {
        return System.getProperty("user.dir") + "\\files";
    }

}
