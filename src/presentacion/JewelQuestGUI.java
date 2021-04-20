package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class JewelQuestGUI extends JFrame{
    JFrame frame;
    JPanel menuPanel;
    JLabel menuLabel;
    JButton newGameButton;
    JButton resumeGameButton;
    JButton openGameButton;
    JButton saveButton;
    JButton saveAsButton;
    JButton modifyBoardButton;
    JButton endGameButton;

    private boolean menuCreated = false;

    // Cards
    public static JPanel cards;
    //public static Component toDelete;
    final static String MAIN_MENU = "main menu";
    final static String GAME_BOARD = "game board";
    final static String MODIFY_BOARD = "modificar tablero";

    // Game board
    GameBoard board;

    /**
     * Constructor of the JewelQuestGUI class
     */
    public JewelQuestGUI(){
        cards = new JPanel(new CardLayout());

        this.prepareElements();
    }

    /**
     * Main method of the JewelQuestGUI
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JewelQuestGUI();

        frame.setVisible(true);
        frame.setResizable(true);
    }

    /**
     * Method for preparing all the elements of the GUI
     */
    private void prepareElements(){
        setTitle("JewelQuest");

        // Size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width / 2, screenSize.height / 2);

        // Position
        setLocationRelativeTo(null);

        // Close operation
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event){
                askBeforeClosing();
            }
        });

        // Add the cards to the frame
        add(cards);

        // Menu
        this.createMenu();

        // Menu actions
        this.prepareMenuActions();
    }

    /**
     * Method for creating the menu and its layout
     */
    private void createMenu(){
        // Frame size
        Dimension frameSize = getSize();

        // Panel
        this.menuPanel = new JPanel();
        this.menuPanel.setSize(frameSize.width - 50, frameSize.height - 50);
        this.menuPanel.setLayout(new GridLayout(7, 3));
        this.menuPanel.setBackground(Color.ORANGE);

        // Label
        this.menuLabel = new JLabel("Menú principal");
        this.menuLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Buttons
        this.newGameButton = new JButton("Nuevo juego");
        this.resumeGameButton = new JButton("Continuar juego");
        this.openGameButton = new JButton("Abrir juego");
        this.saveButton = new JButton("Guardar juego");
        this.saveAsButton = new JButton("Guardar como");
        this.modifyBoardButton = new JButton("Modificar tablero");
        this.endGameButton = new JButton("Finalizar juego");

        // Add the buttons
        this.menuPanel.add(this.menuLabel);

        if (GameBoard.gameRunning){
            this.menuPanel.add(resumeGameButton);
        } else {
            this.menuPanel.add(newGameButton);
        }

        this.menuPanel.add(openGameButton);
        this.menuPanel.add(saveButton);
        this.menuPanel.add(saveAsButton);
        this.menuPanel.add(modifyBoardButton);
        this.menuPanel.add(endGameButton);

        // Add panel to the frame
        cards.add(menuPanel, MAIN_MENU);
    }

    /**
     * Method for preparing all the actions that can be triggered by the user
     * in the menu
     */
    private void prepareMenuActions(){
        // Preparamos los eventos de click de los botones
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });

        resumeGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("RESUME");
                resumeGame();
            }
        });

        openGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGame();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
            }
        });

        saveAsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGameAs();
            }
        });

        modifyBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyBoard();
            }
        });


        endGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endGame();
            }
        });
    }

    /**
     * Method for creating a new game
     */
    private void newGame(){
        board = new GameBoard(getWidth(), getHeight());

        this.createMenu();
        this.prepareMenuActions();

        cards.add(board, GAME_BOARD);
        selectCard(GAME_BOARD);
    }

    /**
     * Method for resuming the game
     */
    private void resumeGame(){
        board = new GameBoard();
        selectCard(GAME_BOARD);
    }

    /**
     * Method for open a previously saved game
     */
    private void openGame(){
        JOptionPane.showMessageDialog(null, "Abrir juego");

        // JFileChooser
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccione un juego");
        int selected = chooser.showOpenDialog(menuPanel);

        switch(selected){
            case JFileChooser.APPROVE_OPTION:
                File file = chooser.getSelectedFile();

                System.out.println("\nEsta lógica está en construcción");
                System.out.println("Opening " + file.getName() + " in " +file.getAbsolutePath());

                // Lógica con el documento
                JOptionPane.showMessageDialog(null, "Message selected");
                break;
            case JFileChooser.ERROR_OPTION:
                JOptionPane.showMessageDialog(null, "Something bad happened");
                break;
            case JFileChooser.CANCEL_OPTION:
                JOptionPane.showMessageDialog(null, "Cancel everything!");
                break;

        }
    }

    /**
     * Method for saving the current game
     */
    private void saveGame(){
        JOptionPane.showMessageDialog(null, "Guardar juego");

        // JFileChooser
        JFileChooser chooser = new JFileChooser();
        int selected = chooser.showSaveDialog(menuPanel);

        switch(selected){
            case JFileChooser.APPROVE_OPTION:
                File file = chooser.getSelectedFile();
                System.out.println("\nEsta lógica está en construcción");
                System.out.println("Saving " + file.getName() + " at " +file.getAbsolutePath());

                // Lógica con el docuumento
                JOptionPane.showMessageDialog(null, "Message selected");
                break;
            case JFileChooser.ERROR_OPTION:
                JOptionPane.showMessageDialog(null, "Something bad happened");
                break;
            case JFileChooser.CANCEL_OPTION:
                JOptionPane.showMessageDialog(null, "Cancel everything!");
                break;
        }
    }

    /**
     * Method for saving the current game in an specific location
     */
    private void saveGameAs(){
        JOptionPane.showMessageDialog(null, "Guardar como");

        // JFileChooser
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccione un archivo para guardar el juego");
        int selected = chooser.showSaveDialog(menuPanel);

        switch(selected){
            case JFileChooser.APPROVE_OPTION:
                File file = chooser.getSelectedFile();
                System.out.println("\nEsta lógica está en construcción");
                System.out.println("Saving " + file.getName() + " at " +file.getAbsolutePath());

                // Lógica con el docuumento
                JOptionPane.showMessageDialog(null, "Message selected");
                break;
            case JFileChooser.ERROR_OPTION:
                JOptionPane.showMessageDialog(null, "Something bad happened");
                break;
            case JFileChooser.CANCEL_OPTION:
                JOptionPane.showMessageDialog(null, "Cancel everything!");
                break;

        }
    }

    /**
     * Method for modifying the board
     */
    private void modifyBoard(){
        ModifyBoard modify = new ModifyBoard(this);

        this.prepareMenuActions();

        cards.add(modify, MODIFY_BOARD);

        //this.repaint();

        //this.switchPanel(board);
        selectCard(MODIFY_BOARD);
    }

    /**
     * Method for ending the current game
     */
    private void endGame(){
        askBeforeClosing();
    }

    /**
     * Method for verifying actions
     */
    private void askBeforeClosing() {
        String ObjButtons[] = {"Sí","No"};
        int PromptResult = JOptionPane.showOptionDialog(null,"¿Desea salir del programa?","JewelQuest",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);

        if(PromptResult==JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }

    /**
     * Method for switching between cards
     * @return
     */
    public static void selectCard(String card){
        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, card);
    }


    public boolean isMenuCreated() {
        return menuCreated;
    }

    public void setMenuCreated(boolean menuCreated) {
        this.menuCreated = menuCreated;
    }
}
