import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

/**
 * An ImagePanel is a Swing component that can displays an Image.
 * 
 * @author Blake Bauer
 * @version CS162 Final Project 2016.06.02
 */
public class ImagePanel extends JComponent
{

    private int width, height;
    private Image image;

    /**
     * Creates an empty imagePanel
     */
    public ImagePanel()
    {
        width = 32;    // arbitrary size for empty panel
        height = 32;
        image = null;
    }

    /**
     * Set the image
     * 
     * @param image 
     */
    public void setImage(Image image)
    {
        if(image != null) {
            width = image.getWidth();
            height = image.getHeight();
            this.image = image;
            revalidate();
            repaint();
        }
    }
    
    /**
     * Preferred size of the panel
     * 
     * @return The preferred dimension for this component.
     */
    public Dimension getPreferredSize()
    {
        return new Dimension(width*4, height*4);
    }
    
    /**
     * repaints the panel
     * 
     * @param g The graphics context.
     */
    public void paintComponent(Graphics g)
    {
        Dimension size = getSize();
        g.clearRect(0, 0, size.width, size.height);
        if(image != null) {
            g.drawImage(image, 0, 0,width*4, height*4,null, null);
        }
    }
}
