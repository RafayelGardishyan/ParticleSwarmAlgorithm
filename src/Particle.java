import java.util.ArrayList;
import java.util.Random;

public class Particle {
    public Vector2 my_pos;
    private Vector2 my_velocity;
    private int m_width, m_height;


    public Vector2 getVelocityVec() {
        return my_velocity;
    }

    Particle(int width, int height) {
        m_width = width;
        m_height = height;
        Random random = new Random();

        int x = Math.abs(random.nextInt()) % width;
        int y = Math.abs(random.nextInt()) % height;

        my_pos = new Vector2(x, y);

        boolean done = false;

        while (!done) {
            x = (random.nextInt() % 10);
            y = (random.nextInt() % 10);

            if (x != 0 && y != 0) {
                done = true;
            }
        }

        my_velocity = new Vector2(x, y);
    }

    private Particle(Vector2 pos, Vector2 vel) {
        my_velocity = vel;
        my_pos = pos;
    }


    public int getDirection() {
        try {
            return (int) Math.atan(my_velocity.getY() / my_velocity.getX());
        } catch (ArithmeticException e){
            return (int) Math.atan(my_velocity.getY() / (my_velocity.getX() + 1));
        }
    }

    public int getVelocity() {
        return (int) Math.sqrt((my_velocity.getX() * my_velocity.getX()) + (my_velocity.getY() * my_velocity.getY()));
    }

    public void algorithm(ArrayList<Particle> o_particles) throws CloneNotSupportedException {
        ArrayList<Particle> particles = new ArrayList<>();

        for (Particle p : o_particles) {
            particles.add((Particle) Particle.clone(p));
        }

        ArrayList<Particle> closest_neighbours = new ArrayList<>();

        while (true) {
            if (closest_neighbours.size() == 4) {
                break;
            }

            Particle closest = null;

            int index = 0;

            while (true) {
                if (closest == null) {
                    closest = particles.remove(0);
                }

                if (Vector2.getDistance(my_pos, particles.get(index).my_pos) < Vector2.getDistance(my_pos, closest.my_pos)) {
                    particles.add(closest);
                    closest = particles.remove(index);
                }

                if (index == particles.size() - 1) {
                    break;
                }

                index++;
            }

            closest_neighbours.add(closest);
        }

        ArrayList<Vector2> vectors = new ArrayList<>();

        for (Particle closest_neighbour : closest_neighbours) {
            vectors.add(closest_neighbour.getVelocityVec());
        }

        Vector2 average = Vector2.average(vectors);

//        my_velocity = Vector2.lerp(my_velocity, average, 5);
        my_velocity = average;
//        my_velocity = Vector2.lerp(my_velocity, temp, 10);

    }

    public void update(ArrayList<Particle> particles) throws CloneNotSupportedException {
        algorithm(particles);
        my_pos = Vector2.plus(my_pos, my_velocity);
        if (my_pos.getY() > m_height + 1){
            my_pos.setY(0);
        }

        if (my_pos.getX() > m_width + 1){
            my_pos.setX(0);
        }

        if (my_pos.getY() < -1){
            my_pos.setY(m_height);
        }

        if (my_pos.getX() < -1){
            my_pos.setX(m_width);
        }
    }

    public static Particle clone(Particle p) {
        return new Particle(p.my_pos, p.getVelocityVec());
    }
}
