package ticket_booking.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ticket_booking.entities.Train;
import ticket_booking.entities.User;
import ticket_booking.util.UserServiceUtil;

public class UserBookingService {
    private User user;
    private final List<User> userList;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String USER_PATH = "../localDB/user.json";

    public UserBookingService(User user) throws IOException {
        this.user = user;
        this.userList = loadUserList();
    }

    public UserBookingService() throws IOException {
        this.userList = loadUserList();
    }

    private List<User> loadUserList() throws IOException {
        File file = new File(USER_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(file, new TypeReference<List<User>>() {});
    }

    public boolean loginUser() {
        return userList.stream().anyMatch(u ->
            u.getName().equals(user.getName()) &&
            UserServiceUtil.checkPassword(user.getPassword(), u.getHashedPassword())
        );
    }

    public boolean signUp(User newUser) {
        try {
            userList.add(newUser);
            saveUserListToFile();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USER_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

    public void fetchBooking() {
        if (user != null) {
            user.printTickets();
        }
    }

    public boolean cancelBooking(String ticketId) {
        try {
            Optional<User> userInList = userList.stream()
                .filter(u -> u.getName().equals(user.getName()))
                .findFirst();

            if (userInList.isPresent()) {
                userInList.get().getTicketsBooked()
                    .removeIf(ticket -> ticket.getTicketId().equals(ticketId));

                saveUserListToFile();
                return true;
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public List<Train> getTrains(String source, String destination) {
        try {
            return new TrainBookingService().searchTrains(source, destination);
        } catch (IOException e) {
            System.out.println("Error fetching trains: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<List<Boolean>> fetchSeats(Train train) {
        return train.getSeats();
    }

    public boolean bookTrainSeat(Train train, int row, int seat) {
        try {
            List<List<Boolean>> seats = train.getSeats();
            if (row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
                if (!seats.get(row).get(seat)) {
                    seats.get(row).set(seat, true);
                    train.setSeats(seats);
                    new TrainBookingService().addOrUpdateTrain(train);
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
