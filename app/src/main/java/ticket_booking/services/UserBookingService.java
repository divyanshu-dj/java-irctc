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
    private List<User> userList;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String USER_PATH = "../localDB/user.json";

    public UserBookingService(User user) throws IOException {
        this.user = user;
        loadUserList();
    }

    public UserBookingService() throws IOException {
        loadUserList();
    }

    public List<User> loadUserList() throws IOException {
        File file = new File(USER_PATH);
        // userList = objectMapper.readValue(file, User[].class); //this will not work due to type erasure
        return objectMapper.readValue(file, new TypeReference<List<User>>() {});
    }

    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }

    public void saveUserListToFile() throws IOException {
        File usersFile = new File(USER_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

    public void fetchBooking() {
        user.printTickets();
    }

    public Boolean cancelBooking(String ticketId) {
        try {
            Optional<User> userInList = userList.stream()
                    .filter(u -> u.getName().equals(user.getName()))
                    .findFirst();

            if (userInList.isPresent()) {
                userInList.get().getTicketsBooked()
                        .removeIf(ticket -> ticket.getTicketId().equals(ticketId));

                saveUserListToFile();
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }

        } catch (IOException e) {
            return Boolean.FALSE;
        }
    }

    public List<Train> getTrains(String source, String destination) {
        try {
            TrainBookingService trainBookingService = new TrainBookingService();
            return trainBookingService.searchTrains(source, destination);
        } catch (IOException ex) {
            System.out.println("Error fetching trains: " + ex.getMessage());
            return new ArrayList<>(); // Return an empty list on error
        }
    }

    public List<List<Boolean>> fetchSeats(Train train){
            return train.getSeats();
    }

    public Boolean bookTrainSeat(Train train, int row, int seat) {
        try{
            TrainBookingService trainBookingService = new TrainBookingService();
            List<List<Boolean>> seats = train.getSeats();
            if (row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
                if (!seats.get(row).get(seat)) {
                    seats.get(row).set(seat, true);
                    train.setSeats(seats);
                    trainBookingService.addOrUpdateTrain(train);
                    return true; // Booking successful
                } else {
                    return false; // Seat is already booked
                }
            } else {
                return false; // Invalid row or seat index
            }
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }
}
