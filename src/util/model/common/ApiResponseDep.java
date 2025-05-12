package util.model.common;

import lombok.Data;
import lombok.Getter;

//   extends io.swagger.v3.oas.models.responses.ApiResponse
//@EqualsAndHashCode(callSuper = true)
@Data
public class ApiResponseDep {

    /** frm swg api respose
     *  private String description = null;
     *     private Map<String, Header> headers = null;
     *     private Content content = null;
     */
    @Getter
    private String message;
    private Object data;
    private int statusCode;
    public String statusCodeStr;
    public String errcode;
    // Constructors, getters, setters

    public ApiResponseDep(String message, Object data, int statusCode) {
        this.message = message;
        this.data = data;
        this.statusCode = statusCode;
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

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
