package Service;

import java.util.List;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    public MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    // Task 3 New message creation
    public Message addMessage(Message message) {

        if (message.message_text.length() == 0 || message.message_text.length() > 255) {
            return null;
        }
        return messageDAO.createMessage(message);
    }

    // Task 4 Getting all messages
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    // Task 5 Getting a message by its ID
    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    // Task 6 Deleting a message by its ID
    public Message deleteMessageById(int message_id) {
        return messageDAO.deleteMessageById(message_id);
    }

    // Task 7 Updating a message by its ID
    public Message updateMessage(int message_id, Message message) {
        if (message.message_text.length() == 0 || message.message_text.length() > 255) {
            return null;
        }
        messageDAO.updateMessage(message_id, message);
        return this.messageDAO.getMessageById(message_id);
    }

    // Task 8 Getting messages written by a particular user
    public List<Message> getAllMessagesByUser(int account_id) {
        return this.messageDAO.getAllMessagesByAccountId(account_id);
    }
}