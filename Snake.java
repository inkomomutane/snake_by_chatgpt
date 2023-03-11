import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Snake extends JPanel implements KeyListener {
    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final int BLOCK_SIZE = 10;
    private static final int SPEED = 100;
    private LinkedList<Point> snake;
    private Point food;
    private int direction;
    private boolean gameOver;

    public Snake() {
        snake = new LinkedList<Point>();
        snake.add(new Point(0, 0));
        food = generateFood();
        direction = KeyEvent.VK_RIGHT;
        gameOver = false;

        JFrame frame = new JFrame("Snake Game");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.addKeyListener(this);
        frame.setVisible(true);

        startGameLoop();
    }

    private void startGameLoop() {
        while (!gameOver) {
            update();
            repaint();
            try {
                Thread.sleep(SPEED);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        Point head = snake.getFirst();
        int dx = 0, dy = 0;
        switch (direction) {
            case KeyEvent.VK_UP:
                dy = -BLOCK_SIZE;
                break;
            case KeyEvent.VK_DOWN:
                dy = BLOCK_SIZE;
                break;
            case KeyEvent.VK_LEFT:
                dx = -BLOCK_SIZE;
                break;
            case KeyEvent.VK_RIGHT:
                dx = BLOCK_SIZE;
                break;
        }
        Point newHead = new Point(head.x + dx, head.y + dy);
        if (newHead.equals(food)) {
            snake.addFirst(newHead);
            food = generateFood();
        } else {
            snake.removeLast();
            if (snake.contains(newHead) ||
                    newHead.x < 0 || newHead.x >= WIDTH ||
                    newHead.y < 0 || newHead.y >= HEIGHT) {
                gameOver = true;
            } else {
                snake.addFirst(newHead);
            }
        }
    }

    private Point generateFood() {
        Random rand = new Random();
        int x = rand.nextInt(WIDTH / BLOCK_SIZE) * BLOCK_SIZE;
        int y = rand.nextInt(HEIGHT / BLOCK_SIZE) * BLOCK_SIZE;
        return new Point(x, y);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        for (Point p : snake) {
            g.fillRect(p.x, p.y, BLOCK_SIZE, BLOCK_SIZE);
        }
        g.setColor(Color.RED);
        g.fillRect(food.x, food.y, BLOCK_SIZE, BLOCK_SIZE);
        if (gameOver) {
            g.setColor(Color.BLACK);
            g.drawString("Game Over", WIDTH / 2 - 30, HEIGHT / 2);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP && direction != KeyEvent.VK_DOWN ||
                code == KeyEvent.VK_DOWN && direction != KeyEvent.VK_UP ||
                code == KeyEvent.VK_LEFT && direction != KeyEvent.VK_RIGHT ||
                code == KeyEvent.VK_RIGHT && direction != KeyEvent.VK_LEFT) {
            direction = code;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    public static void main(String[] args) {
        new Snake();
    }

}