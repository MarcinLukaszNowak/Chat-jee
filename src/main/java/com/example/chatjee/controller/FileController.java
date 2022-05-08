package com.example.chatjee.controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;

@Path("file")
public class FileController {

    // wypadałoby logikę biznesową przenieść do service'ów

    @GET
    @Path("{roomId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response fileList(@PathParam("roomId") String roomId) {
        File[] files = new File(getMainFolder() + "\\" + roomId).listFiles();
        String fileList = "Files:\n";
        if (files != null) {
            fileList += Arrays.stream(files)
                    .map(File::getName)
                    .collect(Collectors.joining("\n"));
        } else {
            fileList += "<empty>";
        }
        return Response.ok(fileList).build();
    }

    @GET
    @Path("{roomId}/{fileName}")
    public Response sendFileToClient(@PathParam("roomId") String roomId,
                                     @PathParam("fileName") String fileName) {
        File file = new File(getMainFolder() + "\\" + roomId + "\\" + fileName);
        if (file.exists()) {
            return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                    .build();
        } else {
            return Response.status(500, "File not found").build();
        }
    }


    @POST
    @Path("{roomId}")
    public Response downloadFileFromClient(@PathParam("roomId") String roomId,
                                           @QueryParam("fileName") String fileName,
                                           InputStream inputStream) {
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
            return Response.ok().build();
        } catch (FileNotFoundException e) {
            return Response.serverError().build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    private String getMainFolder() {
        return System.getProperty("user.dir") + "\\files";
    }
}
