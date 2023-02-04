package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    public MessageDAO messageDAO;

    //No-args constructor
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    //@param messageDAO
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    //Create messages
    public Message addMessage(Message message) {
        // Account account_id = this.accountDAO.getAccountById(account.account_id);
        if (message.message_text.length() == 0 || message.message_text.length() > 255 || message.message_id == 0) {
            return null;
        }
        return messageDAO.createMessage(message);
    }

    //@ return all messages
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
}
