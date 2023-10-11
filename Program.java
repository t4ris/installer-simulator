import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Program {
    private JFrame frame;
    private JFrame installationFrame;
    private JLabel titleLabel;
    private JLabel descriptionLabel;
    private JProgressBar progressBar;
    private JButton runButton;

    private int numberOfFiles;
    private int secondsPerFile;
    private int currentFile;

    public Program() {
        frame = new JFrame();
        createInitialFrame();
    }

    private void createInitialFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 1));

        JLabel titleLabelLabel = new JLabel("Title:");
        JTextField titleInput = new JTextField(30);

        JLabel descriptionLabelLabel = new JLabel("Description:");
        JTextField descriptionInput = new JTextField(30);

        JLabel filesLabel = new JLabel("Number of Files:");
        JTextField filesInput = new JTextField(10);

        JLabel secondsLabel = new JLabel("Seconds Per File:");
        JTextField secondsInput = new JTextField(10);

        titleLabel = new JLabel("");
        descriptionLabel = new JLabel("");

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);

        runButton = new JButton("Run");
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    titleLabel.setText(titleInput.getText());
                    descriptionLabel.setText(descriptionInput.getText());
                    numberOfFiles = Integer.parseInt(filesInput.getText());
                    secondsPerFile = Integer.parseInt(secondsInput.getText());
                    clearInputFields(titleInput, descriptionInput, filesInput, secondsInput);
                    createInstallationFrame();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid numbers for files and seconds.");
                }
            }
        });

        frame.add(titleLabelLabel);
        frame.add(titleInput);
        frame.add(descriptionLabelLabel);
        frame.add(descriptionInput);
        frame.add(filesLabel);
        frame.add(filesInput);
        frame.add(secondsLabel);
        frame.add(secondsInput);
        frame.add(runButton);

        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    private void createInstallationFrame() {
        installationFrame = new JFrame();
        installationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        installationFrame.setLayout(new GridLayout(4, 1));

        JLabel titleLabel = new JLabel(this.titleLabel.getText(), SwingConstants.CENTER);
        JLabel descriptionLabel = new JLabel(this.descriptionLabel.getText(), SwingConstants.CENTER);

        progressBar = new JProgressBar(0, numberOfFiles);
        progressBar.setValue(0);

        installationFrame.add(titleLabel);
        installationFrame.add(descriptionLabel);
        installationFrame.add(progressBar);
        installationFrame.setSize(400, 200);
        installationFrame.setVisible(true);

        simulateInstallation();
    }

    private void simulateInstallation() {
        currentFile = 0;
        progressBar.setValue(0);

        Timer timer = new Timer(1000 * secondsPerFile, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progressBar.setValue(currentFile + 1);
                currentFile++;

                if (currentFile == numberOfFiles) {
                    JOptionPane.showMessageDialog(installationFrame, "You have successfully installed " + titleLabel.getText());
                    installationFrame.dispose();
                    frame.getContentPane().removeAll();
                    createInitialFrame();
                }
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    private void clearInputFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Program();
            }
        });
    }
}
