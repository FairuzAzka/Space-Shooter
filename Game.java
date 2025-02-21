// Game.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game extends JPanel implements ActionListener, KeyListener {
    private Player player;
    private List<Bullet> bullets;
    private List<Enemy> enemies;
    private Timer timer;
    private int score;
    private boolean gameOver;
    
    public Game() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        
        player = new Player(400, 500);
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        score = 0;
        gameOver = false;
        
        // Create game timer (60 FPS)
        timer = new Timer(1000/60, this);
        timer.start();
        
        // Spawn enemies every 2 seconds
        Timer enemySpawner = new Timer(2000, e -> {
            if (!gameOver) {
                spawnEnemy();
            }
        });
        enemySpawner.start();
    }
    
    private void spawnEnemy() {
        int x = (int)(Math.random() * (getWidth() - 40));
        enemies.add(new Enemy(x, 0));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw player
        player.draw(g);
        
        // Draw bullets
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        
        // Draw enemies
        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }
        
        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 20, 30);
        
        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", getWidth()/2 - 150, getHeight()/2);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            // Update player
            player.update();
            
            // Update bullets
            Iterator<Bullet> bulletIt = bullets.iterator();
            while (bulletIt.hasNext()) {
                Bullet bullet = bulletIt.next();
                bullet.update();
                if (bullet.getY() < 0) {
                    bulletIt.remove();
                }
            }
            
            // Update enemies
            Iterator<Enemy> enemyIt = enemies.iterator();
            while (enemyIt.hasNext()) {
                Enemy enemy = enemyIt.next();
                enemy.update();
                
                // Check collision with bullets
                for (Bullet bullet : bullets) {
                    if (enemy.getBounds().intersects(bullet.getBounds())) {
                        enemyIt.remove();
                        bullets.remove(bullet);
                        score += 10;
                        break;
                    }
                }
                
                // Check collision with player
                if (enemy.getBounds().intersects(player.getBounds())) {
                    gameOver = true;
                }
                
                // Remove enemies that passed the screen
                if (enemy.getY() > getHeight()) {
                    enemyIt.remove();
                }
            }
        }
        repaint();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.setMovingLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                player.setMovingRight(true);
                break;
            case KeyEvent.VK_SPACE:
                if (!gameOver) {
                    bullets.add(new Bullet(player.getX() + 20, player.getY()));
                }
                break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.setMovingLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                player.setMovingRight(false);
                break;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Space Shooter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Game());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

// Player.java
class Player {
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

// Bullet.java
class Bullet {
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

// Enemy.java
class Enemy {
    private int x, y;
    private static final int SPEED = 3;
    
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