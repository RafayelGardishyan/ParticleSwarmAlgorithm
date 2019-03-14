import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Flock {
    List<Particle> boids;

    Flock() {
        boids = new ArrayList<>();
    }

    void run(Graphics2D g, int w, int h, Point mousePos) {
        for (Particle b : boids) {
            b.run(g, boids, w, h, mousePos);
        }
    }


    void addBoid(Particle b) {
        boids.add(b);
    }

    static Flock spawn(int  w, int h, int numBoids) {
        Flock flock = new Flock();
        for (int i = 0; i < numBoids; i++)
            flock.addBoid(new Particle(w, h));
        return flock;
    }
}