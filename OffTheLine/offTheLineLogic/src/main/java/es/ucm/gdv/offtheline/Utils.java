package es.ucm.gdv.offtheline;

public class Utils {
    static class Point{
        Point(double x, double y){
            this.x = x;
            this.y = y;
        }
        double x;
        double y;

        public double getX(){return x;}
        public double getY(){return y;}
    }
}
