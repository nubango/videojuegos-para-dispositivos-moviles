package es.ucm.gdv.offtheline;

public class Utils {
    static class Point{
        Point(double x, double y){
            this.x = x;
            this.y = y;
        }
        double x;
        double y;

        public boolean samePoint(Utils.Point p1){
            return p1.x == x && p1.y == y;
        }
    }

    static class Vector{
        Vector(double x, double y){
            this.x = x;
            this.y = y;
        }
        double x;
        double y;

        public double module() {
            return Math.sqrt(x*x + y*y);
        };

        public void normalize(){
            double mag = module();
            x /= mag;
            y /= mag;
        }

        public double scalarProduct(Vector v1){
            return v1.x*x + v1.y*y;
        }

        public boolean sameVector(Utils.Point p1){
            return p1.x == x && p1.y == y;
        }

        public void changeOppositeDirection(){
            x = -x;
            y = -y;
        }
    }

    /**
     * Igualamos las dos funciones para hallar el punto de corte entre las dos rectas
     * A + s*AB
     * C + t*CD
     *
     * A + s*AB = C + t*CD
     *
     * Si multiplicamos la ecuacion por el ortogonal de AB sacamos el valor de t
     * Y lo mismo con el ortogonal de CD
     *
     * Si s y t estan entre 0 y 1 => hay colision
     *
     * sustituimos en una de las dos ecuaciones el valor de t o s para obtener el punto de corte
     * */
    static Point segmentsIntersection(Point pA, Point pB, Point pC, Point pD){

        // coordenadas de vectores ba1 y ba2
        double ABx = pB.x - pA.x;
        double ABy = pB.y - pA.y;
        double CDx = pD.x - pC.x;
        double CDy = pD.y - pC.y;

        // ALPHA
        double ABOrtoX = ABy;
        double ABOrtoY = -ABx;

        // multiplicamos en la ecuacion por el vector ortogonal de ba1
        double temp = (CDx*ABOrtoX) + (CDy*ABOrtoY);
        if(temp == 0)
            return null;
        double alpha = (((pA.x*ABOrtoX) + (pA.y*ABOrtoY)) - ((pC.x*ABOrtoX) + (pC.y*ABOrtoY))) / temp;

        // BETA
        double CDOrtoX = CDy;
        double CDOrtoY = -CDx;

        temp = (ABx*CDOrtoX) + (ABy*CDOrtoY);
        if(temp == 0)
            return null;
        double beta = (((pC.x*CDOrtoX) + (pC.y*CDOrtoY)) - ((pA.x*CDOrtoX) + (pA.y*CDOrtoY))) / temp;


        Point p = null;

        // si ycorte y xcorte estan entre 0 y 1 entonces ha habido corte
        if(alpha >= 0 && alpha <= 1 && beta >= 0 && beta <= 1) {
//            double px = pA.x + (Math.min(alpha, beta)*ABx);
//            double py = pA.y + (Math.min(alpha, beta)*ABy);
            double px = pA.x + (alpha*ABx);
            double py = pA.y + (alpha*ABy);
            p = new Point(Math.round(px), Math.round(py));
        }

        return p;

    }


    // distancia al cuadrado de un punto a un segmento
    static double sqrDistancePointSegment(Point pA, Point pB, Point pP){
        // Primero tengo que calcular la distancia del punto P a la recta que contiene el vector AB
        // Una vez calculado el segmento que une el punto con la recta, tenemos que comprobar si el
        // segmento AB colisiona con el segmento puntoRecta

        // vector AP
        double vAPx = pP.x - pA.x;
        double vAPy = pP.y - pA.y;

        // Vector AB
        double vABx = pB.x - pA.x;
        double vABy = pB.y - pA.y;

        // ap*ab
        double peAP_AB = vAPx*vABx + vAPy*vABy;
        // ab*ab
        double peAB_AB = vABx*vABx + vABy*vABy;

        // Si producto escalar entre ap y ab < 0 -> angulo mayor que 90 -> return ap*ap
        if(peAP_AB < 0) {
            return (vAPx*vAPx) + (vAPy*vAPy);
        }
        // Si no el producto escalar entre ap y ab > ab*ab -> distancia de p a b -> return bp*bp
        else if(peAP_AB > peAB_AB){
            double vBPx = pP.x - pB.x;
            double vBPy = pP.y - pB.y;
            return (vBPx*vBPx) + (vBPy*vBPy);
        }
        // Si no -> return ap*ap - (ap*ab)*(ap*ab)/ab*ab
        else {
            return ((vAPx*vAPx) + (vAPy*vAPy)) - ((peAP_AB*peAP_AB) / peAB_AB);
        }
    }
}
