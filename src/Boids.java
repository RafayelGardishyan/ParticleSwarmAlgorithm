import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.Timer;

public class Boids extends JPanel {
    private Flock flock;
    private final int w, h;

    public Boids() {
        w = 1500;
        h = 700;

        setPreferredSize(new Dimension(w, h));

        setBackground(Color.white);

        spawnFlock();

        new Timer(20, (ActionEvent e) -> {
            repaint();
        }).start();
    }

    private void spawnFlock() {
        flock = Flock.spawn(1500, 700, 400);
    }

    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Point mousePosition = MouseInfo.getPointerInfo().getLocation();
        flock.run(g, w, h, mousePosition);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Braitengerg Patroon: Spreeuwenzwerm");
            f.setResizable(true);
            f.add(new Boids(), BorderLayout.CENTER);

//            JSlider slider = new JSlider();
//
//            f.add(slider);


            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}

