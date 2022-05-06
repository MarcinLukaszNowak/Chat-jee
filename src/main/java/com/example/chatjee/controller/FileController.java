package com.example.chatjee.controller;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.io.*;
import java.lang.annotation.Retention;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Path("file")
public class FileController {

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
//    @Path("hello")
    public Response downloadFile(@PathParam("roomId") String roomId, @PathParam("fileName") String fileName) {
        File file = new File(getMainFolder() + "\\" + roomId + "\\" + fileName);
        if (file.exists()) {
            return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                    .build();
        } else {
            return Response.status(500, "File not found").build();
        }
    }

    private String getMainFolder() {
        return System.getProperty("user.dir") + "\\files";
    }
}
