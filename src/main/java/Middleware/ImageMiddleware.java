package Middleware;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageMiddleware {
    public BufferedImage rescaleImage(int width, int height, BufferedImage image){
        image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return toBufferedImage(image);
    }

    public BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bImage;
    }
}
