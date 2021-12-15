package dto;

public class JsonResponse {

    private Boolean successful;
    private String message;
    private Object data;

    public JsonResponse(Boolean successful, String message, Object data) {
        this.successful = successful;
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsonResponse{" +
                "successful=" + successful +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
