public class King extends Pieces {
    private Boolean firstMove;

    public King(String position, String colour) {
        super(true, position, colour);
        firstMove = true;
    }

    @Override
    public String toString() {
        return "K" + getColour();
    }

    public Boolean getFirstMove() {
        return firstMove;
    }

    public void changeFirstMove() {
        firstMove = false;
    }
}
