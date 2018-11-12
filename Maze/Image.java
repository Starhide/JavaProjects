import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

/**
 * Image extending BufferedImage
 * 
 * @author Blake Bauer
 * @version CS162 Final Project 2016.06.02
 */
public class Image extends BufferedImage
{
    /**
     * Create an Image from a BufferedImage.
     * @param image .
     */
    public Image(BufferedImage image)
    {
         super(image.getColorModel(), image.copyData(null), image.isAlphaPremultiplied(), null);
    }

    /**
     * Create an Image with specified size.
     * @param width Width of the image.
     * @param height Height of the image.
     */
    public Image(int width, int height)
    {
        super(width, height, TYPE_INT_RGB);
    }

    /**
     * sets the given pixel (x,y) to color
     * @param x x coordinate of the pixel.
     * @param y y coordinate of the pixel.
     * @param color The new color of the pixel.
     */
    public void setPixel(int x, int y, Color color)
    {
        setRGB(x, y, color.getRGB());
    }
}
