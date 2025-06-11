package com.event_planner;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventPlanner {

    public static class Event {
        private final String name;
        private final Date date;
        private final String description;

        public Event(String name, Date date, String description) {
            this.name = name;
            this.date = date;
            this.description = description;
        }

        public String getName() { return name; }

        public Date getDate() { return date; }

        public String getDescription() { return description; }

        @Override
        public String toString() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return "Event: " + name + ", Date: " + sdf.format(date) + ", Description: " + description;
        }
    }
}
