package bot.support;

import game.Tile;

import java.util.ArrayList;
import java.util.List;

public class BotGroup {
    private ArrayList<Tile> group = new ArrayList<>();
    private int mineLevel;

    public BotGroup(int mineLevel) {
        this.mineLevel = mineLevel;
    }

    public int size() {
        return this.group.size();
    }

    private Tile get(int index) {
        return this.group.get(index);
    }

    public int getMines() {
        return this.mineLevel;
    }

    public boolean contains(BotGroup other) {
        return this.group.contains(other.group);
    }

    public boolean overlaps(BotGroup other) {
        return this.getOverlap(other).size() != 0;
    }

    public void subtraction(BotGroup other) {
        BotGroup overlap = this.getOverlap(other);
        this.group.removeAll(overlap.group);
    }

    public BotGroup getOverlap(BotGroup other) {
        BotGroup overlap = new BotGroup(0);
        for (int i = 0; i < this.size(); i++)
            for (int j = 0; j < other.size(); j++) {
                if (this.get(i).equals(other.get(j)))
                    overlap.group.add(this.group.get(i));
            }

        int newMineLevel = this.mineLevel - (other.size() - overlap.size());
        if (this.getMines() < other.getMines()) newMineLevel =
                other.getMines() - (this.size() - overlap.size());
        overlap.mineLevel = newMineLevel;
        return overlap;
    }

    public void add(Tile other) {
        this.group.add(other);
    }

    public ArrayList<Tile> getTiles() {
        return this.group;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (this.getClass() != o.getClass()) return false;
        BotGroup obj = (BotGroup) o;
        if (this.size() != obj.size()) return false;
        for (int i = 0; i < this.size(); i++)
            if (!this.group.get(i).equals(obj.get(i))) return false;
        return this.getMines() == obj.getMines();
    }
}
