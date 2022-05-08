package com.example.chatjee.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public interface FileService {

    String getFileList(String roomId);

    File getFileFromRoom(String roomId, String fileName) throws FileNotFoundException;

    boolean saveFileInRoom(String roomId, String fileName, InputStream inputStream);

}
