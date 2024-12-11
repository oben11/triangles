import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Random;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Triangle extends JPanel {

    private int maxDepth = 0; // Control how deep the recursion goes
    private boolean isIncreasing = true; // Flag to track the direction of animation

    // Main method to set up the frame
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ollie's Triangle");
        Triangle panel = new Triangle();
        frame.add(panel);
        frame.setSize(600, 600); // Set window size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Timer timer = new Timer(500, new ActionListener() { // 500ms delay between steps
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.updateDepth(); 
                panel.repaint(); // Repaint with proper step in recursion
            }
        });
        timer.start();
    }

    // recursion depth for animation
    public void updateDepth() {
        if (isIncreasing) {
            maxDepth++;
            if (maxDepth > 10) { // Once max depth is reached, switch direction
                isIncreasing = false;
            }
        } else {
            maxDepth--;
            if (maxDepth < 0) { // Once minimum depth is reached, switch direction
                isIncreasing = true;
            }
        }
    }

    // draw main triangle
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int[] xPoints = {300, 50, 550}; // x coords
        int[] yPoints = {50, 500, 500}; // y coords

        // Start recursive loop with current maxDepth limit
        makeTri(g, xPoints, yPoints, 6, maxDepth);
    }

    // Recursive method to draw the fractal triangles
    private void makeTri(Graphics g, int[] xPoints, int[] yPoints, int limit, int depth) {
        if (depth == 0 || Math.abs(xPoints[0] - xPoints[1]) <= limit) {
            return; // Stop recursion if the depth reaches 0 or the triangle size is too small
            
        }

        Random rand = new Random();
        float red = rand.nextFloat();
        float green = rand.nextFloat();
        float blue = rand.nextFloat();
        g.setColor(new Color(red, green, blue)); // Set random color
        g.fillPolygon(xPoints, yPoints, 3); // Fill the outer triangle

        // triangle midpoint calculation
        int midX1 = (xPoints[0] + xPoints[1]) / 2;
        int midY1 = (yPoints[0] + yPoints[1]) / 2;
        int midX2 = (xPoints[1] + xPoints[2]) / 2;
        int midY2 = (yPoints[1] + yPoints[2]) / 2;
        int midX3 = (xPoints[2] + xPoints[0]) / 2;
        int midY3 = (yPoints[2] + yPoints[0]) / 2;

        // Inner triangle points
        int[] xInner = {midX1, midX2, midX3};
        int[] yInner = {midY1, midY2, midY3};

        // Draw the smaller triangles recursively with reduced depth
        makeTri(g, new int[]{xPoints[0], midX1, midX3}, new int[]{yPoints[0], midY1, midY3}, limit, depth - 1);
        makeTri(g, new int[]{midX1, xPoints[1], midX2}, new int[]{midY1, yPoints[1], midY2}, limit, depth - 1);
        makeTri(g, new int[]{midX3, midX2, xPoints[2]}, new int[]{midY3, midY2, yPoints[2]}, limit, depth - 1);
    }
}
