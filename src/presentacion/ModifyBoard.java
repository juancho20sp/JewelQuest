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
    private JButton sizeButton;
    private JButton goBackButton;
    private JFrame frame;

    private GameConfiguration config;

    /**
     * Constructor for the ModifyBoard class
     */
    public ModifyBoard(JFrame frame, GameConfiguration config){
        this.frame = frame;

        this.config = config;

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
        this.configPanel.setBackground(this.config.getBackgroundColor());

        // Label
        this.title = new JLabel("Seleccione el color de fondo del tablero:");
        this.title.setHorizontalAlignment(SwingConstants.CENTER);

        // Buttons
        this.colorButton = new JButton("Seleccionar color de fondo");
        this.sizeButton = new JButton("Cambiar el tamaño del tablero");
        this.goBackButton = new JButton("Volver al menú principal");

        // Actions
        this.addActionsToButtons();

        // Add
        this.configPanel.add(this.title);
        this.configPanel.add(this.colorButton);
        this.configPanel.add(this.sizeButton);
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

        this.sizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeDimensions();
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
        this.config.setBackgroundColor(chooser.showDialog(null, "Seleccione un color", Color.ORANGE));

        this.refresh();
    }

    /**
     * Method for selecting new dimensions for the board
     */
    private void changeDimensions(){
        int rows = 0;
        int cols = 0;

        try {
            rows = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de filas: "));
            cols = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de columnas: "));

            this.config.setHeight(rows);
            this.config.setWidth(cols);
        } catch (Exception e){}

    }

    /**
     * Method for going back
     */
    private void goBack(){
        JewelQuestGUI.selectCard(JewelQuestGUI.MAIN_MENU);
    }

    /**
     * Method for refreshing the view
     */
    private void refresh(){
        this.configPanel.setBackground(this.config.getBackgroundColor());

    }


    public GameConfiguration getConfig() {
        return config;
    }
}
