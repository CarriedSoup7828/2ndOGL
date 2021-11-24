import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.image.WritableRaster;

public class JavaRenderer implements GLEventListener {

    private float angleX = 0.0f;
    private float angleY = 0.0f;
    private float angleZ = 0.0f;
    private float zoom = 1.0f;
    private static final GLU glu = new GLU();
    private BufferedImage image; // будем здесь хранить картинку
    public static double[][] H = new double[129][129];

    public void sphere(GL2 gl) {
        double R0 = 0.7;
        for (int j = 0; j <= 15; j++) {
            gl.glColor3d(0, 1, 0);
            double R = R0 * Math.cos(2 * Math.PI * (j) / 30);
            gl.glColor3d(0.9, 0.7, 0.2);
            gl.glBegin(gl.GL_QUAD_STRIP);
            for (int i = 0; i <= 56; i++) {
                gl.glVertex3d(R * Math.cos(2 * Math.PI * i / 56), R * Math.sin(2 * Math.PI * i / 56), R0 * Math.sin(2 * Math.PI * (j) / 30));
                gl.glVertex3d(R0 * Math.cos(2 * Math.PI * (j + 1) / 30) * Math.cos(2 * Math.PI * i / 56), R0 * Math.cos(2 * Math.PI * (j + 1) / 30) * Math.sin(2 * Math.PI * i / 56), R0 * Math.sin(2 * Math.PI * (j + 1) / 30));

            }
            gl.glEnd();
        }
    }

    public void conus(GL2 gl) {
        gl.glColor3f(1, 0, 0);
        double R = 0.5;
        gl.glBegin(gl.GL_TRIANGLE_FAN);
        gl.glVertex3d(0, 0, -0.5);
        for (int i = 0; i <= 24; i++) {
            gl.glVertex3d(R * Math.cos(2 * Math.PI * i / 24), R * Math.sin(2 * Math.PI * i / 24), -0.5);
        }
        gl.glEnd();
        gl.glColor3f(1, 1, 0);
        gl.glBegin(gl.GL_TRIANGLE_FAN);
        gl.glVertex3d(0, 0, 0.5);
        for (int i = 0; i <= 24; i++) {
            gl.glVertex3d(R * Math.cos(2 * Math.PI * i / 24), R * Math.sin(2 * Math.PI * i / 24), -0.5);

        }
        gl.glEnd();

    }

    public void cilindr(GL2 gl) {
        gl.glColor3f(1, 0, 0);
        double R = 0.5;
        gl.glBegin(gl.GL_TRIANGLE_FAN);
        gl.glVertex3d(0, 0, 0.5);
        for (int i = 0; i <= 24; i++) {
            gl.glVertex3d(R * Math.cos(2 * Math.PI * i / 24), R * Math.sin(2 * Math.PI * i / 24), 0.5);
        }
        gl.glEnd();
        gl.glColor3f(1, 1, 0);
        gl.glBegin(gl.GL_TRIANGLE_FAN);
        gl.glVertex3d(0, 0, -0.5);
        for (int i = 0; i <= 24; i++) {
            gl.glVertex3d(R * Math.cos(2 * Math.PI * i / 24), R * Math.sin(2 * Math.PI * i / 24), -0.5);

        }
        gl.glEnd();
        gl.glBegin(gl.GL_QUAD_STRIP);
        gl.glVertex3d(0.5, 0, 0.5);
        gl.glVertex3d(0.5, 0, -0.5);
        for (int i = 0; i <= 24; i++) {
            gl.glVertex3d(R * Math.cos(2 * Math.PI * i / 24), R * Math.sin(2 * Math.PI * i / 24), -0.5);
            gl.glVertex3d(R * Math.cos(2 * Math.PI * i / 24), R * Math.sin(2 * Math.PI * i / 24), 0.5);

        }
        gl.glEnd();

    }


    public void Land(GL2 gl) {
        for (int x = 0; x <= 129; x++) {
            for (int y = 0; y <= 129; y++) {
                gl.glBegin(gl.GL_TRIANGLE_FAN);

                gl.glVertex3d(x , y, 0);
                gl.glVertex3d(x + 1, y, 0);
                gl.glVertex3d(x + 1, y + 1, 0);
                gl.glVertex3d(x, y + 1, 0);
                gl.glVertex3d(x - 1, y + 1, 0);
                gl.glVertex3d(x - 1, y, 0);
                gl.glVertex3d(x - 1, y - 1, 0);
                gl.glVertex3d(x, y - 1, 0);
                gl.glVertex3d(x + 1, y - 1, 0);
                gl.glVertex3d(x + 1, y, 0);
                gl.glEnd();
            }
        }
    }

    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -5.0f);

        if (Main.wireFrame)
            gl.glPolygonMode(gl.GL_FRONT_AND_BACK, gl.GL_LINE);
        else
            gl.glPolygonMode(gl.GL_FRONT_AND_BACK, gl.GL_FILL);

        angleX += Main.rotationX;
        angleY += Main.rotationY;
        angleZ += Main.rotationZ;
        zoom *= Main.zoomChange;
        Land(gl);
    }

    public void init(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        try {
            image = ImageIO.read(new File("map.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        WritableRaster r = image.getRaster();
        int[] pixel = new int[3];
        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++) {
                r.getPixel(x, y, pixel);
                H[x][y] = pixel[0];
            }
    }

    public void reshape(GLAutoDrawable gLDrawable, int x,
                        int y, int width, int height) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        if (height <= 0) {
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, 1920, 1080);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void dispose(GLAutoDrawable arg0) {

    }

}