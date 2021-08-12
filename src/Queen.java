public class Queen extends Pieces {
    public Queen(String position, String colour) {
        super(true, position, colour);
    }

    @Override
    public String toString() {
        return "Q" + getColour();
    }
}
