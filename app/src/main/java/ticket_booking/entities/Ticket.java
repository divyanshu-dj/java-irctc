package ticket_booking.entities;

public class Ticket {
    private String ticketId;
    private String userId;
    private String source;
    private String destination;
    private String travelDate;
    private Train train;

    public Ticket(){}

    public Ticket(String ticketId, String userId, String source, String destination, String travelDate, Train train){
        this.ticketId = ticketId;
        this.userId = userId;
        this.source = source;
        this.destination = destination;
        this.travelDate = travelDate;
        this.train = train;
    }

    public String getTicketInfo(){
        return String.format("Ticket ID: %s belongs to User %s from %s to %s on %s", ticketId, userId, source, destination, travelDate);
    }

    public String getTicketId(){
        return ticketId;
    }

    public void setTicketId(String ticketId){
        this.ticketId = ticketId;
    }

    public String getSource(){
        return source;
    }

    public void setSource(String source){
        this.source = source;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getDestination(){
        return destination;
    }

    public void setDestination(String destination){
        this.destination = destination;
    }

    public String gettravelDate(){
        return travelDate;
    }

    public void settravelDate(String travelDate){
        this.travelDate = travelDate;
    }

    public Train getTrain(){
        return train;
    }

    public void setTrain(Train train){
        this.train = train;
    }
}
