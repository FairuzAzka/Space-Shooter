import java.awt.*;

public class Bullet {
    private int x, y;
    private static final int SPEED = 10;
    
    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void update() {
        y -= SPEED;
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, 4, 10);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, 4, 10);
    }
    
    public int getY() { return y; }
}