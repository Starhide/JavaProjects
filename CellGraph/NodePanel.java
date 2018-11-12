import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Write a description of class NodePanel here.
 * 
 * @author Blake Bauer
 * @version (a version number or a date)
 */
public class NodePanel extends JComponent
{
    // The current width and height of this panel
    private int width, height;
    private OFImage panelImage;
    private Graph graph;

    /**
     * Constructor for objects of class NodePanel
     */
    public NodePanel(int w, int h, Graph graph)
    {
        width = w;    // arbitrary size for empty panel
        height = h;
        panelImage = new OFImage(w,h);
        this.graph = graph;
        generateImage(0);
    }

    /**
     * Generates the image
     * needs major cleaning
     */
    public void generateImage(int selectV){
        Graphics graphics = panelImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0,width,height);
        graphics.setColor(Color.BLACK);

        int degree = graph.degree(selectV);
        int diameter = Math.min(157, (int)((Math.PI*width*0.8)/(degree)));

        Iterator it = graph.adj(selectV).iterator();
        int count = 0;
        while(it.hasNext()){
            int index = (int)it.next();
            int w2 = graphics.getFontMetrics().stringWidth("" + index) / 2;
            int h2 = graphics.getFontMetrics().getDescent();
            graphics.drawOval((int)(0.4*width*Math.cos((2*Math.PI*count)/degree)+width/2-diameter/4),(int)(0.4*width*Math.sin((2*Math.PI*count)/degree)+width/2-diameter/4), diameter/2, diameter/2);
            graphics.drawString("" + index, (int)(0.4*width*Math.cos((2*Math.PI*count)/degree)+width/2 - w2), (int)(0.4*width*Math.sin((2*Math.PI*count)/degree)+width/2 + h2));
            graphics.drawLine(width/2 + (int)(Math.cos((2*Math.PI*count)/degree)*diameter*0.25), width/2 + (int)(Math.sin((2*Math.PI*count)/degree)*diameter*0.25), 
                (int)(0.4*width*Math.cos((2*Math.PI*count)/degree)+width/2) - (int)(Math.cos((2*Math.PI*count)/degree)*diameter*0.25),(int)(0.4*width*Math.sin((2*Math.PI*count)/degree)+width/2) - (int)(Math.sin((2*Math.PI*count)/degree)*diameter*0.25));
            count++;
        }
        int w2 = graphics.getFontMetrics().stringWidth("" + selectV) / 2;
        int h2 = graphics.getFontMetrics().getDescent();
        graphics.drawOval(width/2-diameter/4,width/2-diameter/4, diameter/2, diameter/2);
        graphics.drawString("" + selectV, width/2-w2,width/2+h2);

        repaint();
    }
    
    public void setGraph(Graph G){
        graph = G;
    }

    /**
     * Tell the layout manager how big we would like to be.
     * (This method gets called by layout managers for placing
     * the components.)
     * 
     * @return The preferred dimension for this component.
     */
    public Dimension getPreferredSize()
    {
        return new Dimension(width, height);
    }

    /**
     * This component needs to be redisplayed. Copy the internal image 
     * to screen. (This method gets called by the Swing screen painter 
     * every time it want this component displayed.)
     * 
     * @param g The graphics context that can be used to draw on this component.
     */
    public void paintComponent(Graphics g)
    {
        Dimension size = getSize();
        g.clearRect(0, 0, size.width, size.height);
        if(panelImage != null) {
            g.drawImage(panelImage, 0, 0, null);
        }
    }
}
