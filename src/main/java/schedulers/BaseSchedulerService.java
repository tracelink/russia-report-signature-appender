package schedulers;

import services.AuthService;

import java.util.Timer;
import java.util.TimerTask;

public abstract class BaseSchedulerService {
    private final AuthService authService;
    private final Timer timer;

    public BaseSchedulerService(AuthService authService) {
        this.authService = authService;
        this.timer = new Timer(false);
    }

    protected void scheduleAt(TimerTask task, long initialDelay, long period) {
        this.timer.scheduleAtFixedRate(task, initialDelay, period);
    }

    protected AuthService getAuthService() {
        return authService;
    }

    public abstract void start();

    public abstract void stop();
}
