import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.List;
import java.util.Random;

import static java.lang.Math.PI;

public class Particle {
    private Vector2 acceleration;
    private Vector2 velocity;
    public Vector2 location;
    private double maxSpeed;
    private double maxForce;
    private double minSpeed;
    private double minForce;
    private final Random r = new Random();
    private int m_width, m_height;
    static final int size = 2;
    static final Path2D shape = new Path2D.Double();

    static {
        shape.moveTo(0, -size * 2);
        shape.lineTo(-size * 1.5, size * 2);
        shape.lineTo(size * 1.5, size * 2);

        shape.closePath();
    }

    private boolean included = true;


    Particle(int width, int height) {
        m_width = width;
        m_height = height;
        acceleration = new Vector2();
        velocity = new Vector2((r.nextInt() % 10) - 5, (r.nextInt() % 10) - 5);
        location = new Vector2(r.nextInt() % width, r.nextInt() % height);
        maxSpeed = 3.0;
        minSpeed = -3.0;
        maxForce = 0.05;
        minForce = -0.05;
    }

    public void update(Point mousePos) {
        velocity.add(acceleration);
        velocity.limit(minSpeed, maxSpeed);
        acceleration.mult(0);
// Mouse avoiding: werkt nog niet
//        Vector2 rule4 = avoid(mousePos);
//        rule4.mult(100);
//        applyForce(rule4);
//        velocity.add(acceleration);

        location.add(velocity);
    }

    void applyForce(Vector2 force) {
        acceleration.add(force);
    }

    Vector2 seek(Vector2 target) {
        Vector2 steer = Vector2.sub(target, location);
        steer.normalize();
        steer.mult(maxSpeed);
        steer.sub(velocity);
        steer.limit(minForce, maxForce);
        return steer;
    }

    void flock(Graphics2D g, List<Particle> boids) {
        view(g, boids);

        Vector2 rule1 = separation(boids);
        Vector2 rule2 = alignment(boids);
        Vector2 rule3 = cohesion(boids);


        rule1.mult(5);
        rule2.mult(0.5);
        rule3.mult(1);


        applyForce(rule1);
        applyForce(rule2);
        applyForce(rule3);

        if (location.getX() < 0){
            location.setX(m_width);
        }

        if (location.getY() < 0){
            location.setY(m_height);
        }

        if (location.getX() > m_width){
            location.setX(0);
        }

        if (location.getY() > m_height){
            location.setY(0);
        }
    }

    void view(Graphics2D g, List<Particle> boids) {
        double sightDistance = 100;
        double peripheryAngle = PI * 0.85;

        for (Particle b : boids) {
            b.included = false;

            if (b == this)
                continue;

            double d = Vector2.dist(location, b.location);
            if (d <= 0 || d > sightDistance)
                continue;

            Vector2 lineOfSight = Vector2.sub(b.location, location);

            double angle = Vector2.angleBetween(lineOfSight, velocity);
            if (angle < peripheryAngle)
                b.included = true;
        }
    }

    Vector2 separation(List<Particle> boids) {
        double desiredSeparation = 25;

        Vector2 steer = new Vector2(0, 0);
        int count = 0;
        for (Particle b : boids) {
            if (!b.included)
                continue;

            double d = Vector2.dist(location, b.location);
            if ((d > 0) && (d < desiredSeparation)) {
                Vector2 diff = Vector2.sub(location, b.location);
                diff.normalize();
                diff.div(d);        // weight by distance
                steer.add(diff);
                count++;
            }
        }
        if (count > 0) {
            steer.div(count);
        }

        if (steer(steer)) return steer;
        return new Vector2(0, 0);
    }

    Vector2 alignment(List<Particle> boids) {
        double preferredDist = 50;

        Vector2 steer = new Vector2(0, 0);
        int count = 0;

        for (Particle b : boids) {
            if (!b.included)
                continue;

            double d = Vector2.dist(location, b.location);
            if ((d > 0) && (d < preferredDist)) {
                steer.add(b.velocity);
                count++;
            }
        }

        if (count > 0) {
            steer.div(count);
            steer.normalize();
            steer.mult(maxSpeed);
            steer.sub(velocity);
            steer.limit(minForce, maxForce);
        }
        return steer;
    }

//    Vector2 avoid(Point mousePos) {
//        double desiredSeparation = 150;
//        Vector2 mouseVec = new Vector2(mousePos.x, mousePos.y);
//        Vector2 steer = new Vector2(0, 0);
//        int count = 0;
//
//        double d = Vector2.dist(location, mouseVec);
//        if ((d > 0) && (d < desiredSeparation)) {
//            Vector2 diff = Vector2.sub(location, mouseVec);
//            diff.normalize();
//            diff.div(d);        // weight by distance
//            steer.add(diff);
//            count++;
//        }
//        if (count > 0) {
//            steer.div(count);
//        }
//
//        if (steer(steer)) return steer;
//
//        return new Vector2(0, 0);
//    }

    private boolean steer(Vector2 steer) {
        if (steer.mag() > 0) {
            steer.normalize();
            steer.mult(maxSpeed);
            steer.sub(velocity);
            steer.limit(minForce, maxForce);
            return true;
        }
        return false;
    }

    Vector2 cohesion(List<Particle> boids) {
        double preferredDist = 50;

        Vector2 target = new Vector2(0, 0);
        int count = 0;

        for (Particle b : boids) {
            if (!b.included)
                continue;

            double d = Vector2.dist(location, b.location);
            if ((d > 0) && (d < preferredDist)) {
                target.add(b.location);
                count++;
            }
        }
        if (count > 0) {
            target.div(count);
            return seek(target);
        }
        return target;
    }

    void draw(Graphics2D g) {
        AffineTransform save = g.getTransform();

        g.translate(location.getX(), location.getY());
        g.rotate(velocity.heading() + PI / 2);
        g.setColor(Color.WHITE);
        g.fill(shape);
        g.setColor(Color.RED);
        g.draw(shape);

        g.setTransform(save);
    }

    void run(Graphics2D g, List<Particle> boids, int w, int h, Point mousePos) {
        flock(g, boids);
        update(mousePos);
        draw(g);
    }
}
