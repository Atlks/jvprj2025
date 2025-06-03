package util;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class NodeImplMixin {
    @JsonIgnore
    abstract Integer getParameterIndex();
}