/****
 * 
 * This file is a part of the Pentagon Tiling Puzzle. See README.md for details.
 * 
 * This file implements the Board class
 * 
 * =====
 *
 *  @file     Board.java 
 * 
 *  @version  Version 1.0.0, October 2022
 *
 *  @author   D. L. Ehnebuske
 *
 *  @section  license
 *
 *  Software License Agreement (BSD License)
 *
 *  Copyright (c) 2022 by D. L. Ehnebuke All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 * 
 *    1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 
 *    2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 
 *    3. Neither the name of the copyright holders nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS ''AS IS'' AND ANY
 *  EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY
 *  DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
****/
import java.util.*;

public class Board {

    public static final int WIDTH = 8;                          // The width of a board in cells
    public static final int HEIGHT = 6;                         // The height of a board in cells
    public static final int MARGIN = 2;                         // How much margin is needed on the right and bottom (cells)
    public static final int CAPACITY = (WIDTH * HEIGHT) / 3;    // Number of Pieces on a full Board

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

    /****
     * Find all of the possible solutions to a puzzle based on the given set of 
     * 16 Pieces
     * 
     * @param pieces    The set of 16 pieces to use for the solution(s)
     * @return          Returns true if at least one solution was found, false if not
     */
    public boolean solveForSet(Piece[] pieces) {
        assert pieces.length == 16 : "The size of set of Pieces isn't the expected 16.";
        int curPiece = 0;
        boolean[] placed = new boolean[pieces.length];
        int nPlaced = 0;
        boolean running = true;
        boolean solved = false;
        while (running) {
            if (placed[curPiece]) {                         // If the current piece is placed on the board, remove it
                removePiece(pieces[curPiece]);
                placed[curPiece] = false;
                nPlaced--;
            }
            pieces[curPiece].toNextPosition();              // Move the current piece to its next position
            if (pieces[curPiece].getCurPos() >= 0) {        // If the next position wasn't "not positioned"
                if (tryToPlace(pieces[curPiece])) {   //   Try to place it. If that works
                    placed[curPiece] = true;
                    nPlaced++;
                    if (nPlaced >= pieces.length) {         //     Print the state of the board if enough pieces placed
                        System.out.print("\n");
                        System.out.println(toString());
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

    /****
     * Generate all the combinations of Pieces from piece that fit into curComb (i.e., all the combinations of pieces.length 
     * taken curComb.length at a time), and for each, find all the solutions using the combination of Pieces in cueComb.
     * 
     * Many thanks to https://www.geeksforgeeks.org/print-all-possible-combinations-of-r-elements-in-a-given-array-of-size-n/
     * for publishing the algorithm.
     * 
     * @param pieces    The array containing the full set of Pieces
     * @param curComb   The array used as a buffer in which to generate the combinations
     * @param start     Current starting index in pieces[] (Set this to 0 initially)
     * @param end       Current ending index in pieces[] (Set this to pieces.length - 1 initially)
     * @param index     Current index in curComb[] (Set this to 0 initially)
     */
    private void combUtil(
        Piece pieces[], Piece curComb[], int start, int end, int index) {
        int m = curComb.length;
        if (index == m) {                       // If curComb is ready,
            //   Find all the solutions to the puzzle using curComb combination of pieces
            if (solveForSet(curComb)) {         //   If we found at least one
                for (int j = 0; j < m; j++) {   //      Print the curComb combination
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
     * Find all the solutions to the puzzle using any combination of Board.CAPACITY 
     * Pieces from full set of Pieces plus extras (which will be repeats, of course) as 
     * requested.
     * 
     * @param extras    The numbers of the Pieces to repeat in the set of Pieces to be used in
     *                  solving the puzzle. -1 ==> Don't include a repeated Piece.
     */
    public void solve(int[] extras) {
 
        Piece[] pieces = Piece.makePieces(extras);      // Make the full set of possible pieces
        Piece curComb[]=new Piece[Board.CAPACITY];      // A buffer to hold the various subsets of pieces
                                                        //  we'll be using to try to solve the puzzle
 
        combUtil(pieces, curComb, 0, pieces.length - 1, 0);
                                                        // Generate the subsets of the full set of pieces
                                                        //  and for each, find all possible solutions (if any)
    }

    /****
     * A slightly nicer single extra Piece version of solve(Pieces[] piece). Find all the 
     * solutions to the puzzle using any combination of Board.CAPACITY Pieces from full set 
     * of Pieces plus extra (which will be a repeat, of course).
     * 
     * @param extra     The number of the Piece to repeat in the set of Pieces to be used in
     *                  solving the puzzle. -1 ==> Don't include a repeated Piece.
     * 
     */
    public void solve(int extra) {
        int[] extras = {extra};
        solve(extras);
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
