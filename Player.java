import java.awt.*;

public class Player {
    private int x, y;
    private boolean movingLeft, movingRight;
    private static final int SPEED = 5;
    
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void update() {
        if (movingLeft && x > 0) {
            x -= SPEED;
        }
        if (movingRight && x < 760) {
            x += SPEED;
        }
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, 40, 40);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 40);
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
    public void setMovingLeft(boolean movingLeft) { this.movingLeft = movingLeft; }
    public void setMovingRight(boolean movingRight) { this.movingRight = movingRight; }
}