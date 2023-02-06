package Controller;

import java.util.List;

//import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
// import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in
     * the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * 
     * @return a Javalin app object which defines the behavior of the Javalin
     *         controller.
     */

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::newUserRegistrationHandler);
        app.post("/login", this::userLoginHandler);
        // app.get("/accounts", this::getAllUsersHandler);
        app.post("/messages", this::newMessageCreationHandler);
        app.get("/messages", this::retrieveAllMessagesHandler);
        // app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/messages/{message_id}", this::retrieveMessageByIdHandler);
        /*
         * app.get("/messages/{message_id}", this::retrieveMessageByIdHandler);
         * app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
         * app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
         * app.get("/accounts/{account_id}/messages",
         * this::retrieveAllMessagesByParticularUserHandler);
         */

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * 
     * @param context The Javalin Context object manages information about both the
     *                HTTP request and response.
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */

    // registration (1)
    private void newUserRegistrationHandler(Context ctx) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount != null) {
            ctx.json(mapper.writeValueAsString(addedAccount));
        } else {
            ctx.status(400);
        }

    }

    // login (2)
    private void userLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account loginData = mapper.readValue(ctx.body(), Account.class);

        /*
         * if (loginData.password.length() == 0 || loginData.username.length() == 0) {
         * ctx.status(400);
         * return;
         * }
         */

        Account foundAccount = accountService.getUserAccount(loginData.username, loginData.password);

        if (foundAccount == null) {
            ctx.status(401);
            return;
        }

        ctx.json(mapper.writeValueAsString(foundAccount));
    }

    // get all users
    /*
     * private void getAllUsersHandler(Context ctx) {
     * List<Account> accounts = accountService.getAllUsers();
     * ctx.json(accounts);
     * }
     */

    // Create new message (3)

    private void newMessageCreationHandler(Context ctx) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if (addedMessage != null) {
            ctx.json(mapper.writeValueAsString(addedMessage));
        } else {
            ctx.status(400);
        }
    }

    // Retrieve all messages (4)

    public void retrieveAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    // Retrieve a message by id (5) - version 1

    private void retrieveMessageByIdHandler(Context ctx) {
        //path params are of type String by default, so we convert it to int
        ctx.json(messageService.getMessageById(Integer.parseInt(ctx.pathParam("message_id"))));
    }

    // Retrieve a message by id (5) - version 2
    /*
     * private void retrieveMessageByIdHandler(Context ctx) throws
     * JsonProcessingException {
     * ObjectMapper mapper = new ObjectMapper();
     * Message messageById = mapper.readValue(ctx.body(), Message.class);
     * 
     * if (loginData.password.length() == 0 || loginData.username.length() == 0) {
     * ctx.status(400);
     * return;
     * }
     * 
     * Account foundMessageById =
     * messageService.getMessageById(messageById.message_text);
     * 
     * if (messageById == null) {
     * ctx.status(401);
     * return;
     * }
     * 
     * ctx.json(mapper.writeValueAsString(messageById));
     * }
     */

    // Delete message by id
    /*
     * private void deleteMessageByIdHandler(Context ctx) throws
     * JsonProcessingException {
     * ObjectMapper mapper = new ObjectMapper();
     * Account loginData = mapper.readValue(ctx.body(), Account.class);
     * 
     * Account foundMessage = messageService.deleteMessageById(loginData.username,
     * loginData.password);
     * 
     * if (foundMessage == null) {
     * ctx.status(200);
     * return;
     * }
     * 
     * 
     * //update message by id
     * 
     * /*private void updateMessageByIdHandler(Context ctx) throws
     * JsonProcessingException {
     * ObjectMapper mapper = new ObjectMapper();
     * Message message = mapper.readValue(ctx.body(), Message.class);
     * int message_id = Integer.parseInt(ctx.pathParam("message_id"));
     * Message updatedMessage = messageService.updateMessage(message_id, message);
     * System.out.println(updatedMessage);
     * if(updatedMessage == null){
     * ctx.status(400);
     * }else{
     * ctx.json(mapper.writeValueAsString(updatedMessage));
     * }
     * 
     * }
     */

}
