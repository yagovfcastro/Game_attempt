import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class SpaceShip extends Sprite{

    private int dx;
    private int dy;
    private List<LaserBeam> lasers;

    public SpaceShip(int x, int y){

        super(x, y);

        initSpaceShip();
    }

    private void initSpaceShip(){

        lasers = new ArrayList<>();

        loadImage("src/Recursos/spaceship.png");
        getImageDimensions();
    }

    public void move(){

        x += dx;
        y += dy;

        if (x < 1) {
            x = 1;
        }

        if (y < 1) {
            y = 1;
        }
    }

    public List<LaserBeam> getLasers(){
        return lasers;
    }


    public void keyPressed(KeyEvent e){

        int key = e.getKeyCode();

        if(key == KeyEvent.VK_SPACE){
            fire();
        }

        if(key == KeyEvent.VK_LEFT){
            dx = -1;
        }

        if(key == KeyEvent.VK_RIGHT){
            dx = 1;
        }

        if(key == KeyEvent.VK_UP){
            dy = -1;
        }

        if(key == KeyEvent.VK_DOWN){
            dy = 1;
        }
    }

    public void fire(){

        lasers.add(new LaserBeam(x + largura, y + altura / 2));
    }

    public void keyReleased(KeyEvent e){

        int key = e.getKeyCode();

        if(key == KeyEvent.VK_LEFT){
            dx = 0;
        }

        if(key == KeyEvent.VK_RIGHT){
            dx = 0;
        }

        if(key == KeyEvent.VK_UP){
            dy = 0;
        }

        if(key == KeyEvent.VK_DOWN){
            dy = 0;
        }
    }
}










