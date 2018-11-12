 

import static org.lwjgl.opengl.GL11.*;

public class Draw {

    public static void rect(float x, float y, float width, float height) {
        rect(x, y, width, height, new Color(255, 255, 255));
    }

    public static void rect(float x, float y, float width, float height, Color c) {
        glPushMatrix();
        {
            glTranslatef(x, y, 0);

            glBegin(GL_QUADS);
            {
                c.getColor();
                glVertex2f(0, 0);
                glVertex2f(0, height);
                glVertex2f(width, height);
                glVertex2f(width, 0);
            }
            glEnd();
        }
        glPopMatrix();
    }
}