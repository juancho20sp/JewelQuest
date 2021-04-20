package presentacion;

import dominio.GameConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyBoard extends JPanel{
    private JPanel configPanel;
    private JLabel title;
    private JButton colorButton;
    private JButton goBackButton;
    private JFrame frame;

    private GameConfiguration config;


    /**
     * Constructor for the ModifyBoard class
     */
    public ModifyBoard(JFrame frame){
        this.frame = frame;

        this.config = new GameConfiguration();

        this.prepareBoard();

        add(this.configPanel);
    }

    /**
     * Method for preparing the board
     */
    private void prepareBoard(){
        // Frame size
        Dimension frameSize = this.frame.getSize();

        // Main Panel
        setLayout(null);

        // Panel
        this.configPanel = new JPanel();
        this.configPanel.setSize(frameSize.width, frameSize.height);
        this.configPanel.setLayout(new GridLayout(7, 3));
        this.configPanel.setBackground(Color.ORANGE);

        // Label
        this.title = new JLabel("Seleccione el color de fondo del tablero:");
        this.title.setHorizontalAlignment(SwingConstants.CENTER);

        // Buttons
        this.colorButton = new JButton("Seleccionar color de fondo");
        this.goBackButton = new JButton("Volver al menú principal");

        // Actions
        this.addActionsToButtons();

        // Add
        this.configPanel.add(this.title);
        this.configPanel.add(this.colorButton);
        this.configPanel.add(this.goBackButton);

    }

    /**
     * Method for setting up the button actions
     */
    private void addActionsToButtons(){
        this.colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectColor();
            }
        });

        this.goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
    }

    /**
     * Method for toggling the color picker
     */
    private void selectColor(){
        JColorChooser chooser = new JColorChooser();
        config.setBackgroundColor(chooser.showDialog(null, "Seleccione un color", Color.red));
    }

    /**
     * Method for going back
     * @return
     */
    private void goBack(){
        JewelQuestGUI.selectCard(JewelQuestGUI.MAIN_MENU);
    }


    public GameConfiguration getConfig() {
        return config;
    }
}
