public class App {
    public static void main(String[] args) throws Exception {
        Board board = new Board();
        Piece[] pieces = Piece.makePieces();

        int curPiece = 15;
        int curPos = pieces[curPiece].toNextPosition();
        while (curPos >=0) {
            if (board.tryToPlace(pieces[curPiece])) {
                System.out.print(board);
                board.removePiece(pieces[curPiece]);
                curPos = pieces[curPiece].toNextPosition();
            } else {
                System.out.println("Strange: couldn't place the piece on an empty board.");
            }
        }
    }
}
