package Service;

import java.util.List;

// import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    public MessageDAO messageDAO;

    // No-args constructor
    public MessageService() {
        messageDAO = new MessageDAO();
    }

    // @param messageDAO
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    // Create messages
    public Message addMessage(Message message) {
        // Account account_id = this.accountDAO.getAccountById(account.account_id);
        if (message.message_text.length() == 0 || message.message_text.length() > 255) {
            return null;
        }
        return messageDAO.createMessage(message);
    }

    // @ return all messages
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    // retrieve message by id - version 1
    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessageById(int message_id) {
        return messageDAO.deleteMessageById(message_id);
    }

    public Message updateMessage(int message_id, Message message) {
        if (message.message_text.length() == 0 || message.message_text.length() > 255) {
            return null;
        }
        messageDAO.updateMessage(message_id, message);
        return this.messageDAO.getMessageById(message_id);
    }
}
// if(messageFromDb == null) return null;

// //flightDAO.updateFlight(flight_id, flight);
// return this.messageDAO.getMessageById(message_id);
// }

// retrieve message by id - version 2
// public Message getMessageById(int message_id) {
// return accountDAO.getAccountByUsernameAndPassword(username, password);
// }

// messageDAO.updateMessage(message_id, message);
// return this.messageDAO.getMessageById(message_id);

// }

// }

// Message messageFromDb = this.messageDAO.getMessageById(message_id);

// if(messageFromDb == null) return null;

// messageDAO.getMessageById(message_id, message);
// return this.messageDAO.getMessageById(message_id);

// if (message.message_text.length() == 0 || message.message_text.length() > 255
// || message.message_id == 0) {
// return null;
// }