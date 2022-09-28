import java.util.*;
public class Board {
    char[][] field = new char[10][8];       // The cells of the board: 8 wide and 6 high 
                                            // with 2-deep "margin cells" at right and bottom

    /****
     * 
     * Constructor
     * 
     ****/
    public Board(){
        for (char[] row : field) {
            Arrays.fill(row, ' ');
        }
    }

    /****
     * Attempt to place Piece p in its current position on the Board. If the cells it would occupy 
     * are not currently empty, do nothing and return false. Else place the Piece on the Board and 
     * return true.
     * 
     * @param p     The Piece to be placed.
     * @return      Return true if it fits, false if not
     */
    public boolean tryToPlace(Piece p) {
        byte[][] cells = p.getPieceCells();
        if (cells == null) {
            return false;
        }
        for (byte[] loc : cells) {
            if (field[loc[0]][loc[1]] != ' ') {
                return false;
            }
        }
        for (byte[] loc : cells) {
            field[loc[0]][loc[1]] = p.pName;
        }
        return true;
    }

    /***
     * Remove Piece p from the board. If the piece isn't on the board, there's an
     * error and the assertion will fail.
     * 
     * @param p     The piece to remove
     */
    public void removePiece(Piece p) {
        byte[][] cells = p.getPieceCells();
        for (byte[] loc : cells) {
            assert field[loc[0]][loc[1]] == p.pName : "Attempted to remove a Piece not at expected location.";
            field[loc[0]][loc[1]] = ' ';
        }
    }

    /**
     * Return the string reperesentation of the Board
     */
    public String toString() {
        String answer = "\nBoard:\n ********\n";
        for (int y = 0; y < field[0].length - 2; y++) {
            answer += "*";
            for (int x = 0; x < field.length - 2; x++) {
                answer += field[x][y];
            }
            answer += "*\n";
        }
        answer += " ********\n";
        return answer;
    }
}
