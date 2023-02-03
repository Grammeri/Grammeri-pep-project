package Service;

import DAO.AccountDAO;
import Model.Account;
import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    // No-arg constructor

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    // @param accountDAO
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account) {
        // Account account_id = this.accountDAO.getAccountById(account.account_id);
        if (account.username.length() == 0 || account.password.length() < 4){
            return null;
        }
        return accountDAO.createAccount(account);
    }

    public Account getUserAccount(String username, String password) {
        return accountDAO.getAccountByUsernameAndPassword(username, password);
    }

    
}
