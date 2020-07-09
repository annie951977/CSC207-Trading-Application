package Presenters;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuPresenter {
    /**
     * Formats and displays a list of options to the user.
     * @param OptionList the list of options that you want to be displayed.
     */

    private String optionPrompt = "Please type a number corresponding to one of the above options.";

    protected void displayOptions(List<String> OptionList){
        for(int i = 0; i < OptionList.size(); i++){
            String index = Integer.toString(i+1);
            String OutputLine =  index + ". " + OptionList.get(i);
            System.out.println(OutputLine);
        }
        System.out.println(optionPrompt);
    }

    /**
     * Adds Back option at the end of options being displayed to the user.
     * @param OptionList The list of options being displayed prior to calling this method.
     */
    protected void addBackOption(List<String> OptionList){
        String LastIndex = Integer.toString(OptionList.size() + 1);
        String LastOption = ". Go back";
        OptionList.add(LastIndex + LastOption);
    }

    /**
     * Construct methods like this return a list of options/prompts that the menu will have.
     *
     * This particular method constructs the option list that the user will be greeted with upon first logging into
     * the program.
     * @return this returns a list of options that the user can choose from.
     */
    public List<String> constructMainMenu(){ // TO-DO: remove body
        List<String> MenuOptionList = new ArrayList<>();
        MenuOptionList.add("Request Item for Approval");
        MenuOptionList.add("Browse Available Items for Trade");
        MenuOptionList.add("View Active Transactions");
        MenuOptionList.add("View Past Transaction Details");
        MenuOptionList.add("View Wishlist");
        MenuOptionList.add("View Inventory");
        MenuOptionList.add("Request Admin to Unfreeze Account");
        MenuOptionList.add("Log Out");
        return(MenuOptionList);
    }

    /**
     * This method takes in a list of options and handles option display and selection.(generic)
     * @param OptionList the list of options you want displayed.
     * @param BackOption boolean representing if you want a back option appended to the option list and displayed.
     * @param OptionTitle the title of the menu.
     * @return this returns the option that was selected by the user as a string.
     */
    public String handleOptions(List<String> OptionList, boolean BackOption, String OptionTitle) {
        if (BackOption) {
            this.addBackOption(OptionList);
        }
        System.out.println(OptionTitle);
        this.displayOptions(OptionList);
        return(this.selectOption(OptionList));
    }

    private String selectOption(List<String> OptionList){
        Scanner scanner = new Scanner(System.in);
        int OptionChosen;
        do {
            while (!scanner.hasNextInt()) {
                System.out.println("That is not a valid option. Please enter a number corresponding to one of the options.");
                scanner.next();
            }
            OptionChosen = scanner.nextInt();
        } while (OptionChosen > OptionList.size() || OptionChosen <= 0);
        return(OptionList.get(OptionChosen-1));
    }

    private int GetUserInt(String ErrorMsg){
        Scanner scanner = new Scanner(System.in);
        int UserInt;
        do {
            while (!scanner.hasNextInt()) {
                System.out.println("That is not a valid number.");
                scanner.next();
            }
            UserInt = scanner.nextInt();
        } while (UserInt < 0);
        return(UserInt);
    }

    /**
     * This method takes in a list of options and handles option display and selection. (generic)
     * @param OptionList the list of options you want displayed.
     * @param BackOption boolean representing if you want a back option appended to the option list and displayed.
     * @param OptionTitle the title of the option's page.
     * @return returns the index of the option chosen by the user corresponding the option list that was passed in.
     *          So that optionlist.get(return value) gives the option that the user has chosen.
     */
    public int handleOptionsByIndex(List<String> OptionList, boolean BackOption, String
            OptionTitle) {
        if (BackOption) {
            this.addBackOption(OptionList);
        }
        System.out.println(OptionTitle);
        this.displayOptions(OptionList);
        return(this.selectOptionByIndex(OptionList));
    }

    private int selectOptionByIndex(List<String> OptionList){
        Scanner scanner = new Scanner(System.in);
        int OptionChosen;
        do {
            while (!scanner.hasNextInt()) {
                System.out.println("That is not a valid option. Please enter a number corresponding to one of the options.");
                scanner.next();
            }
            OptionChosen = scanner.nextInt();
        } while (OptionChosen > OptionList.size() || OptionChosen <= 0);
        return(OptionChosen -1);
    }
    /** inputTimeGetter
     * Checks the date string that the user has inputted to see if it is in the accepted format.
     * @return this returns tru iff Returns true iff it is
     *      in the accepted format dd/mm/yyyy.
     */

    public LocalTime inputTimeGetter(String optionTimePrompt){
        Scanner scanner = new Scanner(System.in);
        LocalTime returnTime;
        while (true) {
            try {
                System.out.println(optionTimePrompt);
                String DateString = scanner.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                returnTime = LocalTime.parse(DateString, formatter);
                break;
            }
            catch(DateTimeParseException e){
                System.out.println("Invalid time please,try again.");

            }
        }
        return(returnTime);
    }


    /** inputDateGetter
     * This method prompts the user for information to construct a date object, then
     * @param optionDatePrompt
     * @return
     */
    public LocalDate inputDateGetter(String optionDatePrompt){
        Scanner scanner = new Scanner(System.in);
        LocalDate returnDate;
        while (true) {
            try {
                System.out.println(optionDatePrompt);
                String DateString = scanner.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
                returnDate = LocalDate.parse(DateString, formatter);
                break;
            }
            catch(DateTimeParseException e){
                System.out.println("Invalid Date please,try again.");

            }
        }
        return(returnDate);
    }
}
