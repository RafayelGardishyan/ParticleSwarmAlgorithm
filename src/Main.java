import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class Surface extends JPanel implements ActionListener {

    private final int DELAY = 10;
    private Timer timer;
    AffineTransform identity = new AffineTransform();
    ArrayList<Particle> particles = new ArrayList<>();

    public Surface() {
        for (int i = 0; i < 200; i++){
            particles.add(new Particle(1500, 700));
        }


        initTimer();
    }

    private void initTimer() {

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public Timer getTimer(){

        return timer;
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.blue);
        BufferedImage image;

        try {
            image = ImageIO.read(new File("C:\\Users\\rgard\\IdeaProjects\\ELab-Braitenberg\\src\\triangle.png"));
            for (int i = 0; i < particles.size(); i++) {
                Particle p = particles.get(i);
                p.update(particles);
                AffineTransform transform = new AffineTransform();
                transform.setTransform(identity);
                transform.translate(p.my_pos.getX(), p.my_pos.getY());
//                System.out.println(p.getDirection());
                transform.rotate(p.getDirection());
//                transform.rotate(Math.toRadians(90));

                g2d.drawImage(image, transform, this);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }
}

public class Main extends JFrame{

    public Main() {

        initUI();
    }

    private void initUI() {

        final Surface surface = new Surface();
        add(surface);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Timer timer = surface.getTimer();
                timer.stop();
            }
        });

        setTitle("Simple Java 2D example");
        setSize(1500, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                Main main = new Main();
                main.setVisible(true);
            }
        });
    }
}
