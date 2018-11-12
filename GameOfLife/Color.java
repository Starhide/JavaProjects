 

import static org.lwjgl.opengl.GL11.*;

public class Color {

    public float red, green, blue;

    public Color(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    
    public void getColor(){
        glColor3f(red, green, blue);
    }
}