package Game.Levels;

import Game.Game;
import Game.GameObjects.Entities.Player;
import Game.Worlds.CollisionType;
import Game.Worlds.EtherWorld;
import Game.Worlds.TerraWorld;
import Game.Worlds.World;
import Windows.WorldWindow;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Level implements KeyListener {
    protected Game game;
    protected Player player;
    protected boolean levelPlayed = false;
    protected Point playerPosition = new Point(0, 0);
    protected boolean inTerra = true;
    protected TerraWorld terraWorld;
    protected EtherWorld etherWorld;

    protected String levelPath;

    protected ArrayList<WorldWindow> worldWindows = new ArrayList<>();
    protected ArrayList<WorldWindow> worldWindowsToAdd = new ArrayList<>();
    protected ArrayList<WorldWindow> worldWindowsToRemove = new ArrayList<>();

    public Level(Game game, Player player, String levelPath) {
        this.game = game;
        this.player = player;
        this.levelPath = levelPath;

        terraWorld = new TerraWorld(game, this, levelPath);
        etherWorld = new EtherWorld(game, this, levelPath);
    }

    public Level(ArrayList<String> lines, Game game, Player player, String levelPath) {
        this.game = game;
        this.player = player;
        this.levelPath = levelPath;

        double playerX = 0;
        double playerY = 0;

        for(int i = 0; i < lines.size(); i++){
            String line = lines.get(i);

            if(line.startsWith("PLAYED=")) {
                levelPlayed = Boolean.parseBoolean(line.replace("PLAYED=", ""));
            } else if(line.startsWith("IN TERRA=")) {
                inTerra = Boolean.parseBoolean(line.replace("IN TERRA=", ""));
            } else if(line.startsWith("PX=")) {
                playerX = Double.parseDouble(line.replace("PX=", ""));
            } else if(line.startsWith("PY=")) {
                playerY = Double.parseDouble(line.replace("PY=", ""));
            } else if(line.startsWith("TERRA")) {
                ArrayList<String> data = new ArrayList<>();

                i++;
                for(; i < lines.size() && !lines.get(i).equals("END TERRA"); i++) {
                    data.add(lines.get(i));
                }

                terraWorld = new TerraWorld(data, game, this, player, levelPath);
            } else if(line.startsWith("ETHER")) {
                ArrayList<String> data = new ArrayList<>();

                i++;
                for(; i < lines.size() && !lines.get(i).equals("END ETHER"); i++) {
                    data.add(lines.get(i));
                }

                etherWorld = new EtherWorld(data, game, this, player, levelPath);
            } else if(line.startsWith("WINDOW")) {

                String worldName = line.replace("WINDOW ", "");

                WorldWindow worldWindow = new WorldWindow(worldName.equals("TERRA") ? terraWorld : etherWorld);
                worldWindow.setFocusable(true);
                worldWindow.requestFocus();
                worldWindow.setKeyListener(game);

                double x = 0;
                double y = 0;

                i++;
                for(; i < lines.size() && !lines.get(i).equals("END WINDOW " + worldName); i++) {
                    String subLine = lines.get(i);


                    if(subLine.startsWith("TARGET=")) {
                        boolean targetPlayer = subLine.equals("TARGET=PLAYER");
                        if(targetPlayer) {
                            worldWindow.setTarget(player);
                        }
                    } else if(subLine.startsWith("X=")) {
                        x = Double.parseDouble(subLine.replace("X=", ""));
                    } else if(subLine.startsWith("Y=")) {
                        y = Double.parseDouble(subLine.replace("Y=", ""));
                    }
                }

                Point location = new Point(0, 0);
                location.setLocation(x, y);

                worldWindow.setLocation(location);

                worldWindows.add(worldWindow);
            }
        }

        playerPosition.setLocation(playerX, playerY);
    }

    public World getTerra() {
        return terraWorld;
    }

    public World getEther() {
        return etherWorld;
    }

    public void update(long deltaTime) {
        terraWorld.update(deltaTime);
        etherWorld.update(deltaTime);

        for(WorldWindow worldWindow : worldWindows) {
            worldWindow.update(deltaTime);
        }

        worldWindows.addAll(worldWindowsToAdd);
        worldWindows.removeAll(worldWindowsToRemove);

        playerPosition.setLocation(player.getLocation());
        inTerra = player.getWorld() instanceof TerraWorld;
    }

    public void open() {
        terraWorld.open();
        etherWorld.open();

        for(WorldWindow worldWindow : worldWindows) {
            worldWindow.setVisible(true);
        }
    }

    public void close() {
        terraWorld.close();
        etherWorld.close();

        for(WorldWindow worldWindow : worldWindows) {
            worldWindow.setVisible(false);
        }
    }

    public void addWorldWindow(WorldWindow worldWindow) {
        worldWindow.setFocusable(true);
        worldWindow.requestFocus();
        worldWindow.setKeyListener(game);

        worldWindowsToAdd.add(worldWindow);
    }

    public void removeWorldWindow(WorldWindow worldWindow) {
        worldWindowsToRemove.add(worldWindow);
    }

    public void kill() {
        terraWorld.kill();
        etherWorld.kill();

        for(WorldWindow worldWindow : worldWindows) {
            worldWindow.setVisible(false);
        }

        worldWindows.clear();
    }

    public void keyTyped(KeyEvent e) {
        terraWorld.keyTyped(e);
        etherWorld.keyTyped(e);
    }

    public void keyPressed(KeyEvent e) {
        terraWorld.keyPressed(e);
        etherWorld.keyPressed(e);

        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            switchWindow();
        }
    }

    public void keyReleased(KeyEvent e) {
        terraWorld.keyReleased(e);
        etherWorld.keyReleased(e);
    }

    public void switchWindow() {
        WorldWindow currentWindow = null;

        for(WorldWindow worldWindow : worldWindows) {
            if(worldWindow.getTarget() == player) {
                currentWindow = worldWindow;
                break;
            }
        }

        if(currentWindow == null) {
            System.out.println("Something went horribly wrong there is no player in a window??");
            return;
        }

        for(WorldWindow worldWindow : worldWindows) {
            if(worldWindow == currentWindow || worldWindow.getTarget() != null) {
                continue;
            }

            if(!worldWindow.getBounds().intersects(currentWindow.getBounds())) {
                continue;
            }

            World currentWorld = currentWindow.getWorld();
            World switchWorld = worldWindow.getWorld();

            if(switchWorld != currentWorld) {
                Point feet = new Point(player.getLocation());
                feet.translate(0, (int) player.getSize().getHeight() / 2);

                if(switchWorld.checkCollision(feet) != CollisionType.NONE) {
                    continue;
                }

                switchWorld.addGameObject(player);
                currentWorld.removeGameObject(player);

                player.setWorld(switchWorld);
            }


            worldWindow.getView().getFrame().toFront();

            worldWindow.setTarget(player);
            currentWindow.setTarget(null);

            Point currentPosition = currentWindow.getView().getFrame().getLocation();
            currentWindow.getView().getFrame().setLocation(worldWindow.getView().getFrame().getLocation());
            worldWindow.getView().getFrame().setLocation(currentPosition);
        }
    }

    public String encode() {
        worldWindows.addAll(worldWindowsToAdd);
        worldWindows.removeAll(worldWindowsToRemove);

        String result = "";

        result += "PLAYED=" + levelPlayed + "\n";
        result += "IN TERRA=" + inTerra + "\n";
        result += "PX=" + playerPosition.getX() + "\n";
        result += "PY=" + playerPosition.getY() + "\n";

        result += "TERRA\n";
        result += terraWorld.encode();
        result += "END TERRA\n";

        result += "ETHER\n";
        result += etherWorld.encode();
        result += "END ETHER\n";

        for(WorldWindow worldWindow : worldWindows) {
            if(worldWindow.getTarget() != null && !(worldWindow.getTarget() instanceof Player)) {
                // we don't really care enough about the other windows lol :skull:
                continue;
            }

            String location =  ((worldWindow.getWorld() instanceof TerraWorld) ? "TERRA" : "ETHER");

            result += "WINDOW " + location + "\n";
            result += "TARGET=" + ((worldWindow.getTarget() instanceof Player) ? "PLAYER" : "NONE") + "\n";
            result += "X=" + worldWindow.getLocation().getX() + "\n";
            result += "Y=" + worldWindow.getLocation().getY() + "\n";
            result += "END WINDOW " + location + "\n";
        }

        return result;
    }
}
