package cfg;

public interface  HttpHandlerX<T>  {
    public abstract Object handlex(T dto) throws Exception;
}
