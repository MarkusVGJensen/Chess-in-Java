public class Pawn extends Pieces {
    private Boolean firstMove;

    public Pawn(String position, String colour) {
        super(true, position, colour);
        firstMove = true;
    }

    @Override
    public String toString() {
        return "P" + getColour();
    }

    public Boolean getFirstMove() {
        return firstMove;
    }

    public void changeFirstMove() {
        firstMove = false;
    }
}
