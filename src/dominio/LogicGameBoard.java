package dominio;

import presentacion.JewelQuestGUI;

import javax.swing.*;
import java.util.Arrays;
import java.util.Random;

public class LogicGameBoard {
    private int width;
    private int height;

    private int[] firstCell;
    private int[] secondCell;

    private int movements = 0;
    private int points = 0;

    private int[][] board;
    private int[][] boardCopy;
    private int[][] winBoard;

    private Random random;



    /**
     * Constructor fot the logicGameBoard class
     * @param width The number of cells of a row of the matrix
     * @param height The number of rows of the matrix
     */
    public LogicGameBoard(int width, int height) {
        this.width = width;
        this.height = height;

        System.out.println("columnas: " + this.width + " filas: " + this.height);

        this.board = new int[height][width];
        this.winBoard = new int[height][width];
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

                // Win board
                this.winBoard[i][j] = 0;
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

            // Win board

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
     * Method for turning one and only one jewel black
     */
    public void turnBlack(int x, int y){
        if(this.jewelsToDestroy() != 1){
            this.board[x][y] = -1;
        } else {
            this.resetBoard();
            this.board[x][y] = -1;
        }
    }

    /**
     * Method prepare for destroy
     * @param x 'X' position of the jewel to be destroyed
     * @param y 'Y' position of the jewell to be destroyed
     */
    private void prepareForDestroy(int x, int y){
        this.board[x][y] = -1;
    }

    /**
     * Method for verifying if the cells are adjacent
     * @return true if the cells are next to each other, false otherwise
     */
    public boolean verifyAdjacency(){
        int[] first = this.getFirstCell();
        int[] second = this.getSecondCell();

        int xDiff = Math.abs(second[0] - first[0]);
        int yDiff = Math.abs(second[1] - first[1]);

        return (xDiff == 1 || yDiff == 1) && xDiff != yDiff && (xDiff < 2) && (yDiff < 2);
    }

    /**
     * Method for verifying gold positions
     * @return true if the player won, false otherwise
     */
    private boolean verifyWinBoard(){
        int result = 0;

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if(this.board[i][j] == -2){
                    this.winBoard[i][j] = 1;
                }

                if(this.getWinBoard()[i][j] == 1){
                    result++;
                }
            }
        }

        System.out.println("\n\n");

        for(int[] row : this.getWinBoard()){
            System.out.println(Arrays.toString(row));
        }

        System.out.println("\n\n");

        return result == (this.width * this.height);
    }



    /**
     * Method for switching jewels
     */
    public void switchPositions(){
        int[] first = this.getFirstCell();
        int[] second = this.getSecondCell();

        this.board[first[0]][first[1]] = this.getBoardCopy()[second[0]][second[1]];
        this.board[second[0]][second[1]] = this.getBoardCopy()[first[0]][first[1]];

        this.copyBoard();
    }

    /**
     * Method for undo the switch positions
     */
    public void undoSwitchPositions(){
        int[] first = this.getSecondCell();
        int[] second = this.getFirstCell();

        this.board[first[0]][first[1]] = this.getBoardCopy()[second[0]][second[1]];
        this.board[second[0]][second[1]] = this.getBoardCopy()[first[0]][first[1]];

        this.copyBoard();
    }



    /**
     * Is the black jewel clicked
     * @param x 'X' position of the click
     * @param y 'Y' position of the click
     * @param jewelType The jewel type
     */
    public void blackClicked(int x, int y, int jewelType){
        boolean winner = false;

        // First jewel
        int x1 = this.getFirstCell()[0];
        int y1 = this.getFirstCell()[1];

        this.deleteJewels(x1, y1, this.board[x1][y1]);

        // Second jewel
        int x2 = this.getSecondCell()[0];
        int y2 = this.getSecondCell()[1];

        this.deleteJewels(x2, y2, this.board[x2][y2]);

        this.printBoard();

        if (this.board[x][y] == -1){
            System.out.println("To destroy: " + this.jewelsToDestroy());
            if (this.jewelsToDestroy() > 2){
                // Points
                this.setPoints(this.getPoints() + this.jewelsToDestroy());

                // Movements
                this.setMovements(this.getMovements() + 1);

                // Delete
                this.deleteJewels(x, y, -1);

                // Verify win board
                winner = this.verifyWinBoard();

                // Make them fall
                this.makeThemFall();

                // New Jewels
                this.rePopulateBoard();

                this.printBoard();

                //System.out.println("Mov: " + this.getMovements());
                //System.out.println("Pts: " + this.getPoints());
            } else {
                this.undoSwitchPositions();
                this.resetBoard();
                this.printBoard();
            }
        } else {
            this.resetBoard();
            //this.deleteJewels(x, y, jewelType);
        }

        if(winner){
            JOptionPane.showMessageDialog(null,
                    "¡Felicidades! Ha ganado el juego" + "\nMovimientos: " + this.getMovements()
            + "\nPuntos: " + this.getPoints());

            // Set game won
            JewelQuestGUI.gameWon = true;

            // Go to main menu
            JewelQuestGUI.selectCard(JewelQuestGUI.MAIN_MENU);
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

    public int getMovements() {
        return movements;
    }

    public void setMovements(int movements) {
        this.movements = movements;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int[] getFirstCell() {
        return firstCell;
    }

    public void setFirstCell(int[] firstCell) {
        this.firstCell = firstCell;

        System.out.println("First cell: " + firstCell[0] + " - " + firstCell[1]);
    }

    public int[] getSecondCell() {
        return secondCell;
    }

    public void setSecondCell(int[] secondCell) {
        this.secondCell = secondCell;
        System.out.println("Second cell: " + secondCell[0] + " - " + secondCell[1]);
    }

    public int[][] getWinBoard() {
        return winBoard;
    }
}
