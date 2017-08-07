import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * A graphical user interface (GUI) for the GameOfLife
 */
public class GameOfLifeView extends JPanel 
    implements ActionListener {
    
    /**
     * The size of an individual cell in the GUI
     */
    public static final int CELL_SIZE = 32;
    /**
     * The size of the board in the GUI
     */
    public static final int BOARD_SIZE = 
        GameOfLife.BOARD_SIZE * CELL_SIZE;
    /**
     * The overall size of the graphical output.
     * Includes a blank border one cell wide.
     */
    public static final int TOTAL_SIZE = BOARD_SIZE + 2*CELL_SIZE;
    /**
     * The margin around the ALIVE cells
     */
    public static final int MARGIN = 5;
    
    // The instance variables
    // The part you're working on for the assignment
    private GameOfLife board;
    // The window to show the GUI
    private JFrame win;
    // The button for the Next Generation
    private JButton next;
    // The button to clear the board
    private JButton clear;
    // A handler for mouse clicks
    MouseListener mouser = new MouseAdapter() {
        /**
         * The override that handles mouse clicks
         * @param e The event that triggered the handler
         */
        public void mouseClicked(MouseEvent e) {
            // get the cell from the click location
            int row = e.getY() / CELL_SIZE;
            int col = e.getX() / CELL_SIZE;
            // print out the location
            System.out.println("(" + row + ", " + col + ")");
        }
    };
    
    /**
     * Constructs the GUI
     */
    public GameOfLifeView() {
        // set-up the board
        setPreferredSize(new Dimension(TOTAL_SIZE, TOTAL_SIZE));
        setBackground(Color.white);
        // create the game to display
        board = new GameOfLife();
        board.initializeBoard();
        // create the window
        win = new JFrame("Game of Life");
        win.add(this);
        // add mouse clicks to the GUI
        addMouseListener(mouser);
        // create the buttons and toolbar
        next = new JButton("Next Generation");
        next.addActionListener(this);
        clear = new JButton("Clear Board");
        clear.addActionListener(this);
        JPanel tools = new JPanel();
        tools.add(next);
        tools.add(clear);
        win.add(tools, BorderLayout.SOUTH);
        // finish setting up the window
        win.setVisible(true);
        win.pack();
    }
    
    /**
     * This override draws the board
     * @param g The Graphics object for drawing
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw the lines
        for(int i = 1; i < GameOfLife.BOARD_SIZE + 2; i++) {
            g.drawLine(CELL_SIZE, i*CELL_SIZE, BOARD_SIZE + CELL_SIZE, i*CELL_SIZE);
            g.drawLine(i*CELL_SIZE, CELL_SIZE, i*CELL_SIZE, BOARD_SIZE + CELL_SIZE);
        }
        // draw the live cells
        for(int row = 0; row < GameOfLife.BOARD_SIZE + 2; row++) {
            for(int col = 0; col < GameOfLife.BOARD_SIZE + 2; col++) {
                if(board.getState(row, col) == GameOfLife.ALIVE) {
                    g.setColor(Color.blue);
                    g.fillOval(col * CELL_SIZE + MARGIN, row * CELL_SIZE + MARGIN,
                               CELL_SIZE - 2 * MARGIN, CELL_SIZE - 2 * MARGIN);
                }
            }
        }
    }
    
    /**
     * The override that handles button clicks
     * @param e The event that triggered the handler
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == next) {
            // if it's the Next Generation button 
            board.nextGeneration();
            repaint();
        } else if(e.getSource() == clear) {
            // if it's the Clear button
            board.clear();
            repaint();
        }
    }
    
    /**
     * The driver for this GUI version
     * @param args The command-line arguments
     */
    public static void main(String[] args) {
        new GameOfLifeView();
    }
    
}
