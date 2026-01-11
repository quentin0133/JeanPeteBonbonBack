package fr.quentin.jeanpetebonbon.core.utils;

import org.slf4j.Logger;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The type Log utils.
 */
public class LogUtils {
    private static final StackWalker WALKER =
        StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    private LogUtils() {}

    private static StackWalker.StackFrame caller() {
        return WALKER.walk(stream ->
            stream
                .filter(frame ->
                    !frame.getClassName().equals(LogUtils.class.getName())
                )
                .findFirst()
                .orElseThrow()
        );
    }

    /**
     * Log phase.
     *
     * @param log   the log
     * @param phase the phase
     * @param args  the args
     */
    public static void logPhase(
        Logger log,
        String phase,
        Object... args
    ) {
        StackWalker.StackFrame caller = caller();
        String methodName = caller.getMethodName();

        String message = Arrays.stream(args)
            .map(String::valueOf)
            .collect(Collectors.joining(", "));

        log.info("{} : {} with {}", methodName, phase, message);
    }

    public static void logEnter(Logger log, Object... args) {
        logPhase(log, "ENTER", args);
    }

    public static void logExit(Logger log, Object... args) {
        logPhase(log, "EXIT", args);
    }
}
