package webSocketMessages.serverMessages;

public class ErrorMessgae extends ServerMessage{
    public String error;
    public ErrorMessgae(String error) {
        super(ServerMessageType.ERROR);
        this.error = error;
    }
}
