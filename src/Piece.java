/****
 * 
 * This file is a part of the Pentagon Tiling Puzzle. See App.java for details
 * 
 * This file implements the Piece class
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
public class Piece {
    /****
     * 
     * The shapes and orientations of those shapes the pieces can have possibly have. 
     * A shape is a 3 x 3 array telling whether one of the parts of a piece occupy a 
     * cell on the board. 1 => cell occupied by a piece with this shape; 0 => cell not 
     * occupied.
     * 
     ****/

    // column selector --------------------.
    // row selector --------------------.  |
    // shape selector ---------------.  |  |
    //                               |  |  |
    private static final byte shapes[ ][ ][ ] = {
        // I-shape
        {               // 0 (Face up I)
            {1, 1, 1},  // ***
            {0, 0, 0},
            {0, 0, 0}
        },
        {               // 1 (Normal I)
            {1, 0, 0},  // *
            {1, 0, 0},  // *
            {1, 0, 0}   // *
        },

        // L-shape
        {               // 2 (Upside down backwards L)
            {1, 1, 0},  // **
            {1, 0, 0},  // *
            {0, 0, 0}
        },
        {               // 3 (Upside down L)
            {1, 1, 0},  // **
            {0, 1, 0},  //  *
            {0, 0, 0}
        },
        {               // 4 (Backwards L)
            {0, 1, 0},  //  *
            {1, 1, 0},  // **
            {0, 0, 0}
        },
        {               // 5 (Normal L)
            {1, 0, 0},  // *
            {1, 1, 0},  // **
            {0, 0, 0}
        },

        // J-shape
        {               // 6 (Face-down J)
            {1, 1, 0},  // **
            {0, 0, 1},  //   *
            {0, 0, 0}
        },
        {               // 7 (Normal J)
            {0, 1, 0},  //  *
            {0, 1, 0},  //  *
            {1, 0, 0}   // *
        },
        {               // 8 (Face-up J)
            {1, 0, 0},  // *
            {0, 1, 1},  //  **
            {0, 0, 0}
        },
        {               // 9 (Upside down J)
            {0, 1, 0},  //  *
            {1, 0, 0},  // *
            {1, 0, 0}   // *
        },
        {               // 10 (Face-up backwards J)
            {0, 0, 1},  //   *
            {1, 1, 0},  // **
            {0, 0, 0}
        },
        {               // 11 (Backwards J)
            {1, 0, 0},  // *
            {1, 0, 0},  // *
            {0, 1, 0}   //  *
        },
        {               // 12 (Face-down backwards J)
            {0, 1, 1},  //  **
            {1, 0, 0},  // *
            {0, 0, 0}
        },
        {               // 13 (Upside down backwards J)
            {1, 0, 0},  // *
            {0, 1, 0},  //  *
            {0, 1, 0}   //  *
        }
    };

    /****
     * The possible positions of all the pieces. These were determined 
     * by inspecting where each of the pieces could possibly fit on the
     * board. 
     ****/

    // position component selector -----------.     0 => shape, 1 => x, 2 => y
    // position selector ------------------.  |     0..number of possible positions - 1
    // piece selector ------------------.  |  |     0 => Piece A, 1 => Piece B, ... 15 => Piece P
    //                                  |  |  |
    private final static byte positions[ ][ ][ ] = {
        {   // Piece A
//           .------------- Shape selector
//           |  .---------- x position
//           |  |  .------- y position
//           |  |  |            
            {1, 2, 0},  // Normal I at (2, 0)
            {1, 6, 0},  //  " @ (6, 0)
            {1, 0, 2},  //  " @ (0, 2)
            {1, 4, 2},  //  " @ (4, 2)
            {0, 3, 1},  // Face-up I at (3, 1)
            {0, 1, 3},  //   " @ (1, 3)
            {0, 5, 3},  //   " @ (5, 3)
            {0, 3, 5}   //   " @ (3, 5)
        },
        {   // Piece B
            {2, 3, 0},  // Upside down backwards L (3, 0)
            {2, 0, 1},  // 
            {2, 4, 1},  // 
            {2, 1, 2},  // 
            {2, 5, 2},  // 
            {2, 2, 3},  // 
            {2, 6, 3},  // 
            {2, 3, 4},  // 
            {4, 1, 0},  // Backwards L
            {4, 5, 0},  // 
            {4, 2, 1},  // 
            {4, 6, 1},  // 
            {4, 3, 2},  // 
            {4, 0, 3},  // 
            {4, 4, 3},  // 
            {4, 1, 4},  // 
            {4, 5, 4}   // 
        },
        {   // Piece C
            {3, 1, 0},  // Upside down L
            {3, 0, 1},
            {3, 4, 1},
            {3, 3, 2},
            {3, 2, 3},
            {3, 6, 3},
            {3, 1, 4},
            {3, 5, 4},
            {5, 3, 0},  // Normal L
            {5, 2, 1},
            {5, 6, 1},
            {5, 1, 2},
            {5, 5, 2},
            {5, 0, 3},
            {5, 4, 3},
            {5, 3, 4},
        },
        {   // Piece D
            {8, 1, 1},  // Face-up J
            {8, 5, 1},
            {8, 3, 3},
            {13, 3, 1}, // Up side down backwards J
            {13, 1, 3},
            {13, 5, 3},
            {6, 0, 1},  // Face-down J
            {6, 4, 1},
            {6, 2, 3},
            {11, 3, 0}, // Backwards J
            {11, 1, 2},
            {11, 5, 2}
        },
        {   // Piece E
            {1, 3, 0},  // Normal I
            {1, 7, 0},
            {1, 1, 2},
            {1, 5, 2},
            {0, 0, 1},  // Face-up I
            {0, 4, 1},
            {0, 2, 3},
            {0, 0, 5},
            {0, 4, 5}
        },
        {   // Piece F
            {2, 0, 0},  // Upside down backwards L
            {2, 4, 0},
            {2, 2, 2},
            {2, 6, 2},
            {2, 0, 4},
            {2, 4, 4},
            {4, 2, 0},  // Backwards L
            {4, 6, 0},
            {4, 0, 2},
            {4, 4, 2},
            {4, 2, 4},
            {4, 6, 4}
        },
        {   // Piece G
            {5, 0, 0},  // Normal L
            {5, 4, 0},
            {5, 2, 2},
            {5, 6, 2},
            {5, 0, 4},
            {5, 4, 4},
            {3, 2, 0},  // Upside down L
            {3, 6, 0},
            {3, 0, 2},
            {3, 4, 2},
            {3, 2, 4},
            {3, 6, 4}
        },
        {   // Piece H
            {2, 1, 0},  // Upside down backwards L (batch 1)
            {2, 5, 0},
            {2, 3, 2},
            {2, 1, 4},
            {2, 5, 4},
            {2, 2, 1},  //   " (batch 2)
            {2, 6, 1},
            {2, 0, 3},
            {2, 4, 3},
            {4, 3, 0},  // Backwards L (batch 1)
            {4, 1, 2},
            {4, 5, 2},
            {4, 3, 4},
            {4, 0, 1},  //   " (batch 2)
            {4, 4, 1},
            {4, 2, 3},
            {4, 6, 3}
        },
        {   // Piece I
            {9, 2, 0},  // Upside down J
            {9, 6, 0},
            {9, 0, 2},
            {9, 4, 2},
            {10, 3, 0}, // Face-up backwrds J
            {10, 1, 2},
            {10, 5, 2},
            {10, 3, 4},
            {12, 0, 0}, // Face-down backwards J
            {12, 4, 0},
            {12, 2, 2},
            {12, 0, 4},
            {12, 4, 4},
            {7, 0, 1},  // Normal J
            {7, 4, 1},
            {7, 2, 3},
            {7, 6, 3}
        },
        {   // Piece J
            {5, 1, 1},  // Normal L
            {5, 5, 1},
            {5, 3, 3},
            {3, 3, 1},  // Upside down L
            {3, 1, 3},
            {3, 5, 3}
        },
        {   // Piece K
            {4, 1, 1},  // Backwards L
            {4, 5, 1},
            {4, 3, 3},
            {2, 3, 1},  // Upside down backwards L
            {2, 1, 3},
            {2, 5, 3}
        },
        {   // Piece L
            {0, 1, 0},  // Face up I
            {0, 2, 1},
            {0, 3, 2},
            {0, 0, 3},
            {0, 4, 3},
            {0, 1, 4},
            {0, 5, 4},
            {0, 2, 5}
        },
        {   // Piece M
            {13, 1, 1}, // Upside down backwards J
            {13, 5, 1},
            {13, 3, 3},
            {8, 3, 1},  // Face-up J
            {8, 1, 3},
            {8, 5, 3},
            {11, 1, 0}, // Backward J
            {11, 5, 0},
            {11, 3, 2},
            {6, 2, 1},  // Face-down J
            {6, 0, 3},
            {6, 4, 3}
        },
        {   // Piece N
            {0, 2, 0},  // Face-up I
            {0, 0, 2},
            {0, 4, 2},
            {0, 2, 4},
            {1, 3, 1},  // Normal I
            {1, 7, 1},
            {1, 1, 3},
            {1, 5, 3},
        },

        {   // Piece O
            {12, 2, 0}, // Face-down backward J
            {12, 0, 2},
            {12, 4, 2},
            {12, 2, 4},
            {7, 2, 1},  // Normal J
            {7, 6, 1},
            {7, 0, 3},
            {7, 4, 3},
            {9, 0, 0},  // Upside down J
            {9, 4, 0},
            {9, 2, 2},
            {9, 6, 2},
            {10, 1, 0}, // Face-up backwards J
            {10, 5, 0},
            {10, 3, 2},
            {10, 1, 4},
            {10, 5, 4}
        },

        {   // Piece P
            {5, 1, 0},  // Normal L
            {5, 5, 0},
            {5, 0, 1},
            {5, 4, 1},
            {5, 3, 2},
            {5, 2, 3},
            {5, 6, 3},
            {5, 1, 4},
            {3, 3, 0},  // Upside down L
            {3, 2, 1},
            {3, 6, 1},
            {3, 1, 2},
            {3, 5, 2},
            {3, 0, 3},
            {3, 4, 3},
            {3, 3, 4}
        },
        {   // Piece Q
            {1, 0, 0},  // Normal I
            {1, 4, 0},
            {1, 2, 2},
            {1, 6, 2},
            {0, 1, 1},  // Face-up I
            {0, 5, 1},
            {0, 3, 3},
            {0, 1, 5},
            {0, 5, 5}
        }
    };

    /****
     * The symbolic names of the Pieces
     ****/
    public final static int NONE = -1;  // Indication to makePieces that no repeated Piece is wanted
    public final static int A = 0;
    public final static int B = 1;
    public final static int C = 2;
    public final static int D = 3;
    public final static int E = 4;
    public final static int F = 5;
    public final static int G = 6;
    public final static int H = 7;
    public final static int I = 8;
    public final static int J = 9;
    public final static int K = 10;
    public final static int L = 11;
    public final static int M = 12;
    public final static int N = 13;
    public final static int O = 14;
    public final static int P = 15;
    public final static int Q = 16;
    public final static int R = 17;

    /****
     * The number of distinct Pieces
     ****/
    public final static int N_PIECES = positions.length;

    /****
     * The location of a Piece when not positioned
     ****/
    public final static int NOT_POSITIONED = -1;

    /****
     * Return the number of positions a given Piece has
     * 
     * @param pNo   The number of the Piece being asked about
     * @return      The number of positions Piece pNo has
     ****/
    public static int positionsFor(int pNo) {
        return positions[pNo].length;
    }

    /****
     * Factory method to create the collection of 16 Piece objects.
     * 
     * 
     * @param pNo   The number of the Piece to repeat in the set of Pieces to be used in
     *              solving the puzzle. -1 ==> Don't include a repeated Piece.
     * @return      Piece[] containing the instantiated pieces
     ****/
    public static Piece[] makePieces(int pNo) {
        Piece[] answer = new Piece[pNo == -1 ? N_PIECES : N_PIECES + 1];
        for (int i = 0; i < N_PIECES; i++) {
            answer[i] = new Piece(i);
        }
        if (pNo != -1) {
            answer[N_PIECES] = new Piece(pNo);
        }
        return answer;
    }

    public final char pName;    // The name of the Piece: 'A'..'P'
    public final int pNum;      // The number of the Piece 0..15
    private int curPos;         // The current position of the Piece on the board: 
                                //   -1 => not placed, 0..positions[number].length - 1
    
    /****
     * Constructor for Piece. It's private and meant to be invoked
     * by the factory class method makePieces.
     * 
     * @param no    The number of the piece to be instantiated: 0..15
     ****/
    private Piece(int no) {
        pName = (char)(no + 'A');
        pNum = no;
        curPos = -1;
    }

    /****
     * getPieceCells()  Return the coordinates on the board of the three cells occupied 
     *                  by this piece at its current position. Returns null if the piece 
     *                  is not currently at a place on the board
     * 
     * @return  byte[][] The x, y location of each of the three occupied cells for
     *          the current position.
     ****/
    public byte[][] getPieceCells() {
        if (curPos <= -1) {
            return null;
        }
        byte[][] answer = new byte[3][2];
        int cellCount = 0;
        for (byte dy = 0; dy < 3 && cellCount < 3; dy++) {
            for (byte dx = 0; dx < 3 && cellCount < 3; dx++) {
                byte[][] ourShape = shapes[positions[pNum][curPos][0]]; // Get the shape we have for our current position
                if (ourShape[dy][dx] == 1) {                            // NB: shapes[] is rows and columns, so y then x
                    answer[cellCount][0] = positions[pNum][curPos][1];  // Doing this and the next line in one
                    answer[cellCount][0] += dx;                         //   statement makes the syntax checker angry 
                    answer[cellCount][1] = positions[pNum][curPos][2];
                    answer[cellCount][1] += dy;
                    cellCount++;
                }
            }
        }
        return answer;
    }
    
    /****
     * Move the Piece to its next logical position.
     * 
     * @return  Returns the new position. The starting state is -1, meaning "not 
     *          positioned." Subsequent positions increment by 1. The position after the 
     *          last is -1 again.
     ****/
    public int toNextPosition() {
        curPos++;
        if (curPos >= positions[pNum].length) {
            curPos = -1;
        }
        return curPos;
    }

    /****
     * Get this Piece's current position
     * 
     * @return  The current position. -1 ==> not positioned
     ****/
    int getCurPos() {
        return curPos;
    }

    /****
     * Reset the Piece to the starting state -- i.e., "not positioned."
     * 
     ****/
    public void resetPosition(){
        curPos = -1;
    }
}
