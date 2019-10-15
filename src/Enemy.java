public class Enemy extends Sprite{

    private final int X_inicial = 800;

    public Enemy(int x, int y){

        super(x, y);

        initEnemy();
    }

    private void initEnemy(){

        loadImage("src/Recursos/enemy.png");
        getImageDimensions();
    }

    public void move(){

        if(x < 0){
            x = X_inicial;
        }

        x -= 1;
    }
}
