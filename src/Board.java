/****
 * 
 * This file is a part of the Pentagon Tiling Puzzle. See App.java for details
 * 
 * This file implements the Board class
 * 
 * =====
 *
 *  @file     Board.java 
 * 
 *  @version  Version 1.0.0, September 2022
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
