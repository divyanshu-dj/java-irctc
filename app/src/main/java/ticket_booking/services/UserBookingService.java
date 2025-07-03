package ticket_booking.services;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

import ticket_booking.entities.User;

public class UserBookingService {
    private User user;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String USER_PATH = "../localDB/user.json";

    public UserBookingService(User user) {
        this.user = user;
        File file = new File(USER_PATH);

    }


}
