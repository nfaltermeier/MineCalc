package blackop778.mineCalc.core;

import javax.annotation.Nonnull;

import java.util.Comparator;

public class Argument implements Comparator<Argument> {

    public final int index;
    public final int importance;
    public String contents;

    protected Argument(int index, int importance, String contents) {
        this.index = index;
        this.importance = importance;
        this.contents = contents;
    }

    public Argument(Argument old) {
        this.index = old.index;
        this.importance = old.importance;
        this.contents = old.contents;
    }

    @Override
    public int compare(@Nonnull Argument first, @Nonnull Argument second) {
        int importanceOrder = Integer.valueOf(first.importance).compareTo(second.importance);
        if (importanceOrder != 0)
            return -importanceOrder;
        return Integer.valueOf(first.index).compareTo(second.index);
    }
}
