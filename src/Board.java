import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;

public class Board extends JPanel implements Runnable {

    private final int Largura = 800;
    private final int Altura = 600;
    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private SpaceShip spaceShip;
    private List<Enemy> enemies;
    private Thread animator;
    private final int Delay = 5;
    private boolean ingame;

    private final int[][] pos = {
            {2380, 29}, {2500, 59}, {1380, 89},
            {780, 109}, {580, 139}, {680, 239},
            {790, 259}, {760, 50}, {790, 150},
            {980, 209}, {560, 45}, {510, 70},
            {930, 159}, {590, 80}, {530, 60},
            {940, 59}, {990, 30}, {920, 200},
            {900, 259}, {660, 50}, {540, 90},
            {810, 220}, {860, 20}, {740, 180},
            {820, 128}, {490, 170}, {700, 30}
    };

    public Board(){

        initBoard();
    }

    private void initBoard(){

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        ingame = true;

        setPreferredSize(new Dimension(Largura, Altura));

        spaceShip = new SpaceShip(ICRAFT_X, ICRAFT_Y);

        initEnemies();

    }

    public void initEnemies(){

        /*Esse for pode ser considerado como um ReadOnly, já que ele
        não altera os valores do array, somente itera até seu fim.
         */

        enemies = new ArrayList<>();

        for(int[] p : pos){
            enemies.add(new Enemy(p[0], p[1]));
        }
    }

    public void addNotify(){

        super.addNotify();

        animator = new Thread(this);
        animator.start();
    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        if(ingame){
            drawObjects(g);
        } else {
            drawGameOver(g);
        }

    }

    private void drawObjects(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        if(spaceShip.isVisible()) {
            g2d.drawImage(spaceShip.getImage(), spaceShip.getX(),
                    spaceShip.getY(), this);
        }

        List<LaserBeam> lasers = spaceShip.getLasers();

        for(LaserBeam laserBeam : lasers) {
            if(laserBeam.isVisible()) {
                g2d.drawImage(laserBeam.getImage(), laserBeam.getX(),
                        laserBeam.getY(), this);
            }
        }

        for(Enemy enemy : enemies){
            if(enemy.isVisible()){
                g2d.drawImage(enemy.getImage(), enemy.getX(),
                        enemy.getY(), this);
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("Inimigos restantes: " + enemies.size(), 5, 15);
    }

    private void drawGameOver(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        String msg = "GAME OVER";
        Font small = new Font("Helvetica", Font.BOLD, 50);
        FontMetrics fm = getFontMetrics(small);

        g2d.setColor(Color.RED);
        g2d.setFont(small);
        g2d.drawString(msg, (Largura - fm.stringWidth(msg)) / 2,
                Altura / 2);
    }

    public void inGame(){
        if(!ingame){
            animator.stop();
        }
    }


    private void updateLasers(){

        List<LaserBeam> lasers = spaceShip.getLasers();

        for(int i = 0; i < lasers.size(); i++){

            LaserBeam laserBeam = lasers.get(i);

            if(laserBeam.isVisible()){

                laserBeam.move();
            } else {

                lasers.remove(i);
            }
        }
    }

    private void updateSpaceShip(){

        if(spaceShip.isVisible()) {
            spaceShip.move();
        }
    }

    private void updateEnemies(){
        if(enemies.isEmpty()){

            ingame = false;
            return;
        }

        for(int i = 0; i < enemies.size(); i++){

            Enemy e = enemies.get(i);

            if(e.isVisible()){
                e.move();
            } else {
                enemies.remove(i);
            }
        }
    }

    private void checkCollisions(){

        Rectangle r3 = spaceShip.getBounds();

        for(Enemy enemy : enemies){

            Rectangle r2 = enemy.getBounds();

            if(r3.intersects(r2)){

                spaceShip.setVisible(false);
                enemy.setVisible(false);
                ingame = false;
            }
        }

        List<LaserBeam> lasers = spaceShip.getLasers();

        for(LaserBeam lb : lasers){

            Rectangle r1 = lb.getBounds();

            for(Enemy enemy : enemies){

                Rectangle r2 = enemy.getBounds();

                if(r1.intersects(r2)){

                    lb.setVisible(false);
                    enemy.setVisible(false);
                }
            }
        }
    }

    private class TAdapter extends KeyAdapter{

        public void keyReleased(KeyEvent e){
            spaceShip.keyReleased(e);
        }

        public void keyPressed(KeyEvent e){
            spaceShip.keyPressed(e);
        }
    }

    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while(true){

            updateSpaceShip();
            updateLasers();
            updateEnemies();

            checkCollisions();

            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = Delay - timeDiff;

            if(sleep < 0){
                sleep = 2;
            }

            try{
                Thread.sleep(sleep);
            } catch(InterruptedException e){
                String msg = String.format("Thread interrupted: %s", e.getMessage());

                JOptionPane.showMessageDialog(this, msg, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            beforeTime = System.currentTimeMillis();
        }
    }
}













