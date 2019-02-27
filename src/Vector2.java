import java.util.ArrayList;

public class Vector2 {

    private double m_x, m_y;

    public Vector2(double x, double y) {
        m_x = x;
        m_y = y;
    }

    public Vector2() {

    }

    public double getX(){
        return m_x;
    }

    public double getY(){
        return m_y;
    }

    public static Vector2 add(Vector2 first, Vector2 second){
        double x = first.getX() + second.getX();
        double y = first.getY() + second.getY();

        return new Vector2(x, y);
    }

    public static double getDistance(Vector2 first, Vector2 second) {
        double delta_x = Math.abs(first.getX() - second.getX());
        double delta_y = Math.abs(first.getY() - second.getY());
        return Math.sqrt((delta_x * delta_x) + (delta_y + delta_y));
    }

    public static Vector2 lerp(Vector2 first, Vector2 second, int percent){
        double x = lerp(first.getX(), second.getX(), percent);
        double y = lerp(first.getY(), second.getY(), percent);
        return new Vector2(x, y);
    }

    public void add(Vector2 other) {
        m_x += other.getX(); m_y += other.getY();
    }

    public void mult(Vector2 other) {
        m_x *= other.getX(); m_y *= other.getY();
    }

    public void mult(int other) {
        m_x *= other; m_y *= other;
    }

    public void mult(double other) {
        m_x *= other; m_y *= other;
    }

    public void limit(double limit) {
        if (m_x > limit){
            m_x = limit;
        }
        if (m_y > limit){
            m_y = limit;
        }
    }


    public static Vector2 average(ArrayList<Vector2> vectors){
        int x = 0;
        int y = 0;

        for (Vector2 vector : vectors) {
            x += vector.getX();
            y += vector.getY();
        }

        return new Vector2(x / vectors.size(), y / vectors.size());
    }

    private static double lerp(double v1, double v2, double percent){
        return (1 - percent) * v1 + percent * v2;
    }

    public boolean checkZero(){
        return m_x == 0 || m_y == 0;
    }

    public void pluso1(){
        m_x += 1;
        m_y += 1;
    }

    public void setY(double y) {
        m_y = y;
    }

    public void setX(double x) {
        m_x = x;
    }

    public double mag() {
        return Math.sqrt(Math.pow(m_x, 2) + Math.pow(m_y, 2));
    }

    public void normalize() {
        double mag = mag();
        if (mag != 0) {
            m_x /= mag;
            m_y /= mag;
        }
    }

    public static Vector2 sub(Vector2 target, Vector2 location) {
        return new Vector2(target.getX() - location.getX(), target.getY() - location.getY());
    }

    public void sub(Vector2 other) {
        m_x = m_x - other.getX();
        m_y = m_y - other.getY();
    }

    static double dist(Vector2 v, Vector2 v2) {
        return Math.sqrt(Math.pow(v.getX() - v2.getX(), 2) + Math.pow(v.getY() - v2.getY(), 2));
    }

    public double dot(Vector2 v) {
        return m_x * v.getX() + m_y * v.getY();
    }

    static double angleBetween(Vector2 v, Vector2 v2) {
        return Math.acos(v.dot(v2) / (v.mag() * v2.mag()));
    }


    public void div(double val) {
        m_x /= val;
        m_y /= val;
    }

    public double heading() {
        return Math.atan2(m_y, m_x);
    }

}
