package Interface;

import Central.Main;
import Listeners.Key;
import Listeners.Mouse;
import Listeners.Screen;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Window extends JPanel {
    final JFrame FRAME;
    final public Key KEY;
    final public Mouse MOUSE;
    final public Screen SCREEN;

    public Window() {
        FRAME = new JFrame();
        KEY = new Key();
        MOUSE = new Mouse();
        SCREEN = new Screen();
    }

    public void init() {
        //JPanel
        this.setFocusable(true);
        this.setDoubleBuffered(true);
        this.addMouseListener(MOUSE);
        this.addMouseMotionListener(MOUSE);
        this.addKeyListener(KEY);

        //JFrame
        FRAME.getRootPane().addComponentListener(SCREEN);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.setSize(700, 450);
        FRAME.setLocationRelativeTo(null);
        FRAME.setVisible(true);
        FRAME.add(this);
    }

    @Override
    public void paintComponent(Graphics G) {
        super.paintComponent(G);
        Graphics2D G2D = (Graphics2D) G;
        Main.INTERFACE.draw(G2D);
        G.dispose();
    }

    public final JFrame frame() {
        return FRAME;
    }
}
