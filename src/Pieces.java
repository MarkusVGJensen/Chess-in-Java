public class Pieces {
    private Boolean alive; // Is the piece dead or alive
    private String position; // Position on board for example: "A1"
    private final String colour; // Black or white

    public Pieces(Boolean alive, String position, String colour) {
        this.alive = alive;
        this.position = position;
        this.colour = colour;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Boolean getAlive() {
        return alive;
    }

    public String getPosition() {
        return position;
    }

    public String getColour() {
        return colour;
    }
}
