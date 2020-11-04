package referee;

public class Timer {
    private static final int _timeLimitFor6x6 = 995;
    private static final int _timeLimitFor8x8 = 1995;
    private long startTime;
    private int timeLimit;

    public Timer(int dimension) {
        startTime = System.currentTimeMillis();
        timeLimit = dimension == 8 ? _timeLimitFor8x8 : _timeLimitFor6x6;
    }

    public long timePassed() {
        return System.currentTimeMillis()-startTime;
    }
    public boolean timesUp() {
        return timePassed() >= timeLimit;
    }
}
