package ticket_booking.entities;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Train {
    private String trainId;
    private String trainNumber;
    private Date departureTime;
    private Date arrivalTime;
    private List<List<Boolean>> seats;
    private Map<String, String> stationTimes;
    private List<String> stations;
    
    public Train(){}

    public Train(String trainId, String trainNumber, List<List<Boolean>> seats, Map<String, String> stationTimes, List<String> stations){
        this.trainId = trainId;
        this.trainNumber = trainNumber;
        this.seats = seats;
        this.stationTimes = stationTimes;
        this.stations = stations;
    }

    public List<String> getStations(){
        return stations;
    }

    public List<List<Boolean>> getSeats() {
        return seats;
    }

    public void setSeats(List<List<Boolean>> seats){
       this.seats = seats;
    }

    public String getTrainId(){
        return trainId;
    }

    public Map<String, String> getStationTimes(){
        return stationTimes;
    }

    public String getTrainNumber(){
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber){
        this.trainNumber = trainNumber;
    }

    public void setTrainId(String trainId){
        this.trainId = trainId;
    }

    public void setStationTimes(Map<String, String> stationTimes){
        this.stationTimes = stationTimes;
    }

    public void setStations(List<String> stations){
        this.stations = stations;
    }

    public String getTrainInfo(){
        return String.format("Train ID: %s Train No: %s", trainId, trainNumber);
    }
}
