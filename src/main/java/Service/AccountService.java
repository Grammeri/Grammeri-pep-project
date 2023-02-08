package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    // @param accountDAO
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    // Task 1 User Registration.
    public Account addAccount(Account account) {

        if (account.username.length() == 0 || account.password.length() < 4) {
            return null;
        }
        return accountDAO.createAccount(account);
    }

    // Task 2 Login
    public Account getUserAccount(String username, String password) {
        return accountDAO.getAccountByUsernameAndPassword(username, password);
    }

}
