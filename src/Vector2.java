import java.util.ArrayList;

public class Vector2 {

    private int m_x, m_y;

    public Vector2(int x, int y) {
        m_x = x;
        m_y = y;
    }

    public int getX(){
        return m_x;
    }

    public int getY(){
        return m_y;
    }

    public static Vector2 plus(Vector2 first, Vector2 second){
        int x = first.getX() + second.getX();
        int y = first.getY() + second.getY();

        return new Vector2(x, y);
    }

    public static int getDistance(Vector2 first, Vector2 second) {
        int delta_x = Math.abs(first.getX() - second.getX());
        int delta_y = Math.abs(first.getY() - second.getY());
        return (int) Math.sqrt((delta_x * delta_x) + (delta_y + delta_y));
    }

    public static Vector2 lerp(Vector2 first, Vector2 second, int percent){
        int x = lerp(first.getX(), second.getX(), percent);
        int y = lerp(first.getY(), second.getY(), percent);
        return new Vector2(x, y);
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

    private static int lerp(int v1, int v2, int percent){
        return (1 - percent) * v1 + percent * v2;
    }

    public boolean checkZero(){
        return m_x == 0 || m_y == 0;
    }

    public void pluso1(){
        m_x += 1;
        m_y += 1;
    }

    public void setY(int y) {
        m_y = y;
    }

    public void setX(int x) {
        m_y = x;
    }
}
