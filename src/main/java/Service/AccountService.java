package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;
    
    //No-arg constructor

    public AccountService(){
accountDAO = new AccountDAO();
    }
    //@param accountDAO
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account){
        //Account account_id = this.accountDAO.getAccountById(account.account_id);
        if(account.account_id != 0 && account.username !="" && account.password.length()>=4) return null;
        return accountDAO.createAccount(account);
    }

}
