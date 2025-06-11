package com.event_planner;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EventManager {

    public static final List<EventPlanner.Event> events = Collections.synchronizedList(new ArrayList<>());
    private static final java.util.Timer timer = new java.util.Timer();

    public static Date parseDateTime(String input) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        sdf.setLenient(false);
        return sdf.parse(input);
    }

    public static void scheduleReminder(EventPlanner.Event event) {
        Date reminderTime = event.getDate();

        TimerTask reminderTask = new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    JFrame frame = new JFrame();
                    frame.setAlwaysOnTop(true);
                    frame.setUndecorated(true);
                    frame.setVisible(true);

                    JOptionPane.showMessageDialog(
                            frame,
                            "🔔 Reminder: " + event.getName() + "\n" + event.getDescription(),
                            "Event Reminder",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    frame.dispose();
                });

                events.remove(event);
            }
        };

        timer.schedule(reminderTask, reminderTime);
        System.out.println("📌 Reminder scheduled for: " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(reminderTime));
    }
}
