package dominio;

import java.util.Arrays;
import java.util.Random;

public class LogicGameBoard {
    private int width;
    private int height;
    private int[][] board;
    private int[][] boardCopy;

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

        this.copyBoard();
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
     * @param jewelType Type of the jewel
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
        /*if ((px > 0) && (py > 0) && board[px - 1][py - 1] == jewelType){
            deleteJewels(px - 1, py - 1, jewelType);
        }

        // Upper right diagonal
        if ((px < (this.width - 1) && (py > 0) && (board[px + 1][py - 1]) == jewelType)){
            deleteJewels(px + 1, py - 1, jewelType);
        }

        // Lower left diagonal
        if ((px > 0) && (py < (this.height - 1)) && board[px - 1][py + 1] == jewelType){
            deleteJewels(px - 1, py + 1, jewelType);
        }

        // Lower right diagonal
        if ((px < (this.width - 1)) && (py < (this.height - 1)) && board[px + 1][py + 1] == jewelType) {
            deleteJewels(px + 1, py + 1, jewelType);
        }*/
    }

    /**
     * Method for counting the number of jewels affected
     */
    public int jewelsToDestroy(){
        int total = 0;

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if(this.board[i][j] == -1){
                    total++;
                }
            }
        }

        return total;
    }

    /**
     * Method for adding jewels after they're destroyed
     */
    public void rePopulateBoard(){
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
               if (this.board[i][j] == -2){
                   this.board[i][j] = random.nextInt(4) + 1;
               }
            }
        }

        this.copyBoard();
    }

    /**
     * Is the black jewel clicked
     * @param x 'X' position of the click
     * @param y 'Y' position of the click
     * @param jewelType The jewel type
     */
    public void blackClicked(int x, int y, int jewelType){
        if (this.board[x][y] == -1){
            if (this.jewelsToDestroy() > 2){
                this.deleteJewels(x, y, -1);

                // Make them fall
                this.makeThemFall();

                // New Jewels
                this.rePopulateBoard();

                this.printBoard();
            }
        } else {
            this.resetBoard();
            this.deleteJewels(x, y, jewelType);
        }
    }

    /**
     * Method for letting the upper jewels down
     */
    private void makeThemFall(){
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                for (int k = 0; k < this.height; k++) {
                    if (k > 0 && board[j][k] == -2){
                        board[j][k] = board[j][k-1];
                        board[j][k-1] = -2;
                    }
                }
            }

        }
    }

    /**
     * Method for reseting the board
     */
    public void resetBoard() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.board[i][j] = this.getBoardCopy()[i][j];
            }
        }
    }

    /**
     * Method for verifying if there are black jewels on the board
     */
    public boolean areBlacksOnBoard(){
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.getBoard()[i][j] == -1){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Method for copying the board
     */
    private void copyBoard(){
        int[][] temp = new int[this.height][this.width];

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                temp[i][j] = this.getBoard()[i][j];
            }
        }

        this.setBoardCopy(temp);
    }



    public int[][] getBoard() {
        return board;
    }

    private int[][] getBoardCopy() {
        return boardCopy;
    }

    private void setBoardCopy(int[][] boardCopy) {
        this.boardCopy = boardCopy;
    }
}
