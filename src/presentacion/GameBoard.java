package presentacion;

import dominio.JewelQuestException;
import dominio.Jewels.*;
import dominio.LogicGameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class GameBoard extends JPanel{
    private JPanel mainPanel;
    private JPanel upperPanel;
    private JPanel boardPanel;
    private JButton mainMenuButton;
    private JButton restartButton;
    private JLabel movementsLabel;
    private JLabel pointsLabel;

    private int rows = 6;
    private int cols = 6;

    private int width;
    private int height;
    private boolean isGameRunning;

    private Random random;

    private ModifyBoard config;

    private static LogicGameBoard logicBoard;
    //private Color boardColor = ModifyBoard.boardColor;
    public static boolean gameRunning = false;
    private static Jewel board[][];

    /**
     * Constructor of the GameBoard class
     * @param height Height of the main frame
     * @param width Width of the main frame
     */
    public GameBoard(int width, int height){
        this.width = width - 18;
        this.height = height - 38;

        new JPanel();

        //this.mainPanel = new JPanel();

        gameRunning = true;
        this.setGameRunning(false);

        // Random
        this.random = new Random();

        // Logic board
        logicBoard = new LogicGameBoard(this.cols, this.rows);

        // Create the jewel board
        //board = new Jewel[this.rows][this.cols];

        this.prepareElementsBoard();
    }

    /**
     * Constructor for resuming the game
     */
    public GameBoard(){
        new JPanel();
        this.prepareElementsBoard();
    }

    /**
     * Method for preparing the elements of the board
     * @return
     */
    private void prepareElementsBoard(){
        setLayout(null);

        // Upper panel
        this.createUpperPanel();

        // Board panel
        this.createGameBoardPanel();

        // Add
        //add(mainPanel);
    }

    /**
     * Method for creating the upper panel
     * @return
     */
    private void createUpperPanel(){
        // Panel
        this.upperPanel = new JPanel();
        this.upperPanel.setBounds(0, 0, this.width, 30);
        this.upperPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        this.upperPanel.setLayout(new GridLayout(1, 4));

        // Buttons
        this.mainMenuButton = new JButton("Menú principal");
        this.restartButton = new JButton("Reiniciar juego");

        // Labels
        this.movementsLabel = new JLabel("Movimientos: 0", SwingConstants.CENTER);
        this.pointsLabel = new JLabel("Puntos: 0", SwingConstants.CENTER);

        // Actions
        this.createUpperPanelActions();

        // Add
        this.upperPanel.add(mainMenuButton);
        this.upperPanel.add(restartButton);
        this.upperPanel.add(movementsLabel);
        this.upperPanel.add(pointsLabel);

        // Add panel
        add(upperPanel);
    }

    /**+
     * Method for adding actions to the upper panel buttons
     * @return
     */
    private void createUpperPanelActions(){
        // Main menu
        this.mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMainMenu();
            }
        });

        // Restart
        this.restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
    }

    /**
     * Method for creating the game board panel
     * @return
     */
    private void createGameBoardPanel(){
        // Statics
        gameRunning = true;
        board = new Jewel[this.rows][this.cols];

        // Panel
        this.boardPanel = new JPanel();
        //this.boardPanel.setBackground(config.getColor());
        this.boardPanel.setAlignmentX(SwingConstants.CENTER);
        this.boardPanel.setBounds(0, 30, this.width, this.height - 30);

        // Box dimensions
        int boxWidth = this.width / this.cols;
        int boxHeight = (this.height - 11) / this.rows;

        // Layout
        this.boardPanel.setLayout(null);

        // Fill board
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                // Select and add the jewel
                try {
                    this.insertJewellToBoard(i, j, this.getJewel(logicBoard.getBoard()[i][j]));
                } catch(JewelQuestException e){
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }

                // Label config
                board[i][j].setPreferredSize(new Dimension(boxWidth - 10, boxHeight - 10));
                board[i][j].setBounds(i * boxWidth, j * boxHeight, boxWidth, boxHeight);

                // Add actions
                this.addActionToButton(i, j);

                // Add label
                this.boardPanel.add(board[i][j]);
            }
        }

        // Add panel
        add(boardPanel);
    }

    /**
     * Method for selecting the jewell
     * @param id The id of the jewel
     * @return A new jewel
     */
    private Jewel getJewel(int id) throws JewelQuestException{
        switch(id){
            case -2:
                //return new EmptyJewel(config.getBackgroundColor());
                return new BlackJewel();
            case -1:
                return new BlackJewel();
            case 1:
                return new BlueJewel();
            case 2:
                return new GreenJewel();
            case 3:
                return new RedJewel();
            case 4:
                return new YellowJewel();
            default:
                throw new JewelQuestException(JewelQuestException.RANDOM_ERROR);
        }
    }

    /**
     * Method for inserting a jewell on a given position
     * @param x 'X' position for the new jewel
     * @param y 'Y' position for the new jewel
     * @param jewel The jewel to be inserted
     * @return
     */
    private void insertJewellToBoard(int x, int y, Jewel jewel){
        board[x][y] = jewel;
    }

    /**
     * Method for adding actions to the labels of the game board
     * @param x 'X' position of the jewel
     * @param y 'Y' position of the jewel
     * @return
     */
    private void addActionToButton(int x, int y){
        board[x][y].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                deleteJewel(e);
            }
        });
    }

    /**
     * Method for deleting jewels
     * @param event The event that triggered the action
     */
    private void deleteJewel(MouseEvent event){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j] == event.getSource()){
                    logicBoard.deleteJewels(i, j, logicBoard.getBoard()[i][j]);
                }
            }

        }

        this.refresh();
    }

    /**
     * Method for refreshing the board
     * @return
     */
    private void refresh(){
        remove(boardPanel);
        this.createGameBoardPanel();
        repaint();
    }

    /**
     * Method for restarting the game
     * @return
     */
    private void restartGame(){
        logicBoard = null;

        new GameBoard(this.width, this.height);

        this.refresh();
    }

    /**
     * Method for going back to the main menu
     * @return
     */
    private void toggleMainMenu(){
        JewelQuestGUI.selectCard(JewelQuestGUI.MAIN_MENU);
    }



    public void setGameRunning(boolean gameRunning) {
        isGameRunning = gameRunning;
    }
}
