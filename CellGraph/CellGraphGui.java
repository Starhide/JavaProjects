import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.FileInputStream;

/**
 * Write a description of class CellGraphGui here.
 * 
 * @author Blake Bauer
 * @version (a version number or a date)
 */
public class CellGraphGui extends JFrame
{
    private NodePanel nodePanel;
    private boolean running = true;
    private Graph graph;
    private JSpinner spin;
    private JFileChooser fc;

    /**
     * Constructor for objects of class CellGraphGui
     */
    public CellGraphGui(Graph graph)
    {
        super("Cell Graph");
        this.graph = graph;
        makeFrame();
    }

    private void connectComponents(){
        CC c = new CC(graph);
        JOptionPane.showMessageDialog(this,
            "There are " + c.count() + " connected components.");
    }

    private void cycles(){
        CC c = new CC(graph);
        JOptionPane.showMessageDialog(this,
            "There are " + (graph.getEdges().size()+c.count()-graph.getVertexCount()+1) + " cycles.");
    }

    private void spinToWin(){
        nodePanel.generateImage((int) spin.getValue());
    }

    private void makeFrame()
    {       
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        fc = new JFileChooser();

        nodePanel = new NodePanel(500,500, graph);
        contentPane.add(nodePanel, BorderLayout.CENTER);

        JPanel bp = new JPanel();
        JButton ccButton = new JButton("Connected Components");
        ccButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    connectComponents();
                }
            });
        JButton cButton = new JButton("Cycles");
        cButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    cycles();
                }
            });

        spin = new JSpinner();
        spin.setModel(new SpinnerNumberModel(0, 0, graph.getVertexCount()-1, 1));
        spin.addChangeListener(new javax.swing.event.ChangeListener() {
                public void stateChanged(javax.swing.event.ChangeEvent evt) {
                    spinToWin();
                }
            });
        bp.setLayout(new GridLayout(1,3));
        bp.add(ccButton);
        bp.add(cButton);
        bp.add(spin);
        contentPane.add(bp, BorderLayout.SOUTH);

        makeMenuBar();
        pack();
        setVisible(true);
    }

    private void makeMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem importItem = new JMenuItem("Import");
        importItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    int returnVal = fc.showOpenDialog(CellGraphGui.this);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        //This is where a real application would open the file.
                        try{
                            graph = new Graph(new FileInputStream(file));
                            nodePanel.setGraph(graph);
                            nodePanel.generateImage((int) spin.getValue());
                            spin.setModel(new SpinnerNumberModel(0, 0, graph.getVertexCount()-1, 1));
                        }catch (Exception e){

                        }
                    }

                }
            });
        fileMenu.add(importItem);

        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    System.exit(0);
                }
            });
        fileMenu.add(quitItem);

        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    System.out.println("Cell Graph");
                }
            });
        helpMenu.add(aboutItem);

    }
}
