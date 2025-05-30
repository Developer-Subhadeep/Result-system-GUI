import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResultForm extends JFrame {
    JTextField nameField, rollField, subjectField, marksField;
    JButton submitBtn;

    public ResultForm() {
        setTitle("Result System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setBounds(50, 30, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(160, 30, 150, 25);
        add(nameField);

        JLabel rollLabel = new JLabel("Roll Number:");
        rollLabel.setBounds(50, 70, 100, 25);
        add(rollLabel);

        rollField = new JTextField();
        rollField.setBounds(160, 70, 150, 25);
        add(rollField);

        JLabel subjectLabel = new JLabel("Subject:");
        subjectLabel.setBounds(50, 110, 100, 25);
        add(subjectLabel);

        subjectField = new JTextField();
        subjectField.setBounds(160, 110, 150, 25);
        add(subjectField);

        JLabel marksLabel = new JLabel("Marks:");
        marksLabel.setBounds(50, 150, 100, 25);
        add(marksLabel);

        marksField = new JTextField();
        marksField.setBounds(160, 150, 150, 25);
        add(marksField);

        submitBtn = new JButton("Submit");
        submitBtn.setBounds(130, 200, 100, 30);
        add(submitBtn);

        submitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertResult();
            }
        });

        setVisible(true); // <-- Important to show the GUI
    }

    void insertResult() {
        String name = nameField.getText();
        String roll = rollField.getText();
        String subject = subjectField.getText();
        int marks;

        try {
            marks = Integer.parseInt(marksField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid marks (numbers only)");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO results(student_name, roll_number, subject, marks) VALUES (?, ?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, roll);
            stmt.setString(3, subject);
            stmt.setInt(4, marks);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Result saved successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Unexpected Error: " + ex.getMessage());
        }
    }
}
