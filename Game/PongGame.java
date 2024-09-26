import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PongGame extends JPanel implements ActionListener, KeyListener {

    private int ballX = 160, ballY = 120, ballXDirection = 2, ballYDirection = 2;
    private int playerPaddleY = 100, aiPaddleY = 100, paddleWidth = 10, paddleHeight = 60;
    private int playerScore = 0, aiScore = 0;
    private int ballSpeed = 3; // Variable to control the speed of the ball
    private int playerScoreCounter = 0; // Counter for player's score to check for speed increase
    private Timer timer;

    public PongGame() {
        setBackground(Color.BLACK);
        setFocusable(true);
        PongGame l = this;
        addKeyListener(l);
        timer = new Timer(5, this); // Timer with delay of 5ms
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);

        // Draw Ball
        g.fillOval(ballX, ballY, 15, 15);

        // Draw Player Paddle
        g.fillRect(30, playerPaddleY, paddleWidth, paddleHeight);

        // Draw AI Paddle
        g.fillRect(540, aiPaddleY, paddleWidth, paddleHeight);

        // Draw Scores
        g.setFont(new Font("Serif", Font.BOLD, 20));
        g.drawString("Player: " + playerScore, 50, 30);
        g.drawString("AI: " + aiScore, 450, 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Move the ball
        ballX += ballXDirection;
        ballY += ballYDirection;

        // Ball bounce on top and bottom walls
        if (ballY <= 0 || ballY >= getHeight() - 15) {
            ballYDirection = -ballYDirection;
        }

        // Ball bounce on player paddle
        if (ballX <= 40 && ballY + 15 >= playerPaddleY && ballY <= playerPaddleY + paddleHeight) {
            ballXDirection = -ballXDirection;
        }

        // Ball bounce on AI paddle
        if (ballX >= 530 && ballY + 15 >= aiPaddleY && ballY <= aiPaddleY + paddleHeight) {
            ballXDirection = -ballXDirection;
        }

        // AI paddle movement (simple logic)
        if (ballY < aiPaddleY + paddleHeight / 2) {
            aiPaddleY -= 2;
        } else if (ballY > aiPaddleY + paddleHeight / 2) {
            aiPaddleY += 2;
        }

        // Ball goes out (score update)
        if (ballX <= 0) {
            aiScore++;
            resetGame();
        } else if (ballX >= getWidth()) {
            playerScore++;
            playerScoreCounter++; // Increase the player's score counter
            increaseBallSpeed(); // Check if speed should increase
            resetGame();
        }

        repaint();
    }

    // Increase the ball speed after every 3 points scored by the player
    private void increaseBallSpeed() {
        if (playerScoreCounter % 3 == 0 && playerScoreCounter > 0 && ballSpeed < 5) {
            ballSpeed++; // Increase the speed for the next round
        }
        ballXDirection = (ballXDirection > 0 ? ballSpeed : -ballSpeed);
        ballYDirection = (ballYDirection > 0 ? ballSpeed : -ballSpeed);
    }

    // Reset the ball to the center after a point is scored
    private void resetGame() {
        ballX = 160;
        ballY = 120;
        // Reverse the X direction to serve
        ballXDirection = -ballXDirection; 
    }

    // Move player paddle with the up and down keys
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            playerPaddleY = Math.max(playerPaddleY - 40, 0);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            playerPaddleY = Math.min(playerPaddleY + 40, getHeight() - paddleHeight);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }
    @Override
    public void keyTyped(KeyEvent e) { }

    // Main method to set up the JFrame
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game");
        PongGame pongGame = new PongGame();
        frame.add(pongGame);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public int getPaddleWidth() {
        return paddleWidth;
    }

    public void setPaddleWidth(int paddleWidth) {
        this.paddleWidth = paddleWidth;
    }

    public int getPaddleHeight() {
        return paddleHeight;
    }

    public void setPaddleHeight(int paddleHeight) {
        this.paddleHeight = paddleHeight;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}
