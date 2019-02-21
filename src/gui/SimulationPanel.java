package gui;

import reactiveAgent.Environment;
import reactiveAgent.EnvironmentListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

import javax.swing.SwingWorker;

public class SimulationPanel extends JPanel implements EnvironmentListener {

    private static final int SLEEP_MILLIS = 500; // todo modify to speed up the simulation
    private static final int NUM_ITERATIONS = 20; // todo modify to change the number of iterations

    private static final int CELL_SIZE = 20;
    private static final int GRID_TO_PANEL_GAP = 20;
    private static final int N = 10;

    private Environment environment;
    private Image image;
    JPanel EnvironmentPanel = new JPanel();
    JButton jButtonRun = new JButton("Run");

    public SimulationPanel() {
        EnvironmentPanel.setPreferredSize(new Dimension(N * CELL_SIZE + GRID_TO_PANEL_GAP * 2, N * CELL_SIZE + GRID_TO_PANEL_GAP * 2));
        setLayout(new BorderLayout());
        add(EnvironmentPanel, java.awt.BorderLayout.CENTER);
        add(jButtonRun, java.awt.BorderLayout.SOUTH);
        jButtonRun.addActionListener(new SimulationPanel_jButtonRun_actionAdapter(this));
    }

    public void jButtonRun_actionPerformed(ActionEvent e) {
        environment = new Environment(N, N, NUM_ITERATIONS);
        environment.addEnvironmentListener(this);

        buildImage(environment);

        SwingWorker worker = new SwingWorker<Void, Void>() {
            public Void doInBackground() {
                try {
                    environmentUpdated();
                    environment.run();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                return null;
            }
        };
        worker.execute();
    }

    public void buildImage(Environment environment) {
        image = new BufferedImage(environment.getNumColumns() * CELL_SIZE, environment.getNumLines() * CELL_SIZE, BufferedImage.TYPE_INT_RGB);
    }

    public void environmentUpdated() {
        int n = environment.getNumLines();
        Graphics g = image.getGraphics();
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                g.setColor(environment.getCellColor(y, x));
                g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        g = EnvironmentPanel.getGraphics();
        g.drawImage(image, GRID_TO_PANEL_GAP, GRID_TO_PANEL_GAP, null);

        try {
            Thread.sleep(SLEEP_MILLIS);
        } catch (InterruptedException ignore) {
        }
    }
}

//--------------------
class SimulationPanel_jButtonRun_actionAdapter implements ActionListener {

    private SimulationPanel adaptee;

    SimulationPanel_jButtonRun_actionAdapter(SimulationPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButtonRun_actionPerformed(e);
    }
}
