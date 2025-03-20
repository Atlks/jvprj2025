package service.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface IsamLogMng {


    //--------------log
    public void addLogVldFail(@NotBlank String uid, @NotNull Throwable e);

    public void addLogVldSucess(@NotBlank String uid);

    default @NotNull List<Object> listLog() {
        return List.of();
    }
}
