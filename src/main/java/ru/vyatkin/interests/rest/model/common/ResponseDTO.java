package ru.vyatkin.interests.rest.model.common;

import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Objects;

public class ResponseDTO<T> implements Serializable {
    private static final long serialVersionUID = 5005377922061976636L;
    private final RequestStatus requestStatus;
    private final String message;
    private final T payload;

    public ResponseDTO(RequestStatus requestStatus, String message, T payload) {
        this.requestStatus = requestStatus;
        this.message = message;
        this.payload = payload;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public String getMessage() {
        return message;
    }

    public T getPayload() {
        return payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseDTO<?> that = (ResponseDTO<?>) o;
        return requestStatus == that.requestStatus &&
                Objects.equals(message, that.message) &&
                Objects.equals(payload, that.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestStatus, message, payload);
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "requestStatus=" + requestStatus +
                ", message='" + message + '\'' +
                ", payload=" + payload +
                '}';
    }
}
