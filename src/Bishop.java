public class Bishop extends Pieces {
    public Bishop(String position, String colour) {
        super(true, position, colour);
    }

    @Override
    public String toString() {
        return "B" + getColour();
    }
}
