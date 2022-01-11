package creatures;

import huglife.*;

import java.awt.Color;
import java.util.List;
import java.util.Map;

public class Clorus extends Creature {
    private double maxEnergy;
    private int r;
    private int g;
    private int b;

    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
        maxEnergy = e;
    }

    public Clorus() {
        this(0.1);
    }

    @Override
    public Color color() {
        return color(r, g, b);
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public void move() {
        energy -= 0.03;
    }

    @Override
    public void stay() {
        energy -= 0.01;
    }

    @Override
    public Clorus replicate() {
        energy *= 0.5;
        return new Clorus(energy);
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> le = getNeighborsOfType(neighbors, "empty");
        List<Direction> lp = getNeighborsOfType(neighbors, "plip");
        if (le.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        } else if (!lp.isEmpty()) {
            return new Action(Action.ActionType.ATTACK, lp.get(HugLifeUtils.randomInt(lp.size() - 1)));
        } else if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, le.get(HugLifeUtils.randomInt(le.size() - 1)));
        } else {
            return new Action(Action.ActionType.MOVE, le.get(HugLifeUtils.randomInt(le.size() - 1)));
        }
    }
}
