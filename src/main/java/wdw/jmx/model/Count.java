package wdw.jmx.model;

public class Count {

    private long   count = 10;
    public boolean stop;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public void stop() {
        stop = true;
    }

    public boolean getStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
