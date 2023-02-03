package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;
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

    public SocialMediaController() {
        this.accountService = new AccountService();
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
        /*
         * app.post("/messages", this::newMessageCreationHandler);
         * app.get("/messages", this::retrievAllMessagesHandler);
         * app.get("/messages/{message_id}", this::retrievMessageByIdHandler);
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

    private void userLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account loginData = mapper.readValue(ctx.body(), Account.class);

        if (loginData.password.length() == 0 || loginData.username.length() == 0) {
            ctx.status(400);
            return;
        }

        Account foundAccount = accountService.getUserAccount(loginData.username, loginData.password);

        if (foundAccount == null) {
            ctx.status(401);
            return;
        }

        ctx.json(mapper.writeValueAsString(foundAccount));
    }
   /*  private void getAllUsersHandler(Context ctx) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account loginData = mapper.readValue(ctx.body(), Account.class);

        if (loginData.password.length() == 0 || loginData.username.length() == 0) {
            ctx.status(400);
            return;
        }

        Account foundAccount = accountService.getAllAccounts();

        if (foundAccount == null) {
            ctx.status(401);
            return;
        }

        ctx.json(mapper.writeValueAsString(foundAccount));
    }*/

}