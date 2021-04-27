package graphics;

import entity.Player;
import graphics.elements.Position;
import graphics.elements.Room;
import graphics.elements.cells.Cell;
import graphics.map.WorldMap;
import graphics.window.GameEndPanel;
import graphics.window.GameWindow;
import items.AbstractItem;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.im.InputContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class Tools {

    public static String getKeyboard() {
        InputContext context = InputContext.getInstance();
        Locale country = context.getLocale();
        return country.toString();
    }

    public static List<Position> findPath(List<List<Position>> P, Position start, Position end, Room r, Cell[][] lab, boolean[][] booleans){
        List<Position> res = new ArrayList<>();
        while (true){
            int x = end.getX() - r.getTopLeft().getX();
            int y = end.getY() - r.getTopLeft().getY();
            Cell c = lab != null ? lab[end.getX()][end.getY()] : WorldMap.getInstanceWorld().getCell(end);
            Position current = P.get(x).get(y);
            res.add(current);
            c.setObstacle(null);
            if (booleans != null) booleans[x][y] = true;
            if (current.equals(start)) break;
            end = current;
        }
        return res;
    }

    public static List<List<Position>> BFS(Position start, Room r, Cell[][] lab, boolean[][] booleans){
        List <Position> Q = new ArrayList<>();
        List<List<Position>> P = new ArrayList<>();
        for (int i = 0; i < r.getWidth() + 1; i++) {
            P.add(new ArrayList<>());
            for (int j = 0; j < r.getHeight() + 1; j++)
                P.get(i).add(null);
        }
        Q.add(start); P.get(start.getX() - r.getTopLeft().getX()).set(start.getY() - r.getTopLeft().getY(), start);
        while (Q.size() != 0){
            Position z = Q.remove(0);
            List<Position> ng = z.getNeighbor(true);
            Collections.shuffle(ng);
            for (Position p : ng){
                int x = p.getX() - r.getTopLeft().getX();
                int y = p.getY() - r.getTopLeft().getY();
                Cell c = lab == null ? WorldMap.getInstanceWorld().getCell(p) : lab[p.getX()][p.getY()];
                if (p.getX() >= r.getTopLeft().getX() && p.getX() <= r.getBottomRight().getX() &&
                        p.getY() >= r.getTopLeft().getY() && p.getY() <= r.getBottomRight().getY() &&
                        !c.getBaseContent().isWall() &&
                        P.get(x).get(y) == null) {
                    if (lab != null || c.isAccessible() || c.getEntity() != null) {
                        if (!p.equals(AbstractItem.end.getPosition())) {
                            P.get(x).set(y, z);
                            Q.add(p);
                            if (booleans != null && booleans[x][y]) {
                                findPath(P, start, p, r, lab, booleans);
                                Q.clear();
                            }
                        }
                    }
                }
            }
        }
        return P;
    }

    public static char universalCharOf(char key) {
        return switch (key) {
            case 'z' -> 'w';
            case 'q' -> 'a';
            case 'a' -> 'q';
            case 'w' -> 'z';
            default -> key;
        };
    }

    public static void showCommands() {
        char top = 'w', left = 'a', attack = 'q';
        if (getKeyboard().equals("fr_FR")){ top = 'z'; left = 'q'; attack = 'a';}
        System.out.printf(System.lineSeparator() + "To move : %c (top), %c (left), s (bottom), d (right)%sTo attack (not effective): %c%sTo leave : p%s", top, left, System.lineSeparator(), attack, System.lineSeparator(), System.lineSeparator());
    }

    public static Clip play(URL pathname, boolean loop) {
        try {
            //File audioFile1 = new File(pathname.toURI());
            AudioInputStream audioStream1 = AudioSystem.getAudioInputStream(pathname);

            AudioFormat format = audioStream1.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            Clip clip = (Clip) AudioSystem.getLine(info);

            clip.open(audioStream1);
            clip.start();
            if(loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
            return clip;
        }
        catch(Exception e) {
            System.err.println("Error: failed to load music.");
            return null;
        }
    }

    public static Cursor cursor() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(Objects.requireNonNull(Tools.class.getClassLoader().getResource("data/images/system/cursor.png")));
        return toolkit.createCustomCursor(image, new Point(0,0), "cursor");
    }

    public static void restartGame() {
        GameWindow.clear();
        Player.getInstancePlayer().restorePlayer();
        WorldMap.getInstanceWorld().generateWorld();
        GameWindow.display();
    }

    public static void gameEnd(Victory_Death vd) {
        switch (vd){
            case DEATH_BY_HUNGER, DEATH_BY_HP -> GameWindow.addToLogs(Language.logHeroDeath(vd.equals(Victory_Death.DEATH_BY_HUNGER)), Color.RED);
            case WIN -> GameWindow.addToLogs(Language.logHeroVictory(), Color.RED);
        }
        Ranking.saveRanking(vd);
        GameWindow.refreshInventory();
        GameWindow.window.repaint();
        GameWindow.window.revalidate();
        new GameEndPanel(vd);
    }

    public enum Victory_Death {
        DEATH_BY_HUNGER(0), DEATH_BY_HP(1), WIN(2), ABANDON(3);
        public final int id;
        Victory_Death(int i){
            id = i;
        }
        public static Victory_Death findById(int id){
            for (Victory_Death vd : Victory_Death.values())
                if (vd.id == id) return vd;
            throw new RuntimeException("Int not known in Victory_Death");
        }

        @Override
        public String toString() {
            return Language.translate(this);
        }
    }

    /**
     * A sub class for the settings of the game.
     */
    public static class Settings {
        private static int[] resolution;
        private static Language language;
        private static boolean mute = false;
        private static GameWindow.Difficulty difficulty; //0 to 4
        private static boolean[] difficultiesUnlocked; //1 to 5
        private static final String path =
                (System.getProperty("os.name").startsWith("Windows") ?
                        System.getenv("APPDATA") + "\\ThatTimeTheHeroSavedTheVillage\\.settings" :
                        System.getProperty("user.home") + "/.ThatTimeTheHeroSavedTheVillage/settings_project_TERD");

        public static void loadSettings() {
            try {
                File f = new File(path);
                if(!f.exists()) {
                    defaultSettings();
                    return;
                }
                Scanner scanner = new Scanner(f);
                String line;
                while(scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    if(line.length() > 0) if(line.charAt(0) != '$') {
                        String[] info = line.split(" "); //info[0] = var_name ; info[1] = var_value
                        if (info.length > 1) {
                            switch (info[0]) {
                                case "sResolution" -> resolution = new int[]{Integer.parseInt(info[1].split("_")[0]), Integer.parseInt(info[1].split("_")[2])};
                                case "sLanguage" -> language = switch (info[1]) {
                                    case "FR" -> Language.FR;
                                    case "IT" -> Language.IT;
                                    case "AR" -> Language.AR;
                                    default -> Language.EN;
                                };
                                case "sMusic" -> mute = info[1].equals("false");
                                case "sDifficulty" -> difficulty = GameWindow.Difficulty.findById(Integer.parseInt(info[1]));
                                case "sUnlocked" -> {
                                    int n = Integer.parseInt(info[1]);
                                    difficultiesUnlocked = new boolean[5];
                                    for(int i = 0; i < 5; i++) difficultiesUnlocked[i] = i < n;
                                }
                            }
                        }
                    }
                }
                scanner.close();
                if(resolution == null || language == null || difficulty == null || difficultiesUnlocked == null)
                    defaultSettings(); //If at least 1 line is missing, then restore the default settings.
            } catch(Exception e) {
                defaultSettings(); //If at least 1 line is missing, then restore the default settings.
                saveSettings(language, !mute, difficulty, difficultiesUnlocked, resolution);
            }
        }

        public static void saveSettings(Language lang, boolean sound, GameWindow.Difficulty diff, boolean[] unlocked, int[] resolution) {
            try {
                if (!makeDir()) return;
                File f = new File(path);
                if(!f.exists()) if(!f.createNewFile()) return;
                FileWriter writer = new FileWriter(f);
                writer.write("sResolution " + resolution[0] + "_x_" + resolution[1] + "\n");
                writer.write("sLanguage " + lang + "\n");
                writer.write("sMusic " + sound + "\n");
                writer.write("sDifficulty " + diff.id + "\n");
                int res = 0;
                for(boolean b: unlocked) if(b) res++;
                writer.write("sUnlocked " + res + "\n");
                writer.close();
                loadSettings();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        private static void defaultSettings() {
            resolution = new int[]{800,600};
            language = Language.EN; //Default language
            mute = false; //Default value
            difficulty = GameWindow.Difficulty.EASY; //Default value
            difficultiesUnlocked = new boolean[]{true, false, false, false, false};
        }

        public static int[] getResolution() {
            return resolution;
        }

        public static Language getLanguage() {
            return language;
        }

        public static boolean isMuted() {
            return mute;
        }

        public static GameWindow.Difficulty getDifficulty() {
            return difficulty;
        }

        public static boolean[] getDifficultiesUnlocked() {
            return difficultiesUnlocked;
        }
    }

    public static class Ranking {
        private static final String path =
                (System.getProperty("os.name").startsWith("Windows") ?
                        System.getenv("APPDATA") + "\\ThatTimeTheHeroSavedTheVillage\\.ranking" :
                        System.getProperty("user.home") + "/.ThatTimeTheHeroSavedTheVillage/ranking");

        public static String[][] getRankings() {
            File f = new File(path);
            try {
                List<String[]> res = new ArrayList<>();
                Scanner scanner = new Scanner(f);
                int limit = 7;
                while (scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    if (!line.equals("")) res.add(line.split(" ", limit));
                }
                String[][] resS = new String[res.size()][limit];
                for (int i = 0; i < res.size(); i++){
                    int j = 0;
                    resS[i][j] = res.get(i)[j++];
                    resS[i][j] = Victory_Death.findById(Integer.decode(res.get(i)[j++])).toString();
                    resS[i][j] = GameWindow.Difficulty.findById(Integer.decode(res.get(i)[j++])).toString();
                    resS[i][j] = switch (res.get(i)[j++]) {
                        case "1" -> Language.archerCL();
                        case "2" -> Language.mageCL();
                        default -> Language.warriorCL();
                    };
                    resS[i][j] = res.get(i)[j++];
                    resS[i][j] = res.get(i)[j++];
                    resS[i][j] = res.get(i)[j];
                }
                return resS;
            } catch (FileNotFoundException e){
                return null;
            } catch (Exception e){
                System.out.println(e);
                System.err.println("Can't parse the ranking file.");
                clearRanking();
                return null;
            }
        }

        /*
        * Save file :
        * Date | VictoryDefeat | Difficulty | Spec | Level | Stage | Name
        * the name is at the end since it can be composed by any char
         */

        public static void saveRanking(Victory_Death vd) {
            if(!makeDir()) return;
            File f = new File(path);
            try {
                if (!f.exists()) if (!f.createNewFile()) return;
                //true = appends the string to the file
                FileWriter fw = new FileWriter(path, true);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String numSpec = switch(Player.getInstancePlayer().entityType) {
                    case HERO_ARCHER -> "1";
                    case HERO_MAGE -> "2";
                    default -> "0";
                };
                fw.write(formatter.format(new Date()) + " " +
                        vd.id + " " +
                        GameWindow.difficulty().id + " " +
                        numSpec + " " +
                        Player.getInstancePlayer().getLvl() + " " +
                        WorldMap.stageNum + " " +
                        (GameWindow.name == null || GameWindow.name.equals("") ? "Unknown" : GameWindow.name) + "\n");
                fw.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        public static void clearRanking() {
            File f = new File(path);
            try {
                if (!f.exists()) if (!f.createNewFile()) return;
                FileWriter fw = new FileWriter(path);
                fw.write("");
                fw.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private static boolean makeDir(){
        File directory = new File(
                System.getProperty("os.name").startsWith("Windows") ?
                        System.getenv("APPDATA") + "\\ThatTimeTheHeroSavedTheVillage" :
                        System.getProperty("user.home") + "/.ThatTimeTheHeroSavedTheVillage");
        if(!directory.exists())
            return directory.mkdir();
        return true;
    }

    /**
     * Sub classes for the text.
     */
    public static class TerminalText {
        public static final String DEFAULT = "\033[0m";
        public static String black(String txt) { return "\033[30m" + txt + DEFAULT; }
        public static String red(String txt) { return "\033[31m" + txt + DEFAULT; }
        public static String green(String txt) { return "\033[32m" + txt + DEFAULT; }
        public static String yellow(String txt) { return "\033[33m" + txt + DEFAULT; }
        public static String blue(String txt) { return "\033[34m" + txt + DEFAULT; }
        public static String magenta(String txt) { return "\033[35m" + txt + DEFAULT; }
        public static String cyan(String txt) { return "\033[36m" + txt + DEFAULT; }
        public static String white(String txt) { return "\033[37m" + txt + DEFAULT; }

        public static String blackBG(String txt) { return "\033[40m" + txt + DEFAULT; }
        public static String redBG(String txt) { return "\033[41m" + txt + DEFAULT; }
        public static String greenBG(String txt) { return "\033[42m" + txt + DEFAULT; }
        public static String yellowBG(String txt) { return "\033[43m" + txt + DEFAULT; }
        public static String blueBG(String txt) { return "\033[44m" + txt + DEFAULT; }
        public static String magentaBG(String txt) { return "\033[45m" + txt + DEFAULT; }
        public static String cyanBG(String txt) { return "\033[46m" + txt + DEFAULT; }
        public static String whiteBG(String txt) { return "\033[47m" + txt + DEFAULT; }

        public static String encircled(String txt) { return "\033[52m" + txt + DEFAULT; }
    }

    public static class WindowText {
        public static final Color green = new Color(80, 140, 50);
        public static final Color purple = new Color(100,60,120);
        public static final Color blue = new Color(50,80,140);
        public static final Color cyan = new Color(80,140,180);
        public static final Color red = new Color(140,30,30);
        public static final Color golden = new Color(210,170,60);
        public static final Color dark_golden = new Color(100, 60, 10);
        public static final Color orange = new Color(160,60,30);
    }
}
