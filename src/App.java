public class App {
    private static boolean solve(Piece[] pieces) {
        Board board = new Board();
        int curPiece = 0;
        boolean[] placed = new boolean[pieces.length];
        int nPlaced = 0;
        boolean running = true;
        boolean solved = false;

        while (running) {
            if (placed[curPiece]) {                         // If the current piece is placed on the board, remove it
                board.removePiece(pieces[curPiece]);
                placed[curPiece] = false;
                nPlaced--;
            }
            pieces[curPiece].toNextPosition();              // Move the current piece to its next position
            if (pieces[curPiece].getCurPos() >= 0) {        // If the next position wasn't "not positioned"
                if (board.tryToPlace(pieces[curPiece])) {   //   Try to place it. If that works
                    placed[curPiece] = true;
                    nPlaced++;
                    if (nPlaced >= pieces.length) {         //     Print the state of the board if enough pieces placed
                        System.out.printf("\nPlaced: %d\n", nPlaced);
                        System.out.println(board);
                        solved = true;                      //      And note there was a solution
                    }
                    if (curPiece < pieces.length - 1) {     //     If not the highest numbered piece, switch to working
                        curPiece++;                         //       on the next higher numbered piece.
                    }
                }
            } else {                                        // Otherwise (current piece moved to "not positioned")
                if (curPiece == 0) {
                    running = false;                        //   If it was piece 0, we're done.
                } else {
                    curPiece--;                             //   Otherwise, start looking for a place for the next lower piece
                }
            }
        }
        return solved;
    }

    /***
     * The "guts" of the piece combinations generator comb() only invoked from there
     * Many thanks to https://www.geeksforgeeks.org/print-all-possible-combinations-of-r-elements-in-a-given-array-of-size-n/
     * for the algorithm.
     * 
     * @param pieces    The array containing the Pieces
     * @param curComb   The array used as a buffer in which to generate the combinations
     * @param start     Current starting index in pieces
     * @param end       Current ending index in pieces
     * @param index     Current index in curComb
     */
    private static void combUtil(
        Piece pieces[], Piece curComb[], int start, int end, int index) {
        int m = curComb.length;
        if (index == m) {                       // If curComb is ready,
            //   Find all the solutions to the puzzle using that combination of pieces
            if (solve(curComb)) {               //   If we found at least one
                for (int j = 0; j < m; j++) {   //   Print the combination
                    System.out.print(curComb[j].pName + " ");
                }
                System.out.println("");
            } else {
                System.out.print("*");       // Show we're alive
            }
            return;
        }
 
        // Replace index with all possible elements. The condition
        // "end - i + 1 >= m - index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i = start; i <= end && end - i + 1 >= m - index; i++) {
            curComb[index] = pieces[i];
            combUtil(pieces, curComb, i + 1, end, index + 1);
        }
    }

    /****
     * Piece combination generator. Generate all combinations of 
     * Pieces pieces taken m at a time and, for each, search for 
     * all possible solutions.
     * @param pieces
     * @param m
     */
    private static void comb(Piece[] pieces, int m) {
 
        Piece curComb[]=new Piece[m];
 
        combUtil(pieces, curComb, 0, pieces.length-1, 0);
    }

    public static void main(String[] args) throws Exception {
        Piece[] pieces = Piece.makePieces();

        comb(pieces, 16);
    }
}
