public class Piece {
    // The shapes and orientations of those shapes the pieces can have possibly have
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
            {1, 1, 0},  // *
            {1, 0, 0},  // **
            {0, 0, 0}
        },

        // J-shape
        {               // 6 (Face-down J)
            {1, 1, 0},  // **
            {0, 0, 1},  //   *
            {0, 0, 0}
        },
        {               // 7 (Normal J)
            {0, 0, 1},  //  *
            {0, 0, 1},  //  *
            {0, 1, 0}   // *
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

    // The possible positions of the pieces
    // position component selector -----------.
    // position selector ------------------.  |
    // piece selector ------------------.  |  |
    //                                  |  |  |
    private final static byte positions[ ][ ][ ] = {
        {               // Piece A (position components are shape, x, y)
            {1, 2, 0},  // Normal I at (2, 0)
            {1, 6, 0},  //  " @ (6, 0)
            {1, 4, 2},  //  " @ (4, 2)
            {0, 3, 1},  // Face Up I at (3, 1)
            {0, 1, 3},  //   " @ (1, 3)
            {0, 5, 3},  //   " @ (5, 3)
            {0, 3, 5}   //   " @ (3, 5)
        },
        {               // Piece B
            {2, 0, 3},  // Upside down L (0, 3)
            {2, 0, 1},  // 
            {2, 4, 1},  // 
            {2, 1, 3},  // 
            {2, 5, 3},  // 
            {2, 2, 4},  // 
            {2, 6, 4},  // 
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
        {               // Piece C
            {2, 1, 0},  // Upside down backwards L
            {2, 0, 1},
            {2, 4, 1},
            {2, 3, 2},
            {2, 2, 3},
            {2, 6, 3},
            {2, 1, 4},
            {2, 5, 4},
        },
        {               // Piece D
            {5, 3, 0},  // Normal L
            {5, 2, 1},
            {5, 6, 1},
            {5, 1, 2},
            {5, 5, 2},
            {5, 0, 3},
            {5, 4, 3},
            {5, 3, 4},
        },
        {               // Piece E
            {8, 1, 1},  // Face up J
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
        {               // Piece F
            {1, 3, 0},  // Normal I
            {1, 7, 0},
            {1, 1, 2},
            {1, 5, 2},
            {0, 0, 1},  // Face Up I
            {0, 4, 1},
            {0, 2, 3},
            {0, 0, 5},
            {0, 4, 5}
        },
        {               // Piece G
            {2, 0, 0},  // Upside down backwards L
            {2, 4, 0},
            {2, 2, 2},
            {2, 6, 2},
            {2, 0, 5},
            {2, 4, 5},
            {4, 2, 0},  // Backwards L
            {4, 6, 0},
            {4, 0, 2},
            {4, 4, 2},
            {4, 2, 4},
            {4, 6, 4}
        },
        {               // Piece H
            {5, 0, 0},  // Normal L
            {5, 4, 0},
            {5, 2, 2},
            {5, 6, 2},
            {5, 0, 5},
            {5, 4, 5},
            {3, 2, 0},  // Upside down L
            {3, 6, 0},
            {3, 0, 2},
            {3, 4, 2},
            {3, 2, 4},
            {3, 6, 4}
        },
        {               // Piece I
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
        {               // Piece J
            {9, 2, 0},  // Upside down J
            {9, 6, 0},
            {9, 0, 2},
            {9, 4, 2},
            {10, 3, 0}, // Face up backwrds J
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
        {               // Piece K
            {1, 2, 0},  // Normal I
            {1, 6, 0},
            {1, 0, 2},
            {1, 4, 2},
            {0, 3, 1},  // Face up I
            {0, 1, 3},
            {0, 5, 3},
            {0, 3, 5},
        },
        {               // Piece L
            {5, 1, 1},  // Normal L
            {5, 5, 1},
            {5, 3, 3},
            {3, 3, 1},  // Upside down L
            {3, 1, 3},
            {3, 5, 3}
        },
        {               // Piece M
            {4, 1, 1},  // Backwards L
            {4, 5, 1},
            {4, 3, 3},
            {2, 3, 1},  // Upside down backwards L
            {2, 1, 3},
            {2, 5, 3}
        },
        {               // Piece N
            {0, 1, 0},  // Face up I
            {0, 2, 1},
            {0, 3, 2},
            {0, 0, 3},
            {0, 4, 3},
            {0, 1, 4},
            {0, 5, 4},
            {0, 2, 5}
        },
        {               // Piece O
            {13, 1, 1}, // Upside down backwards J
            {13, 5, 1},
            {13, 3, 3},
            {8, 3, 1},  // Face up J
            {8, 1, 3},
            {8, 5, 3},
            {11, 1, 0}, // Backward J
            {11, 5, 0},
            {11, 3, 2},
            {6, 2, 1},  // Face-down J
            {6, 0, 3},
            {6, 4, 3}
        },
        {               // Piece P
            {0, 2, 0},  // Face up I
            {0, 0, 2},
            {0, 4, 0},
            {0, 2, 4},
            {1, 3, 1},  // Normal I
            {1, 7, 1},
            {1, 1, 3},
            {1, 5, 3},
        }
    };
    public char name;
    public int number;
    Piece(char nm, int no) {
        name = nm;
        number = no;
    }
}
