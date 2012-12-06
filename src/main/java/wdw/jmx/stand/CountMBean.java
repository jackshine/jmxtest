package wdw.jmx.stand;

public interface CountMBean {

    public long getCount();

    public void setCount(long count);

    public void stop();
}
