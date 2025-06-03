package orgx.rest;

import io.javalin.config.Key;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import io.javalin.http.HttpStatus;
import io.javalin.json.JsonMapper;
import io.javalin.plugin.ContextPlugin;
import io.javalin.security.RouteRole;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ContextImp4sdkwb implements io.javalin.http.Context {
    @Override
    public <T> T appData(@NotNull Key<T> key) {
        return null;
    }


    @Override
    public HttpServletRequest req() {
        return null;
    }


    @Override
    public HttpServletResponse res() {
        return null;
    }


    @Override
    public HandlerType handlerType() {
        return null;
    }


    @Override
    public String matchedPath() {
        return "";
    }

    @NotNull
    @Override
    public String endpointHandlerPath() {
        return "";
    }


    @Override
    public JsonMapper jsonMapper() {
        return null;
    }

    @Override
    public <T> T with(@NotNull Class<? extends ContextPlugin<?, T>> aClass) {
        return null;
    }

    @Override
    public boolean strictContentTypes() {
        return false;
    }

    @NotNull
    @Override
    public String pathParam(@NotNull String s) {
        return "";
    }

    @NotNull
    @Override
    public Map<String, String> pathParamMap() {
        return Map.of();
    }


    @Override
    public ServletOutputStream outputStream() {
        return null;
    }


    @Override
    public Context minSizeForCompression(int i) {
        return null;
    }


    @Override
    public Context result(@NotNull InputStream inputStream) {
        return null;
    }

    @Nullable
    @Override
    public InputStream resultInputStream() {
        return null;
    }

    @Override
    public void future(@NotNull Supplier<? extends CompletableFuture<?>> supplier) {

    }

    @Override
    public void redirect(@NotNull String s, @NotNull HttpStatus httpStatus) {

    }

    @Override
    public void writeJsonStream(@NotNull Stream<?> stream) {

    }


    @Override
    public Context skipRemainingHandlers() {
        return null;
    }

    @NotNull
    @Override
    public Set<RouteRole> routeRoles() {
        return Set.of();
    }
}
