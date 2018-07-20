package codesecurity.api.request;

import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;

public abstract class CodeSecurityRequest<T> {
    @NotNull
    private ObjectId id;

    private T type;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public T getType() {
        return type;
    }

    public void setType(T type) {
        this.type = type;
    }

}
