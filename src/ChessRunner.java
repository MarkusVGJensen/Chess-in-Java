import java.util.Arrays;
import java.util.Scanner;

public class ChessRunner {
    // White pieces
    private static final Pawn pw1 = new Pawn("A2", "w");
    private static final Pawn pw2 = new Pawn("B2", "w");
    private static final Pawn pw3 = new Pawn("C2", "w");
    private static final Pawn pw4 = new Pawn("D2", "w");
    private static final Pawn pw5 = new Pawn("E2", "w");
    private static final Pawn pw6 = new Pawn("F2", "w");
    private static final Pawn pw7 = new Pawn("G2", "w");
    private static final Pawn pw8 = new Pawn("H2", "w");
    private static final Rook rw1 = new Rook("A1", "w");
    private static final Rook rw2 = new Rook("H1", "w");
    private static final Horse hw1 = new Horse("B1", "w");
    private static final Horse hw2 = new Horse("G1", "w");
    private static final Bishop bw1 = new Bishop("C1", "w");
    private static final Bishop bw2 = new Bishop("F1", "w");
    private static final Queen qw = new Queen("D1", "w");
    private static final King kw = new King("E1", "w");
    // Black pieces
    private static final Pawn pb1 = new Pawn("A7", "b");
    private static final Pawn pb2 = new Pawn("B7", "b");
    private static final Pawn pb3 = new Pawn("C7", "b");
    private static final Pawn pb4 = new Pawn("D7", "b");
    private static final Pawn pb5 = new Pawn("E7", "b");
    private static final Pawn pb6 = new Pawn("F7", "b");
    private static final Pawn pb7 = new Pawn("G7", "b");
    private static final Pawn pb8 = new Pawn("H7", "b");
    private static final Rook rb1 = new Rook("A8", "b");
    private static final Rook rb2 = new Rook("H8", "b");
    private static final Horse hb1 = new Horse("B8", "b");
    private static final Horse hb2 = new Horse("G8", "b");
    private static final Bishop bb1 = new Bishop("C8", "b");
    private static final Bishop bb2 = new Bishop("F8", "b");
    private static final Queen qb = new Queen("D8", "b");
    private static final King kb = new King("E8", "b");

    private static Pieces[][] board = {{rb1, hb1, bb1, kb, qb, bb2, hb2, rb2},
        {pb1, pb2, pb3, pb4, pb5, pb6, pb7, pb8},
        {null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null},
        {pw1, pw2, pw3, pw4, pw5, pw6, pw7, pw8},
        {rw1, hw1, bw1, qw, kw, bw2, hw2, rw2}};

    private static final Scanner myObj = new Scanner(System.in); // Create a Scanner object

    public static void main(String[] args) {
        System.out.println("Game has started:\n");
        PrintGameBoard();
        while (true) {
            System.out.println("\nWhite needs to make a move!\nWrite \"Position NewPosition\"");
            String wmove = myObj.nextLine();
            while (!checkMove(wmove, "b")) {
                System.out.println("You cannot make this move. Try again");
                wmove = myObj.nextLine();
            }
            makeMove(wmove);
            PrintGameBoard();
            if (!kb.getAlive()) {
                System.out.println("White won!");
                return;
            }
            System.out.println("\nBlack needs to make a move!\nWrite \"Position NewPosition\"");
            String bmove = myObj.nextLine();
            while (!checkMove(bmove, "w")) {
                System.out.println("You cannot make this move. Try again");
                bmove = myObj.nextLine();
            }
            makeMove(bmove);
            PrintGameBoard();
            if (!kw.getAlive()) {
                System.out.println("Black won!");
                return;
            }
        }
    }

