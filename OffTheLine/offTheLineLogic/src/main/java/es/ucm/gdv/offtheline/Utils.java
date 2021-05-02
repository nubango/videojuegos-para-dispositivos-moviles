package es.ucm.gdv.offtheline;

public class Utils {
    static class Point{
        Point(double x, double y){
            this.x = x;
            this.y = y;
        }
        double x;
        double y;

        public double magnitud() {
            return Math.sqrt(x*x + y*y);
        };
    }

    // interseccion de segmentos
    static Point segmentsIntersection(double a1x, double a1y, double b1x, double b1y, double a2x, double a2y, double b2x, double b2y ){

        /*
        double tem = (b2x - a2x)*(b1y - a1y) - (b2y - a2y)*(b1x - a1x);

        if(tem == 0)
            return null;

        double mu = ((a1x - a2x)*(b1y - a1y) - (a1y - a2y)*(b1x - a1x))/tem;

        Point puntoCorte = new Point((a2x + (b2x - a2x) * mu),(a2y + (b2y - a2y) * mu));

        return puntoCorte;
*/

        // coordenadas de vectores ba1 y ba2
        double ba1x = b1x - a1x;
        double ba1y = b1y - a1y;
        double ba2x = b2x - a2x;
        double ba2y = b2y - a2y;

        // calculamos el vector ortogonal de ba1
        /*double ba1yOrto = ((2*ba1x*ba1x) + (ba1x*ba1x*ba1y*ba1y)) / ((ba1y*ba1y) + (ba1x*ba1x));
        double ba1xOrto = (ba1x*ba1x) + (ba1y*ba1y) - ba1yOrto;
        ba1yOrto = Math.sqrt(ba1yOrto);
        ba1xOrto = Math.sqrt(ba1xOrto);*/

        double x = -ba1x;
        double ba1xOrto = ba1y;
        double ba1yOrto = x;


        // multiplicamos en la ecuacion por el vector ortogonal de ba1
        double temp = (ba2x*ba1xOrto) + (ba2y*ba1yOrto);
        if(temp == 0)
            return null;
        double alpha = (((a1x*ba1xOrto) + (a1y*ba1yOrto)) - ((a2x*ba1xOrto)+(a2y*ba1yOrto))) / temp;
        //alpha = Math.abs(alpha);

        // calculamos el vector ortogonal de ba1
        /*double ba2yOrto = ((2*ba2x*ba2x) + (ba2x*ba2x*ba2y*ba2y)) / ((ba2y*ba2y) + (ba2x*ba2x));
        double ba2xOrto = (ba2x*ba2x) + (ba2y*ba2y) - ba2yOrto;
        ba2yOrto = Math.sqrt(ba2yOrto);
        ba2xOrto = Math.sqrt(ba2xOrto);*/

        double x2 = -ba2x;
        double ba2xOrto = ba2y;
        double ba2yOrto = x2;


        // multiplicamos en la ecuacion por el vector ortogonal de ba1
        //double x2Corte = ((a2x*ba2xOrto) - (a1x*ba2xOrto)) / (ba1x*ba2xOrto);
        //double y2Corte = ((a2y*ba2yOrto) - (a1y*ba2yOrto)) / (ba1y*ba2yOrto);
        temp = (ba1x*ba2xOrto) + (ba1y*ba2yOrto);
        if(temp == 0)
            return null;
        double beta = (((a2x*ba2xOrto) + (a2y*ba2yOrto)) - ((a1x*ba2xOrto) + (a1y*ba2yOrto))) / temp;
        //beta = Math.abs(beta);

        Point p = null;

        double px = a1x + (alpha*ba1x);
        double py = a1y + (alpha*ba1y);

        double p2x = a2x + (beta*ba2x);
        double p2y = a2y + (beta*ba2y);

        // si ycorte y xcorte estan entre 0 y 1 entonces ha habido corte
        if(alpha >= 0 && alpha <= 1 && beta >= 0 && beta <= 1)
            p = new Point(px, py);


        return p;

    }

    // distancia al cuadrado de un punto a un segmento
    static double sqrDistancePointSegment(double ax, double ay, double bx, double by, double px, double py){

        return 0;
    }
}
