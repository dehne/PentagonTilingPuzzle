import java.util.*;

public class Board {

    public static final int WIDTH = 8;          // The width of a board in cells
    public static final int HEIGHT = 6;         // The height of a board in cells
    public static final int MARGIN = 2;         // How much margin is needed on the right and bottom (cells)

    private char[][] field = new char[WIDTH + MARGIN][HEIGHT + MARGIN]; // The cells of the board
                                                                        // with "margin cells" at right and bottom

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
        for (byte[] loc : cells) {                  // Check to see if it would fit on the board
            if (field[loc[0]][loc[1]] != ' ') {
                return false;
            }
        }
        for (byte[] loc : cells) {                  // Looks good: Place it
            field[loc[0]][loc[1]] = p.pName;
        }
        return true;
    }

    /***
     * Remove Piece p from the board. If the piece isn't on the board, there's an
     * error and the assertion will fail (if enabled, of course).
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
    
    /***
     * Exercise a piece by marching it through each of its positions on the board,
     * printing the board at each positon. At the end, the board is clear.
     * 
     * @param p     The Piece to be exercised
     */
    public void exercisePiece(Piece p) {
        do {
        p.toNextPosition();
        tryToPlace(p);
        if (p.getCurPos() != Piece.NOT_POSITIONED) {
            System.out.printf("Position %d\n", p.getCurPos());
            System.out.println(toString());
            removePiece(p);
        }
    }  while (p.getCurPos() != Piece.NOT_POSITIONED);
}

    /**
     * Return the string reperesentation of the Board
     */
    public String toString() {
        String answer = " ********\n";
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