    private static Boolean checkMove(String move, String notMovingColour) {
        // Check for correct length
        if (move.length() != 5 || move.charAt(2) != ' ') {
            return false;
        }
        // Check that the move is within the bounds of the game
        String oldPosition = move.substring(0, 2);
        String newPosition = move.substring(3);
        String chars = "abcdefgh";
        String ints = "12345678";
        if (!(chars.contains(oldPosition.substring(0, 1)) &&
                ints.contains(oldPosition.substring(1, 2)) &&
                chars.contains(newPosition.substring(0, 1)) &&
                ints.contains((newPosition.substring(1, 2))))) {
            return false;
        }
        // Check that you are moving your own piece
        int xo = oldPosition.charAt(0);
        int yo = oldPosition.charAt(1);
        Pieces movingPiece = board[7 - (yo - 49)][xo - 97];
        if (movingPiece == null ||
                movingPiece.getColour().equals(notMovingColour)) {
            return false;
        }
        // Check that you are not moving to a position with your own piece.
        int xn = newPosition.charAt(0);
        int yn = newPosition.charAt(1);
        if (board[7 - (yn - 49)][xn - 97] != null) {
            String movingColour = "w";
            if (notMovingColour.equals("w")) {
                movingColour = "b";
            }
            if (board[7 - (yn - 49)][xn - 97].getColour().equals(movingColour)) {
                return false;
            }
        }
        // Check that the piece can actually make the move
        if (movingPiece instanceof Pawn) {
            if (notMovingColour.equals("w")) {
                if (board[7 - (yn - 49)][xn - 97] != null && yn == yo - 1 && (xn == xo + 1 || xn == xo - 1)) {
                    return CollisionCheck(oldPosition, newPosition);
                } else if (((Pawn) movingPiece).getFirstMove() && (yn == yo - 2 || yn == yo - 1) && xn == xo) {
                    ((Pawn) movingPiece).changeFirstMove();
                    return CollisionCheck(oldPosition, newPosition);
                } else return yn == yo - 1 && xn == xo;
            } else if (board[7 - (yn - 49)][xn - 97] != null && yn == yo + 1 && (xn == xo + 1 || xn == xo - 1)) {
                return CollisionCheck(oldPosition, newPosition);
            } else if (((Pawn) movingPiece).getFirstMove() && (yn == yo + 2 || yn == yo + 1) && xn == xo) {
                ((Pawn) movingPiece).changeFirstMove();
                return CollisionCheck(oldPosition, newPosition);
            } else return yn == yo + 1 && xn == xo;
        } else if (movingPiece instanceof King) {
            if ((Math.abs(yo - yn) == 1 || Math.abs(yo - yn) == 0) && (Math.abs(xo - xn) == 1 || Math.abs(xo - xn) == 0)) {
                return true;
            } else if (((King) movingPiece).getFirstMove() && newPosition.charAt(0) == 'b') {
                if (CollisionCheck(oldPosition, newPosition)) {
                    if (notMovingColour.equals("w") && board[0][0] == rb1) {
                        board[0][0] = null;
                        board[7 - (yn - 49)][xn - 97 + 1] = rb1;
                    } else if (board[7][0] == rw1) {
                        board[7][0] = null;
                        board[7 - (yn - 49)][xn - 97 + 1] = rw1;
                    }
                }
            } else if (((King) movingPiece).getFirstMove() && newPosition.charAt(0) == 'g') {
                if (CollisionCheck(oldPosition, newPosition)) {
                    if (notMovingColour.equals("w") && board[0][7] == rb2) {
                        board[0][7] = null;
                        board[7 - (yn - 49)][xn - 97 - 1] = rb2;
                    } else if (board[7][7] == rw2) {
                        board[7][7] = null;
                        board[7 - (yn - 49)][xn - 97 - 1] = rw2;
                    }
                }
            } else { return false; }
        } else if (movingPiece instanceof Queen) {
            return CollisionCheck(oldPosition, newPosition);
        } else if (movingPiece instanceof Bishop) {
            return CollisionCheckDiagonal(oldPosition, newPosition);
        } else if (movingPiece instanceof Horse) {
            if (yo > yn) {
                if (xo > xn) {
                    return (yn == yo - 2 && xn == xo - 1) || (yn == yo - 1 && xn == xo - 2);
                } else {
                    return (yn == yo - 2 && xn == xo + 1) || (yn == yo - 1 && xn == xo + 2);
                }
            } else {
                if (xo > xn) {
                    return (yn == yo + 2 && xn == xo - 1) || (yn == yo + 1 && xn == xo - 2);
                } else {
                    return (yn == yo + 2 && xn == xo + 1) || (yn == yo + 1 && xn == xo + 2);
                }
            }
        } else {                    // It is an instance of a Rook
            return CollisionCheckNonDiagonal(oldPosition, newPosition);
        }
        return true;
    }

