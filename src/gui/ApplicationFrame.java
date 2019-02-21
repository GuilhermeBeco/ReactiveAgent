package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class ApplicationFrame extends JFrame{

    public static final long serialVersionUID = 1;
    
    SimulationPanel simulationPanel = new SimulationPanel();
    
    public ApplicationFrame() {
           	
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    	setTitle("Reactive Agents (c) Inteligência Artificial - Engenharia Informática - ESTG/IPL");

        //Menus
        JMenuBar jMenuBar1 = new JMenuBar();
        setJMenuBar(jMenuBar1);        
        JMenu jMenuFile = new JMenu("Menu");
        jMenuBar1.add(jMenuFile);        
        JMenuItem jMenuFileExit = new JMenuItem("Exit");
        jMenuFile.setMnemonic('M');
        jMenuFileExit.addActionListener(new ApplicationFrame_jMenuFileExit_ActionAdapter(this));
        jMenuFile.add(jMenuFileExit);
                              
        //Global structure 
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.add(simulationPanel, BorderLayout.CENTER);
        pack();              
    }


    void jMenuFileExit_actionPerformed(ActionEvent actionEvent) {
        System.exit(0);
    }

    
    public void showError(String message){
        JOptionPane.showMessageDialog(this, message, "Error!", JOptionPane.ERROR_MESSAGE);
    }
}


class ApplicationFrame_jMenuFileExit_ActionAdapter implements ActionListener {
    ApplicationFrame adaptee;

    ApplicationFrame_jMenuFileExit_ActionAdapter(ApplicationFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jMenuFileExit_actionPerformed(actionEvent);
    }
}
