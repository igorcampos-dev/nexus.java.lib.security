package model.dto;

import com.nexus.security.model.dto.Error;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorTest {

    @Test
    void testErrorRecord() {
        String timestamp = "2022-02-12T12:00:00";
        Integer status = 404;
        String error = "Not Found";
        String message = "Resource not found";
        String path = "/api/users";

        Error errorRecord = Error.builder()
                .timestamp(timestamp)
                .status(status)
                .error(error)
                .message(message)
                .path(path)
                .build();

        assertEquals(timestamp, errorRecord.timestamp());
        assertEquals(status, errorRecord.status());
        assertEquals(error, errorRecord.error());
        assertEquals(message, errorRecord.message());
        assertEquals(path, errorRecord.path());
    }
}