    private static Boolean CollisionCheckNonDiagonal(String oldPosition, String newPosition) {
        int xo = oldPosition.charAt(0);
        int yo = oldPosition.charAt(1);
        int xn = newPosition.charAt(0);
        int yn = newPosition.charAt(1);
        if (yo == yn) { // Bevæger sig fra side til side
            if (xn > xo) { // Bevæger sig til højre
                for (int x = xo + 1; x < xn; x++) {
                    if (board[7 - (yn - 49)][x - 97] != null) {
                        return false;
                    }
                }
            } else { // Bevæger sig til venstre
                for (int x = xo - 1; x > xn; x--) {
                    if (board[7 - (yn - 49)][x - 97] != null) {
                        return false;
                    }
                }
            }
        } else if (xo == xn) { // Bevæger sig frem og tilbage
            if (yn > yo) { // Bevæger sig nedad
                for (int y = yo + 1; y < yn; y++) {
                    if (board[7 - (y - 49)][xn - 97] != null) {
                        return false;
                    }
                }
            } else { // Bevæger sig opad
                for (int y = yo - 1; y > yn; y--) {
                    if (board[7 - (y - 49)][xn - 97] != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static Boolean CollisionCheckDiagonal(String oldPosition, String newPosition) {
        int xo = oldPosition.charAt(0);
        int yo = oldPosition.charAt(1);
        int xn = newPosition.charAt(0);
        int yn = newPosition.charAt(1);
        if (Math.abs(yn - yo) != Math.abs(xn - xo)) {
            return false;
        }
        if (yo > yn && xo < xn) { // diagonalt ned til højre
            for (int inc = 1; inc < Math.abs(yn - yo); inc++) {
                if (board[7 - (yo - 49) + inc][xo - 97 + inc] != null) {
                    return false;
                }
            }
        } else if (yo < yn && xo < xn) { // diagonalt op til højre
            for (int inc = 1; inc < Math.abs(yn - yo); inc++) {
                if (board[7 - (yo - 49) - inc][xo - 97 + inc] != null) {
                    return false;
                }
            }
        } else if (yo > yn) { // diagonalt ned til venstre
            for (int inc = 1; inc < Math.abs(yn - yo); inc++) {
                if (board[7 - (yo - 49) + inc][xo - 97 - inc] != null) {
                    return false;
                }
            }
        } else { // diagonalt op til venstre
            for (int inc = 1; inc < Math.abs(yn - yo); inc++) {
                if (board[7 - (yo - 49) - inc][xo - 97 - inc] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    // Check if there is a collision on the path with another piece
    private static Boolean CollisionCheck(String oldPosition, String newPosition) {
        return CollisionCheckNonDiagonal(oldPosition, newPosition) || CollisionCheckDiagonal(oldPosition, newPosition);
    }

    // Check if the Pawn needs a promotion
    private static void PawnFinish(Pieces movingPiece, String newPosition) {
        int xn = newPosition.charAt(0);
        int yn = newPosition.charAt(1);
        String colour = "w";
        if (7 - (yn - 49) == 7) { colour = "b"; }
        if (7 - (yn - 49) == 0 || 7 - (yn - 49) == 7) {
            while (movingPiece.getAlive()) {
                System.out.println("\nYou have to promote your Pawn to a Rook, Horse, Bishop or a Queen. Write the one you want.");
                String promotion = myObj.nextLine();
                switch (promotion) {
                    case "Queen" -> {
                        movingPiece.setAlive(false);
                        board[7 - (yn - 49)][xn - 97] = new Queen(newPosition, colour);
                    }
                    case "Rook" -> {
                        movingPiece.setAlive(false);
                        board[7 - (yn - 49)][xn - 97] = new Rook(newPosition, colour);
                    }
                    case "Bishop" -> {
                        movingPiece.setAlive(false);
                        board[7 - (yn - 49)][xn - 97] = new Bishop(newPosition, colour);
                    }
                    case "Horse" -> {
                        movingPiece.setAlive(false);
                        board[7 - (yn - 49)][xn - 97] = new Horse(newPosition, colour);
                    }
                    default -> System.out.println("\nTry again.");
                }
            }
        }
    }

    private static void makeMove(String move) {
        String oldPosition = move.substring(0, 2);
        String newPosition = move.substring(3);
        int xo = oldPosition.charAt(0);
        int yo = oldPosition.charAt(1);
        int xn = newPosition.charAt(0);
        int yn = newPosition.charAt(1);
        Pieces moving = board[7 - (yo - 49)][xo - 97]; // Save the moving piece
        if (board[7 - (yn - 49)][xn - 97] != null) {
            board[7 - (yn - 49)][xn - 97].setAlive(false); // Killing the piece moved to
        }
        board[7 - (yo - 49)][xo - 97] = null; // Moving from the old position
        board[7 - (yn - 49)][xn - 97] = moving;
        if (moving instanceof Pawn) {
            PawnFinish(moving, newPosition);
        }
    }

    private static void PrintGameBoard() {
        String[] chars = {"a", "b", "c", "d", "e", "f", "g", "h", "¤"};
        for (int row = 0; row <= board.length; row++) {
            for (int col = 0; col <= board.length; col++) {
                if (row != board.length) {
                    if (col != board.length) {
                        if (board[row][col] != null) {
                            System.out.print(board[row][col] + " ");
                        } else {
                            System.out.print("[] ");
                        }
                    } else {
                        System.out.print(8 - row);
                    }
                } else {
                    System.out.print(chars[col] + "  ");
                }
            }
            System.out.println();
        }
    }
}
