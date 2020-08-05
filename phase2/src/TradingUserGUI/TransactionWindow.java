package TradingUserGUI;

import Exceptions.InvalidItemException;
import Items.Item;
import Transactions.Meeting;
import Users.TradingUser;
import Users.UserMenuController;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TransactionWindow {
    private UserMenuController umc;
    private Item item;
    private Item offeredItem;
    private boolean twoWay;
    private TradingUser owner;
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private String firstLocation;
    private Date firstDate;
    private Date firstTime;
    private String secondLocation;
    private Date secondDate;
    private Date secondTime;
    private JButton submit = new JButton("Submit");
    private Calendar dateCalendar;
    private Calendar timeCalendar;

    public TransactionWindow(UserMenuController umc, Item item, TradingUser owner) {
        this.umc = umc;
        this.item = item;
        this.owner = owner;
    }

    public void display() {
        JFrame frame = new JFrame("Create Transaction");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(1280, 720));
        frame.setLocationRelativeTo(null);

        // set the panels on the frame
        frame.add(panel1, BorderLayout.WEST);
        frame.add(panel2, BorderLayout.EAST);

        setupLeftPanel();

        // display the window
        frame.setVisible(true);
    }

    private void setupLeftPanel() {
        panel1.add(new JLabel("Transaction Status"));

        // create the radio buttons
        JRadioButton virtualButton = new JRadioButton("Virtual");
        JRadioButton permButton = new JRadioButton("Permanent");
        JRadioButton tempButton = new JRadioButton("Temporary");

        // group the buttons
        ButtonGroup group = new ButtonGroup();
        group.add(virtualButton);
        group.add(permButton);
        group.add(tempButton);

        // add event handlers for the buttons
        virtualButton.addActionListener(e -> {
            setupRightPanel("Virtual");
        });

        permButton.addActionListener(e -> {
            setupRightPanel("Perm");
        });

        tempButton.addActionListener(e -> {
            setupRightPanel("Temp");
        });

        // add the buttons to the panel
        panel1.add(virtualButton);
        panel1.add(permButton);
        panel1.add(tempButton);
    }

    private void setupRightPanel(String type) {
        panel2.add(new JLabel("Would you like to offer one of your items?"));

        List<Item> inventory = umc.getIm().convertIdsToItems(owner.getInventory());

        // create JComboBox of user's inventory
        JComboBox<Item> comboBox = new JComboBox<>();

        for (Item item : inventory) {
            comboBox.addItem(item);
        }

        // add event handler for comboBox
        comboBox.addActionListener(e -> {
            if (comboBox.getSelectedItem() == null) {
                // offeredItem = null;
                twoWay = false;
            }
            else {
                twoWay = true;
                offeredItem = (Item) comboBox.getSelectedItem();
            }
        });

        // add comboBox to panel
        panel2.add(comboBox);

        // add meetings fields
        if (type.equals("Perm")) {
            panel2 = setMeetingPanel("First");
            submit.addActionListener(e -> {
                try {
                    areYouSureWindow(type);
                } catch (InvalidItemException invalidItemException) {
                    invalidItemException.printStackTrace();
                }
            });
            panel2.add(submit);
        }
        else if (type.equals("Temp")) {
            panel2 = setMeetingPanel("First");
            submit.addActionListener(e -> secondMeetingWindow());
            panel2.add(submit);
        }
    }

    private void setDateCalendar() { // helper method for making a date JSpinner
        dateCalendar = Calendar.getInstance();
        dateCalendar.set(Calendar.DAY_OF_MONTH, 1);
        dateCalendar.set(Calendar.MONTH, 1);
        dateCalendar.set(Calendar.YEAR, 2020);
    }

    private void setTimeCalendar() { // helper method for making a time JSpinner
        timeCalendar = Calendar.getInstance();
        timeCalendar.set(Calendar.HOUR_OF_DAY, 24);
        timeCalendar.set(Calendar.MINUTE, 0);
    }

    private JPanel setMeetingPanel(String meetingNum) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(meetingNum + "Meeting"));

        JTextField location = new JTextField("Location");
        location.setBounds(100, 100, 50, 20);
        location.addActionListener(e -> {
            if (meetingNum.equals("first")) { firstLocation = location.getText(); }
            if (meetingNum.equals("second")) { secondLocation = location.getText(); }
        });

        setDateCalendar();
        SpinnerDateModel dateModel = new SpinnerDateModel();
        dateModel.setValue(dateCalendar.getTime());

        JSpinner meetingDate = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(meetingDate, "dd:mm:yyyy");
        meetingDate.setEditor(dateEditor);
        meetingDate.addChangeListener(e -> {
            if (meetingNum.equals("first")) { firstDate = dateModel.getDate(); }
            if (meetingNum.equals("second")) { secondDate = dateModel.getDate(); }
        });

        setTimeCalendar();
        SpinnerDateModel timeModel = new SpinnerDateModel();
        timeModel.setValue(timeCalendar.getTime());

        JSpinner meetingTime = new JSpinner(timeModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(meetingTime, "hh:mm");
        meetingTime.setEditor(editor);
        meetingTime.addChangeListener(e -> {
            if (meetingNum.equals("first")) { firstTime = timeModel.getDate(); }
            if (meetingNum.equals("second")) { secondTime = timeModel.getDate(); }
        });

        panel.add(location);
        panel.add(meetingDate);
        panel.add(meetingTime);
        return panel;
    }

    private void areYouSureWindow(String type) throws InvalidItemException {
        List<UUID> itemList = new ArrayList<>();
        itemList.add(item.getId());
        if (twoWay) {
            itemList.add(offeredItem.getId());
        }
        JFrame frame = new JFrame();
        Meeting firstMeeting = new Meeting(firstLocation, firstTime, firstDate);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            int a = JOptionPane.showConfirmDialog(frame, "Are you sure you want to cancel the transaction?");
            if (a == JOptionPane.YES_OPTION) {
                if (type.equals("Perm")) {
                    umc.buildTransaction(itemList, owner, firstMeeting);
                    // cancelledWindow();
                }
                if (type.equals("Temp")) {
                    Meeting secondMeeting = new Meeting(secondLocation, secondTime, secondDate);
                    umc.buildTransaction(itemList, owner, firstMeeting, secondMeeting);
                }
            }
            if (a == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        }

    private void secondMeetingWindow() {
            JFrame tempFrame = new JFrame("Second Meeting");
            JPanel panel3 = setMeetingPanel("Second");
            JButton submit2 = new JButton("Submit Second Meeting");
            submit2.addActionListener(e -> {
                try {
                    areYouSureWindow("Temp");
                } catch (InvalidItemException invalidItemException) {
                    invalidItemException.printStackTrace();
                }
            });
            panel3.add(submit2);
            tempFrame.add(panel3);
        }
    }
