package com.team5419.frc2023.auto;

import com.team5419.frc2023.loops.CrashTrackingRunnable;

/**
 * This class selects, runs, and stops (if necessary) a specified autonomous
 * mode.
 */
public class AutoModeExecutor {
    private AutoModeBase m_auto_mode;
    private Thread m_thread = null;

    public void setAutoMode(AutoModeBase new_auto_mode) {
        System.out.println("AUTO MODE SET");
        m_auto_mode = new_auto_mode;
    }

    public void start() {
        if (m_thread == null) {
            m_thread = new Thread(new CrashTrackingRunnable() {
                @Override
                public void runCrashTracked() {
                    if (m_auto_mode != null) {
                        m_auto_mode.run();
                    } else {
                        System.out.println("Missing auto mode, nothing running");
                    }
                }
            });

            m_thread.start();
        } else {
            System.out.println("THREAD ALREADY THERE");
            System.out.println("THREAD STATE: " + m_thread.getState().name());
        }

    }

    public void stop() {
        if (m_auto_mode != null) {
            m_auto_mode.stop();
        }

        m_thread = null;
    }

}
