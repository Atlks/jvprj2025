package api.usr;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class AnnotatedTypeMetadataImpMock implements AnnotatedTypeMetadata {
    /**
     * @return
     */
    @NotNull
    @Override
    public MergedAnnotations getAnnotations() {
        return null;
    }
}
