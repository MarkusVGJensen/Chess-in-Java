public class Rook extends Pieces {
    public Rook(String position, String colour) {
        super(true, position, colour);
    }

    @Override
    public String toString() {
        return "R" + getColour();
    }
}
