        import   java.util.Scanner;

public class Vector3 {
    public double x,y,z;

     @Override
    public String toString(){
        return String.format("(%.2f, %.2f, %.2f", x,y,z);
    }
    public Vector3(double x,double y,double z) {
        this.x= x;
        this.y= y;
        this.z= z;
    }
    public static double length(Vector3 v){
        double l= Math.sqrt(v.x*v.x+v.y*v.y+v.z*v.z);
        return l;
    }

    public static   Vector3 norm(Vector3 v) {
         if (length(v)!=0) {

             return new Vector3(v.x / length(v), v.y / length(v), v.z / length(v));
         }
         else{
             return new Vector3(0,0,0);
         }
    }
    public static Vector3 Vx(Vector3 v, double R) {
        return new Vector3(v.x*R, v.y*R, v.z*R);

    }
    public  Vector3 vPlusV(Vector3 v, Vector3 V) {
        return new Vector3(v.x+V.x, v.y+V.y, v.z+V.z);

    }
    public  Vector3 vminusV(Vector3 v, Vector3 V) {
        return new Vector3(v.x-V.x, v.y-V.y, v.z-V.z);

    }
    public static double vdotV(Vector3 v, Vector3 V){
         double scal= v.x*V.x+v.y* V.y+ v.z* V.z;
         return scal;
    }
    public  Vector3 vxV(Vector3 v, Vector3 V) {
        return new Vector3(v.y* V.z- V.y*v.z, v.z* V.x-V.z*v.x, v.x* V.y-V.x*v.y);

    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Vector3 v = new Vector3(in.nextDouble(),in.nextDouble(),in.nextDouble() );
        System.out.printf("%.2f", Vector3.norm(v));
    }
}
