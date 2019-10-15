public class LaserBeam extends Sprite{

    private final int Largura_Board = 799;
    private int Velocidade_Raio = 2;

    public LaserBeam(int x, int y){

        super(x, y); //Super chama o construtor da classe mãe que no caso é Sprite

        initLaseBeam();
    }

    private void initLaseBeam(){

        loadImage("src/Recursos/missile.png");
        getImageDimensions();
    }

    public void move(){

        x += Velocidade_Raio;

        if(x > Largura_Board){
            visible = false;
        }
    }
}
