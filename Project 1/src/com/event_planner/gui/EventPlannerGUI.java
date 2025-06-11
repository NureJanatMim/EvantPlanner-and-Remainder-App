package com.event_planner.gui;

import com.event_planner.EventManager;
import com.event_planner.EventPlanner;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class EventPlannerGUI extends JFrame {
    private JTextField nameField;
    private JTextField dateField;
    private JTextArea descriptionArea;
    private JButton addButton;
    private JTextArea eventListArea;

    public EventPlannerGUI() {
        setTitle("Event Planner");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel nameLabel = new JLabel("Event Name:");
        nameLabel.setBounds(20, 20, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(130, 20, 300, 25);
        add(nameField);

        JLabel dateLabel = new JLabel("Date (yyyy-MM-dd HH:mm):");
        dateLabel.setBounds(20, 60, 200, 25);
        add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(220, 60, 210, 25);
        add(dateField);

        JLabel descLabel = new JLabel("Description:");
        descLabel.setBounds(20, 100, 100, 25);
        add(descLabel);

        descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setBounds(130, 100, 300, 60);
        add(scrollPane);

        addButton = new JButton("Add Event");
        addButton.setBounds(20, 180, 120, 30);
        add(addButton);

        eventListArea = new JTextArea();
        eventListArea.setEditable(false);
        JScrollPane listScrollPane = new JScrollPane(eventListArea);
        listScrollPane.setBounds(20, 220, 440, 120);
        add(listScrollPane);

        addButton.addActionListener(e -> addEvent());
    }

    private void addEvent() {
        String name = nameField.getText().trim();
        String dateStr = dateField.getText().trim();
        String description = descriptionArea.getText().trim();

        if (name.isEmpty() || dateStr.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Missing Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Date eventDate = EventManager.parseDateTime(dateStr);
            if (eventDate.before(new Date())) {
                JOptionPane.showMessageDialog(this, "⚠ The event date is in the past.", "Invalid Date", JOptionPane.WARNING_MESSAGE);
                return;
            }

            EventPlanner.Event event = new EventPlanner.Event(name, eventDate, description);
            EventManager.events.add(event);
            EventManager.scheduleReminder(event);

            JOptionPane.showMessageDialog(this, "✅ Event added successfully!");
            updateEventList();

            nameField.setText("");
            dateField.setText("");
            descriptionArea.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Invalid date format. Use yyyy-MM-dd HH:mm.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateEventList() {
        EventManager.events.sort((e1, e2) -> e1.getDate().compareTo(e2.getDate()));
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (EventPlanner.Event e : EventManager.events) {
            sb.append(index++)
                    .append(". ")
                    .append(e.toString())
                    .append("\n");
        }
        eventListArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EventPlannerGUI().setVisible(true));
    }
}
