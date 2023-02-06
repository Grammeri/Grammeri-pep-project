package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    // Create message (3)

    public Message createMessage(Message message) {

        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into message (posted_by, message_text, time_posted_epoch) values (?,?,?);";
            // PreparedStatement preparedStatement = connection.prepareStatement(sql);
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generated_message_id = (int) pkeyResultSet.getInt(1);
    
                return new Message(generated_message_id, message.posted_by, message.getMessage_text(),
                        message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Retrieve all messages (4)
    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "select * from message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    // Retrieve a message by it's id - (5)

    public Message getMessageById(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return message;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Delete message by a message ID - (6)

    // Update message by text identified by a message id - (7)
    public void updateMessage(int message_id, Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {

            String sql = "update message set posted_by=?, message_text=?, time_posted_epoch where flight_id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message.posted_by);
            preparedStatement.setString(2, message.message_text);
            preparedStatement.setLong(3, message_id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

// Retrieve all messages written by a particular user (8??)
/*
 * public List<Message> getParticularUserMessages(int account_id, String
 * message_text) {
 * Connection connection = ConnectionUtil.getConnection();
 * List<Message> messages = new ArrayList<>();
 * try {
 * String sql = "select * from message where posted_by = ?;";
 * PreparedStatement preparedStatement = connection.prepareStatement(sql);
 * 
 * preparedStatement.setInt(1, posted_by);
 * 
 * ResultSet rs = preparedStatement.executeQuery();
 * while (rs.next()) {
 * Message message = new Message(rs.getString("message_text"));
 * return message;
 * messages.add(message);
 * }
 * } catch (SQLException e) {
 * System.out.println(e.getMessage());
 * }
 * return messages;
 * }
 */

// Do not forget to add curly
