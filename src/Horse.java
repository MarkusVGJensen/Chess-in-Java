public class Horse extends Pieces {
    public Horse(String position, String colour) {
        super(true, position, colour);
    }

    @Override
    public String toString() {
        return "H" + getColour();
    }
}
