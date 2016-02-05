package ninja.bryansills.spotidemo;

public class TimeUtils {
    private static final long ONE_HOUR = 3600;

    public static String formatDuration(long duration) {
        long durationSeconds = duration/1000;
        if (durationSeconds > ONE_HOUR) {
            return String.format("%d:%02d:%02d",
                                 durationSeconds / 3600,
                                 (durationSeconds % 3600) / 60,
                                 (durationSeconds % 60));
        } else {
            return String.format("%d:%02d",
                    (durationSeconds % 3600) / 60,
                    (durationSeconds % 60));

        }
    }
}
