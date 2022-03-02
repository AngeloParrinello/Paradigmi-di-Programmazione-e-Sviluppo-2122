package lab01.tdd;

import java.util.List;
import java.util.Optional;

public class CircularListImpl implements CircularList {

    private final List<Integer> circularList;
    private int index = -1;

    public CircularListImpl(List<Integer> circularList) {
        this.circularList = circularList;
    }


    @Override
    public void add(int element) {
        this.circularList.add(element);
    }

    @Override
    public int size() {
       return this.circularList.size();
    }

    @Override
    public boolean isEmpty() {
        return this.circularList.isEmpty();
    }

    @Override
    public Optional<Integer> next() {
        if (!this.circularList.isEmpty()) {
            this.increaseIndex();
            return getOptionalElement(this.index);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Integer> previous() {
        if (!this.circularList.isEmpty()) {
            decreaseIndex();
            return getOptionalElement(this.index);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void reset() {
        this.index = -1;
    }

    @Override
    public Optional<Integer> next(SelectStrategy strategy) {

        for (int i=0; i<=this.circularList.size(); i++) {
            this.next();
            if(strategy.apply(this.circularList.get(this.index))) {
                return getOptionalElement(this.index);
            }
        }

        return Optional.empty();
    }

    private void increaseIndex() {
        if (this.index == -1 || this.index == this.circularList.size() - 1) {
            this.index = 0;
        } else {
            this.index++;
        }
    }

    private void decreaseIndex() {
        if (this.index == -1 || this.index == 0) {
            this.index = this.circularList.size() - 1;
        } else {
            this.index--;
        }
    }

    private Optional<Integer> getOptionalElement(int index) {
        return Optional.of(this.circularList.get(index));
    }
}
