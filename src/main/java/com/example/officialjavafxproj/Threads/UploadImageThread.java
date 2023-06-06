package com.example.officialjavafxproj.Threads;

import Service.UserServices;
import com.example.officialjavafxproj.Utils.FileController;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Builder
@Data
@NoArgsConstructor
public class UploadImageThread implements Runnable {
    private File targetFile;
    private File uploadedFile;
    private int finalWidth;
    private int finalHeight;


    public UploadImageThread(File targetFile, File uploadedFile, int finalWidth, int finalHeight) {
        this.targetFile = targetFile;
        this.uploadedFile = uploadedFile;
        this.finalWidth = finalWidth;
        this.finalHeight = finalHeight;
    }

    @Override
    public void run() {
        FileController.uploadFile(targetFile, uploadedFile, finalWidth, finalHeight);
    }
}
