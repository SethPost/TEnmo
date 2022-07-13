package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.UserService;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final BigDecimal ZERO = new BigDecimal("0.00");

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final UserService userService = new UserService();

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        } else {
            userService.setAuthToken(currentUser.getToken());
        }
       //currentUser.setToken(currentUser.getToken()); //reference lecture final d16
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                thankYou();
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
		// TODO Auto-generated method stub
        long longUserId = currentUser.getUser().getId();
        int userId = (int)longUserId;
        Account account = userService.getAccountByUserId(userId);
        int accountId = account.getId();
       System.out.println("Your account balance is: $" + userService.getBalance(accountId));
		
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
        long longUserId = currentUser.getUser().getId();
        int userId = (int)longUserId;
        Transfer[] transfers = userService.listTransfers(userId);
        User toUser = null;
        User fromUser = null;
        for (Transfer transfer : transfers) {
            Account fromAccount = userService.getAccount(transfer.getAccountFromId());
            int fromUserId = fromAccount.getUserId();
            fromUser = userService.getUserById(fromUserId);
            transfer.setFromUserName(fromUser.getUsername());

            Account toAccount = userService.getAccount(transfer.getAccountToId());
            int toUserId = toAccount.getUserId();
            toUser = userService.getUserById(toUserId);
            transfer.setToUserName(toUser.getUsername());

            System.out.println(transfer.toString());
        }
        System.out.println();
        int transferId = consoleService.promptForInt("Please enter a transfer Id number to view details, or press '0' to exit: ");
        if (transferId == 0) {
        } else {
            Transfer specificTransfer = null;
            while (specificTransfer == null) {
                specificTransfer = userService.getTransferById(transferId);
                if (specificTransfer != null) {
                    System.out.println("---------------------");
                    System.out.println("Transfer Details");
                    System.out.println("---------------------");
                    System.out.println("Id: " + specificTransfer.getId());
                    System.out.println("From: " + fromUser.getUsername());
                    System.out.println("To: " + toUser.getUsername());
                    System.out.println("Type: " + specificTransfer.getTypeDescription());
                    System.out.println("Status: " + specificTransfer.getStatusDescription());
                    System.out.println("Amount: " + specificTransfer.getAmount());
                } else {
                    transferId = consoleService.promptForInt("Please enter a valid transfer Id number, or press '0' to exit: ");
                    if (transferId == 0) {
                        break;
                    } else {
                        specificTransfer = userService.getTransferById(transferId);
                        if (specificTransfer != null) {
                            System.out.println("---------------------");
                            System.out.println("Transfer Details");
                            System.out.println("---------------------");
                            System.out.println("Id: " + specificTransfer.getId());
                            System.out.println("From: " + fromUser.getUsername());
                            System.out.println("To: " + toUser.getUsername());
                            System.out.println("Type: " + specificTransfer.getTypeDescription());
                            System.out.println("Status: " + specificTransfer.getStatusDescription());
                            System.out.println("Amount: " + specificTransfer.getAmount());
                        }
                    }
                }
            }
        }
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
        System.out.println("We're sorry, that service is currently unavailable.");
	}

	private void sendBucks() {
		// TODO Auto-generated method stub
        Transfer transferEnteredByUser = new Transfer();

        User[] users = userService.findAllUsers();
        for (User user : users) {
            System.out.println(user.toString());
        }

        long longUserId = currentUser.getUser().getId();
        int fromUserId = (int)longUserId;
        Account fromAccount = userService.getAccountByUserId(fromUserId);
        int fromAccountId = fromAccount.getId();
        transferEnteredByUser.setAccountFromId(fromAccountId);

        User fromUser = userService.getUserById(fromUserId);
        String fromUserName = fromUser.getUsername();
        transferEnteredByUser.setFromUserName(fromUserName);

        int toUserId = consoleService.promptForInt("Please enter the user ID for the person you would like to send TE bucks to, or press '0' to exit: ");
        if (toUserId == 0) {

        }
        else {
            User toUser = userService.getUserById(toUserId);
            while (toUserId == currentUser.getUser().getId() || toUser == null) {
                if (toUserId == currentUser.getUser().getId()) {
                    toUserId = consoleService.promptForInt("You cannot enter your own ID. Please try again: ");
                    toUser = userService.getUserById(toUserId);
                } else if (toUser == null) {
                    toUserId = consoleService.promptForInt("Please enter a valid user ID: ");
                    toUser = userService.getUserById(toUserId);
                    if (toUserId == 0) {
                        break;
                    }
                } else {
                    Account toAccount = userService.getAccountByUserId(toUserId);
                    int toAccountId = toAccount.getId();
                    transferEnteredByUser.setAccountToId(toAccountId);

                    String toUserName = toUser.getUsername();
                    transferEnteredByUser.setToUserName(toUserName);

                    BigDecimal amount = consoleService.promptForBigDecimal("Please enter the amount of TE bucks you would like to send, or press '0' to exit: ");
                    if (amount.compareTo(ZERO) <= 0) {
                        while (amount.compareTo(ZERO) < 0) {
                            amount = consoleService.promptForBigDecimal("Please enter a number greater than 0, or press '0' to exit: ");
                        }
                        if (amount.equals(ZERO)) {

                        }
                    } else {
                        transferEnteredByUser.setAmount(amount);

                        Transfer transferFromApi = userService.sendTransfer(transferEnteredByUser);
                        if (transferFromApi == null) {
                            consoleService.printErrorMessage();
                        } else {
                            System.out.println("TE bucks sent successfully!");
                        }
                    }
                }
            }
            Account toAccount = userService.getAccountByUserId(toUserId);
            int toAccountId = toAccount.getId();
            transferEnteredByUser.setAccountToId(toAccountId);

            String toUserName = toUser.getUsername();
            transferEnteredByUser.setToUserName(toUserName);

            BigDecimal amount = consoleService.promptForBigDecimal("Please enter the amount of TE bucks you would like to send, or press '0' to exit: ");
            if (amount.compareTo(ZERO) <= 0) {
                while (amount.compareTo(ZERO) < 0) {
                    amount = consoleService.promptForBigDecimal("Please enter a number greater than 0, or press '0' to exit: ");
                }
                if (amount.equals(ZERO)) {

                }
            } else {
                transferEnteredByUser.setAmount(amount);

                Transfer transferFromApi = userService.sendTransfer(transferEnteredByUser);
                if (transferFromApi == null) {
                    consoleService.printErrorMessage();
                } else {
                    System.out.println("TE bucks sent successfully!");
                }
            }
        }
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
        System.out.println("We're sorry, that service is currently unavailable.");
	}

    private void thankYou() {
        System.out.println("Thank you for using TEnmo! Have a nice day!");
    }

}
