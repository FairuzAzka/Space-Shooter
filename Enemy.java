import java.awt.*;

public class Enemy {
    private int x, y;
    private static final int SPEED = 5;
    
    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void update() {
        y += SPEED;
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 40, 40);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 40);
    }
    
    public int getY() { return y; }
}