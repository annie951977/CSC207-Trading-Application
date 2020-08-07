package Initialization;

import Popups.PopUpWindow;
import Presenters.MenuPresenter;
import TradingUserGUI.TradingUserMenu;
import Users.UserMenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// ideas taken from https://beginnersbook.com/2015/07/java-swing-tutorial/
public class RegistrationWindow {
    private final JLabel userLabel = new JLabel();
    private final JTextField userText = new JTextField(20);
    private final JLabel passwordLabel = new JLabel();
    private final JPasswordField passwordText = new JPasswordField(20);
    private final JTextField confirmText = new JPasswordField(20);
    private final JLabel confirmLabel = new JLabel();
    private final JButton createButton = new JButton();
    private final LoginController lc;
    private final UserMenuController umc;
    private MenuPresenter mp;

    public RegistrationWindow(LoginController lc, UserMenuController umc, MenuPresenter mp) {
        this.lc = lc;
        this.umc = umc;
        this.mp = mp;
    }

    public void display() {
        // create the frame
        JFrame frame = new JFrame(mp.accountCreationTitle);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // set the frame's size and centre it
        frame.setSize(new Dimension(400, 250));
        frame.setLocationRelativeTo(null);

        // layout the fields and button on the frame
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        // add action listener for the "sign up" button
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount(userText.getText(), passwordText.getText(), confirmText.getText());
                frame.dispose();
            }
        });

        // display the window
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // add username label to panel
        userLabel.setText(mp.username);
        userLabel.setBounds(50,30,80,25);
        panel.add(userLabel);

        // create field for user to enter their username
        userText.setBounds(150,30,165,25);
        panel.add(userText);

        // add password label to panel
        passwordLabel.setText(mp.password);
        passwordLabel.setBounds(50,80,80,25);
        panel.add(passwordLabel);

        // add password field to panel
        passwordText.setBounds(150,80,165,25);
        panel.add(passwordText);

        // add confirm label to panel
        confirmLabel.setText(mp.confirmPsw);
        confirmLabel.setBounds(50,130,80,25);
        panel.add(confirmLabel);

        // add confirm field to panel
        confirmText.setBounds(150,130,165,25);
        panel.add(confirmText);

        // add "sign up" button to panel
        createButton.setText(mp.signUp);
        createButton.setBounds(50, 180, 160, 25);
        panel.add(createButton);
    }

    private void createAccount(String username, String password, String password2) {
        if (lc.availableUsername(username) && password.equals(password2)) {
            lc.addTradingUser(username, password);
            new TradingUserMenu(umc).display();
        } else {
            new PopUpWindow(mp.usernameTaken).display();
        }
    }
}
