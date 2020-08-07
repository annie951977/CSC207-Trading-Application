package Popups;

import javax.swing.*;
import java.awt.*;
import Presenters.MenuPresenter;

public class ChangePasswordWindow {
    private final JLabel newLabel = new JLabel();
    private final JPasswordField newPass = new JPasswordField(20);
    private final JLabel confirmLabel = new JLabel();
    private final JPasswordField confirmPass = new JPasswordField(20);
    private final JButton confirmButton = new JButton();
    private MenuPresenter mp;

    public ChangePasswordWindow(MenuPresenter mp) {
        this.mp = mp;
    }

    public void display() {
        // create the frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // set the frame's size and centre it
        frame.setSize(new Dimension(350, 200));
        frame.setLocationRelativeTo(null);

        // layout the fields and button on the frame
        JPanel panel = new JPanel();
        placeComponents(panel);
        frame.add(panel);

        // add event handler for confirm button
        confirmButton.addActionListener(e -> {

        });

        // display the window
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        newLabel.setText (mp.newPassword);
        newLabel.setBounds(10,20,80,25);
        panel.add(newLabel);

        newPass.setBounds(100,20,165,25);
        panel.add(newPass);

        confirmLabel.setText(mp.confirmPsw);
        confirmLabel.setBounds(10,50,80,25);
        panel.add(confirmLabel);

        confirmPass.setBounds(100,50,165,25);
        panel.add(confirmPass);

        confirmButton.setText(mp.changePsw);
        confirmButton.setBounds(10, 110, 160, 25);
        panel.add(confirmButton);
    }
}
