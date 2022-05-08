package com.example.chatjee.controller;

import com.example.chatjee.service.FileService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

@Path("file")
public class FileController {

    @Inject
    FileService fileService;

    @GET
    @Path("{roomId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response fileList(@PathParam("roomId") String roomId) {
        return Response.ok(fileService.getFileList(roomId)).build();
    }

    @GET
    @Path("{roomId}/{fileName}")
    public Response sendFileToClient(@PathParam("roomId") String roomId,
                                     @PathParam("fileName") String fileName) {
        try {
            return Response.ok(fileService.getFileFromRoom(roomId, fileName)).build();
        } catch (FileNotFoundException e) {
            return Response.status(500, "File not found.").build();
        }
    }


    @POST
    @Path("{roomId}")
    public Response downloadFileFromClient(@PathParam("roomId") String roomId,
                                           @QueryParam("fileName") String fileName,
                                           InputStream inputStream) {
        boolean fileSaved = fileService.saveFileInRoom(roomId, fileName, inputStream);
        if (fileSaved) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }
    }

}
