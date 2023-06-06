package com.example.officialjavafxproj.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileController{

    public static void uploadFile(File targetFile, File uploadedFile, int width, int height){
        try {
            BufferedImage bufferedImage = ImageIO.read(uploadedFile);
//            BufferedImage rescaledImage = imageMiddleware.rescaleImage(width, height, bufferedImage);
            String extension = getFileExtension(uploadedFile);
            ImageIO.write(bufferedImage, extension, targetFile);
            System.out.println("File uploaded and saved successfully.");
        }catch (IOException err){
        }
    }

    public static String getFileExtension(File inputFile){
        String fileName = inputFile.toString();
        String extension = "";
        int index = fileName.lastIndexOf('.');
        if(index > 0) {
            extension = fileName.substring(index + 1);
        }
        return extension;
    }

    public static void deleteFile(File deleteFile){
        deleteFile.delete();
    }

    public static void renameFile(File file, File renameFile){
        file.renameTo(renameFile);
    }

}
