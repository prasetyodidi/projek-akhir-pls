package components;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.stream.IntStream;

public class DatePicker extends JPanel {
    private JComboBox<Integer> dayComboBox;
    private JComboBox<Month> monthComboBox;
    private JComboBox<Integer> yearComboBox;

    public DatePicker() {
        setLayout(new FlowLayout());

        // Initialize day combo box with values from 1 to 31
        dayComboBox = new JComboBox<>(IntStream.rangeClosed(1, 31).boxed().toArray(Integer[]::new));

        // Initialize month combo box with values from January to December
        monthComboBox = new JComboBox<>(Month.values());

        // Initialize year combo box with values from 1900 to current year + 10
        int currentYear = LocalDate.now().getYear();
        yearComboBox = new JComboBox<>(IntStream.rangeClosed(1900, currentYear + 10).boxed().toArray(Integer[]::new));

        add(dayComboBox);
        add(monthComboBox);
        add(yearComboBox);
    }

    public LocalDate getSelectedDate() {
        int day = (int) dayComboBox.getSelectedItem();
        Month month = (Month) monthComboBox.getSelectedItem();
        int year = (int) yearComboBox.getSelectedItem();
        return LocalDate.of(year, month, day);
    }

    public void setSelectedDate(LocalDate date) {
        dayComboBox.setSelectedItem(date.getDayOfMonth());
        monthComboBox.setSelectedItem(date.getMonth());
        yearComboBox.setSelectedItem(date.getYear());
    }
}
