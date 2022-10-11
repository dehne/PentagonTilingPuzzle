/****
 * 
 * This file is a part of the Pentagon Tiling Puzzle. See README.md for details.
 * 
 * This file implements the driver program that actually instantiates the Board and Pieces and 
 * uses them to solve the puzzle.
 * 
 * =====
 *
 *  @file     App.java 
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
public class App {
    /****
     * Find all of the possible solutions to a puzzle based on the set of Pieces pieces
     * 
     * @param pieces    The set of 16 pieces to use for the solution(s)
     * @return          Returns true if at least one solution was found, false if not
     */
    private static boolean solveForSet(Piece[] pieces) {
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
                        System.out.print("\n");
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
    private static void combUtil(
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
    private static void solve(int[] extras) {
 
        Piece[] pieces = Piece.makePieces(extras);      // Make the full set of possible pieces
        Piece curComb[]=new Piece[Board.CAPACITY];      // A buffer to hold the various subsets of pieces
                                                        //  we'll be using to try to solve the puzzle
 
        combUtil(pieces, curComb, 0, pieces.length - 1, 0);
                                                        // Generate the subsets of the full set of pieces
                                                        //  and for each, find all possible solutions (if any)
    }

    /****
     * The driver program
     * @param args          Not using these
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int[] extras = new int[1];
        for (int i = Piece.A; i < Piece.N_PIECES; i++) {
            extras[0] = i;
            System.out.printf("\nAdd extra piece %c.\n", (char)(i + 'A'));
            solve(extras);
        }
    }
}
