package entity;

import javax.swing.*;

public class Animation extends Thread {
    private volatile boolean running = false;
    AbstractEntity entity;
    EntityState state;
    final JLabel[] images;

    Animation(AbstractEntity e, EntityState es) {
        super();
        entity = e;
        state = es;
        images = new JLabel[4];
        for(int i=0; i<4; i++) images[i] = new JLabel(es.getAnim()[i]);
    }

    @Override
    public void run() {
        int j = 0;
        running = true;
        while(running) {
            entity.add(images[j],0);
            entity.repaint();
            entity.revalidate();
            try {
                sleep(200);
            } catch (InterruptedException e) {
                currentThread().interrupt();
                break;
            }
            j = (j + 1) % 4;
            entity.remove(0);
        }
        entity.repaint();
        entity.revalidate();
    }

    public void end() {
        running = false;
    }
}
