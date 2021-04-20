package dominio;

import java.util.Arrays;
import java.util.Random;

public class LogicGameBoard {
    private int width;
    private int height;
    private int[][] board;
    private Random random;

    /**
     * Constructor fot the logicGameBoard class
     * @param width The number of cells of a row of the matrix
     * @param height The number of rows of the matrix
     */
    public LogicGameBoard(int width, int height) {
        this.width = width;
        this.height = height;

        this.board = new int[width][height];
        this.random = new Random();

        this.createBoard();
    }

    /**
     * Method for creating the board
     */
    private void createBoard(){
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.board[i][j] = random.nextInt(4) + 1;
            }
        }
    }

    /**
     * Method for printing the board
     */
    public void printBoard(){
        for(int[] row: this.board){
            System.out.println(Arrays.toString(row));
        }
    }

    /**
     * Method for deleting similar jewels
     * @param px 'X' position of the jewel
     * @param py 'Y' position of the jewel
     * @param jewelType Type of the jewell
     */
    public void deleteJewels(int px, int py, int jewelType){
        // The jewels to delete will turn black
        if (jewelType != -1){
            board[px][py] = -1;
        }

        // If the color is black, delete the jewel
        if (jewelType == -1){
            board[px][py] = -2;
        }

        // Delete adjacent positions
        // Right
        if ((px < (this.width - 1)) && board[px + 1][py] == jewelType){
            deleteJewels(px + 1, py, jewelType);
        }

        // Left
        if ((px > 0) && board[px -1][py] == jewelType){
            deleteJewels(px - 1, py, jewelType);
        }

        // Down
        if ((py < (this.height - 1)) && board[px][py + 1] == jewelType){
            deleteJewels(px, py + 1, jewelType);
        }

        // Up
        if ((py > 0) && board[px][py - 1] == jewelType){
            deleteJewels(px, py - 1, jewelType);
        }

        // Upper left diagonal
        if ((px > 0) && (py > 0) && board[px - 1][py - 1] == jewelType){
            deleteJewels(px - 1, py - 1, jewelType);
        }

        // Upper right diagonal
        if ((px < (this.width - 1) && (py > 0) && (board[px + 1][py - 1]) == jewelType)){
            deleteJewels(px + 1, py - 1, jewelType);
        }
    }

    /**
     * Getter for the board
     * @return The board
     */
    public int[][] getBoard() {
        return board;
    }
}
