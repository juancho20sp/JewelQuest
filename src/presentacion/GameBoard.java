package presentacion;

import dominio.GameConfiguration;
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

    private int rows;
    private int cols;

    private int width;
    private int height;
    private boolean isGameRunning;
    private boolean firstCellSelected;

    private Random random;

    private GameConfiguration config;

    private static LogicGameBoard logicBoard;
    //private Color boardColor = ModifyBoard.boardColor;
    public static boolean gameRunning = false;
    private static Jewel board[][];

    /**
     * Constructor of the GameBoard class
     * @param height Height of the main frame
     * @param width Width of the main frame
     * @param config The object that handles all the modifications of the board
     */
    public GameBoard(int width, int height, GameConfiguration config){
        this.width = width - 18;
        this.height = height - 38;
        this.config = config;

        new JPanel();

        //this.mainPanel = new JPanel();

        // Rows and cols
        this.rows = this.config.getHeight();
        this.cols = this.config.getWidth();

        System.out.println("Height: " + this.config.getHeight());
        System.out.println("width: " + this.config.getWidth());

        gameRunning = true;
        this.setGameRunning(false);
        this.setFirstCellSelected(false);

        // Random
        this.random = new Random();

        // Logic board
        System.out.println("GUI filas: " + this.rows + " cols: " + this.cols);
        logicBoard = new LogicGameBoard(this.cols, this.rows);

        System.out.println("\n\n");
        logicBoard.printBoard();
        System.out.println("\n\n");

        // Create the jewel board
        //board = new Jewel[this.rows][this.cols];

        this.prepareElementsBoard();
    }

    /**
     * Constructor for resuming the game
     */
    public GameBoard(GameConfiguration config){
        this.config = config;

        // Rows and cols
        this.rows = this.config.getHeight();
        this.cols = this.config.getWidth();

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
     */
    private void createUpperPanel(){
        // Panel
        this.upperPanel = new JPanel();
        this.upperPanel.setBounds(0, 0, this.width, 30);
        this.upperPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        this.upperPanel.setLayout(new GridLayout(1, 4));
        this.upperPanel.setBackground(this.config.getBackgroundColor());

        // Buttons
        this.createUpperPanelButtons();

        // Labels
        this.createUpperPanelLabels();

        // Actions
        this.createUpperPanelActions();

        // Add
        this.addElementsToUpperPanel();

    }

    /**+
     * Method for adding actions to the upper panel buttons
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
     * Method for creating the upper panel buttons
     */
    private void createUpperPanelButtons(){
        this.mainMenuButton = new JButton("Menú principal");
        this.restartButton = new JButton("Reiniciar juego");
    }

    /**
     * Method for creating the upper panel labels
     */
    private void createUpperPanelLabels(){
        this.setMovementsLabel(new JLabel("Movimientos: " + logicBoard.getMovements(), SwingConstants.CENTER));
        this.setPointsLabel( new JLabel("Puntos: " + + logicBoard.getPoints(),
                SwingConstants.CENTER));
    }

    /**
     * Method for adding elements to the upper panel
     */
    private void addElementsToUpperPanel(){
        // Elements
        this.upperPanel.add(mainMenuButton);
        this.upperPanel.add(restartButton);
        this.upperPanel.add(this.getMovementsLabel());
        this.upperPanel.add(this.getPointsLabel());

        // Add panel
        add(upperPanel);
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
        this.boardPanel.setBackground(this.config.getBackgroundColor());
        this.boardPanel.setBounds(0, 30, this.width, this.height - 30);
        //this.boardPanel.setAlignmentX(SwingConstants.CENTER);

        // Box dimensions
        int boxWidth = (this.width) / this.cols;
        int boxHeight = (this.height - 40) / this.rows;

        // Layout
        this.boardPanel.setLayout(new GridLayout(this.rows, this.cols));

        // Fill board
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                System.out.println(i + " - " + j);

                // Select and add the jewel
                try {
                    this.insertJewellToBoard(i, j, this.getJewel(logicBoard.getBoard()[i][j]));
                } catch(JewelQuestException e){
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }

                // Label config
                //board[i][j].setPreferredSize(new Dimension(boxWidth - 10, boxHeight - 10));
               // board[i][j].setBounds(i * boxWidth, j * boxHeight, boxWidth, boxHeight);

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
                return new EmptyJewel(config.getBackgroundColor());
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
                handleClickOnJewel(e);
            }
        });
    }

    /**
     * Method for capturing the click event on each jewel
     * @param event The click event
     */
    private void handleClickOnJewel(MouseEvent event){
        int[] position = new int[2];


        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j] == event.getSource()){
                    position[0] = i;
                    position[1] = j;

                    if(!this.isFirstCellSelected()){
                        logicBoard.setFirstCell(position);
                        logicBoard.turnBlack(position[0], position[1]);

                        this.setFirstCellSelected(true);
                    } else {
                        logicBoard.setSecondCell(position);
                        logicBoard.resetBoard();

                        this.switchJewels();
                        this.setFirstCellSelected(false);
                    }
                }
            }
        }

        this.refresh();
    }

    /**
     * Method for switching positions
     */
    private void switchJewels(){
        if (logicBoard.verifyAdjacency()){
            // Switch positions
            logicBoard.switchPositions();

            // Validate possible affected cells
            this.deleteJewel(logicBoard.getSecondCell()[0], logicBoard.getSecondCell()[1]);

        } else {
            JOptionPane.showMessageDialog(null, JewelQuestException.NON_ADJACENT_CELLS);
        }
        System.out.println("Adyacentes: " + logicBoard.verifyAdjacency());

        this.refresh();
    }


    /**
     * Method for deleting jewels
     * @param i 'X' position of the main jewel
     * @param j 'Y' position of the main jewel
     */
    private void deleteJewel(int i, int j){
        logicBoard.blackClicked(i, j, logicBoard.getBoard()[i][j]);

        this.refresh();
    }



    /**
     * Method for refreshing the board
     */
    private void refresh(){
        //remove(upperPanel);
        //remove(boardPanel);
        removeAll();
        revalidate();
        repaint();

        //this.boardPanel.invalidate();
        //this.boardPanel.validate();
        //this.createUpperPanel();

        //this.createUpperPanelLabels();
        //this.addElementsToUpperPanel();
        //this.createGameBoardPanel();
        //this.boardPanel.setLayout(new GridLayout(this.rows, this.cols));
        this.prepareElementsBoard();
        //repaint();
        //this.movementsLabel = new JLabel("test");

        //this.upperPanel.setBackground(this.config.getBackgroundColor());
        //repaint();

//        repaint();


       // this.boardPanel.setBackground(this.config.getBackgroundColor());
    }

    /**
     * Method for restarting the game
     * @return
     */
    private void restartGame(){
        logicBoard = null;

        new GameBoard(this.width, this.height, this.config);

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

    public boolean isFirstCellSelected() {
        return firstCellSelected;
    }

    public void setFirstCellSelected(boolean firstCellSelected) {
        this.firstCellSelected = firstCellSelected;
    }

    public JLabel getMovementsLabel() {
        return movementsLabel;
    }

    public void setMovementsLabel(JLabel movementsLabel) {
        this.movementsLabel = movementsLabel;
    }

    public JLabel getPointsLabel() {
        return pointsLabel;
    }

    public void setPointsLabel(JLabel pointsLabel) {
        this.pointsLabel = pointsLabel;
    }
}
