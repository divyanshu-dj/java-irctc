package ticket_booking.entities;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Train {
    private String trainId;
    private String trainNumber;
    private Date departureTime;
    private Date arrivalTime;
    private List<List<Boolean>> seats;
    private Map<String, LocalTime> stationTime;
    private List<String> stations;
    
}
