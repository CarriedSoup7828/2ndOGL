import java.util.Scanner;
import java.util.Vector;
class Vector3 {
    public double x;
    public double y;
    public double z;

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f, %.2f)", x, y, z);
    }
    public String toStringMatrix() {
        return String.format(toString(), toString(), toString());
    }


    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static double len(Vector3 v) {
        double l;
        l = Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z);
        return l;
    }

    public static Vector3 norm(Vector3 v) {
        if( len(v)==0){
            return new Vector3 (0,0,0);
        }
        return new Vector3(v.x / len(v), v.y / len(v), v.z / len(v));
    }
    public Vector3 xR(double r) { // resultVector = cam.pos.xR(5)
        return new Vector3(x * r, y * r, z * r);
    }


    public Vector3 vPlusV(Vector3 v1) {
        return new Vector3(x + v1.x, y + v1.y, z + v1.z);
    }
    public Vector3 vminusV(Vector3 v1) {
        return new Vector3(x - v1.x, y - v1.y, z - v1.z);
    }


    double VdotV(Vector3 v1) {
        double s = x * v1.x + y * v1.y + z * v1.z;
        return s;
    }

    public Vector3 VxV(Vector3 v1) {
        return new Vector3(y * v1.z - v1.y * z, z * v1.x - v1.z * x, x * v1.y - y * v1.x);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Vector3 v = new Vector3(in.nextDouble(), in.nextDouble(), in.nextDouble());
        Vector3 v1 = new Vector3(in.nextDouble(), in.nextDouble(), in.nextDouble());

        System.out.println(v.VxV(v1));
    }
}
class Matrix3x3 {
    public Vector3 a;
    public Vector3 b;
    public Vector3 c;

    public Matrix3x3(Vector3 a, Vector3 b, Vector3 c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public String toString() {
        return "("+a+",\n "+b+",\n "+c+")";
    }
    public static Matrix3x3 mI(){
        return new Matrix3x3(new Vector3(1,0,0),
                new Vector3(0,1,0),
                new Vector3(0,0,1));
    }
    public Matrix3x3 xR( double r){
        return new Matrix3x3(a.xR(r),
                b.xR(r),
                c.xR(r));
    }
    public Matrix3x3 MplusM( Matrix3x3 m) {
        return new Matrix3x3(new Vector3(a.x +m.a.x, a.y +m.a.y, a.z +m.a.z),
                new Vector3(b.x +m.b.x, b.y +m.b.y, b.z +m.b.z),
                new Vector3(c.x +m.c.x, c.y +m.c.y, c.z +m.c.z));
    }

    public Matrix3x3 MminusM( Matrix3x3 m) {
        return new Matrix3x3(new Vector3(a.x - m.a.x, a.y -m.a.y, a.z -m.a.z),
                new Vector3(b.x -m.b.x, b.y -m.b.y, b.z -m.b.z),
                new Vector3(c.x -m.c.x, c.y -m.c.y, c.z -m.c.z));
    }

    public Matrix3x3 MxM(Matrix3x3 m) {
        return new Matrix3x3(new Vector3(a.x * m.a.x + a.y * m.b.x + a.z * m.c.x, a.x * m.a.y + a.y * m.b.y + a.z * m.c.y, a.x * m.a.z + a.y * m.b.z + a.z * m.c.z),
                new Vector3(b.x * m.a.x + b.y * m.b.x+ b.z * m.c.x, b.x * m.a.y + b.y * m.b.y + b.z * m.c.y, b.x * m.a.z + b.y * m.b.z + b.z * m.c.z),
                new Vector3(c.x * m.a.x + c.y * m.b.x + c.z * m.c.x, c.x * m.a.y + c.y * m.b.y + c.z * m.c.y, c.x * m.a.z + c.y * m.b.z + c.z * m.c.z));
    }

    public static Matrix3x3 MRot(Vector3 v, double r) {
        Matrix3x3 i = mI();
        Matrix3x3 s = new Matrix3x3(new Vector3(0, v.z, -v.y),
                new Vector3(-v.z, 0, v.x),
                new Vector3(v.y, -v.x, 0));
        Matrix3x3 t = i.MplusM(s.xR(Math.sin(r)));
        Matrix3x3 h = s.MxM(s).xR(1 - Math.cos(r));
        return t.MplusM(h);
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Vector3 a = new Vector3(in.nextDouble(), in.nextDouble(), in.nextDouble());
        double r = in.nextDouble();
        System.out.println(MRot(a, r));
    }}
