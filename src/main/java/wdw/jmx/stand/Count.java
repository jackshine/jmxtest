package wdw.jmx.stand;

public class Count implements CountMBean {

    private long   count;
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
}
