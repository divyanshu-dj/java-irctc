package ticket_booking.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ticket_booking.entities.Train;

public class TrainBookingService {

    private static final String TRAIN_DB_PATH = "../localDB/trains.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private List<Train> trainList;

    public TrainBookingService() {
        try {
            this.trainList = loadTrainList();
        } catch (IOException e) {
            this.trainList = new ArrayList<>();
            System.err.println("⚠️ Failed to load train data. Starting with empty list.");
        }
    }

    private List<Train> loadTrainList() throws IOException {
        File file = new File(TRAIN_DB_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(file, new TypeReference<List<Train>>() {});
    }

    private void saveTrainListToFile() throws IOException {
        File file = new File(TRAIN_DB_PATH);
        objectMapper.writeValue(file, trainList);
    }

    public List<Train> searchTrains(String source, String destination) throws IOException {
        if (source == null || destination == null) return Collections.emptyList();

        return trainList.stream()
                .filter(train -> isValidTrain(train, source, destination))
                .collect(Collectors.toList());
    }

    private boolean isValidTrain(Train train, String source, String destination) {
        List<String> stations = train.getStations().stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        int sourceIndex = stations.indexOf(source.toLowerCase());
        int destinationIndex = stations.indexOf(destination.toLowerCase());

        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
    }

    public void addOrUpdateTrain(Train train) throws IOException {
        OptionalInt indexOpt = IntStream.range(0, trainList.size())
                .filter(i -> trainList.get(i).getTrainId().equalsIgnoreCase(train.getTrainId()))
                .findFirst();

        if (indexOpt.isPresent()) {
            trainList.set(indexOpt.getAsInt(), train); // Update existing
        } else {
            trainList.add(train); // Add new
        }

        saveTrainListToFile();
    }

    public List<Train> getAllTrains() {
        return new ArrayList<>(trainList); // Return a copy to protect internal list
    }
}
