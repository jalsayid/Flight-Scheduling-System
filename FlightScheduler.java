import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

// To throw appropriate exceptions when needed
// Custom exception class for when a flight already exists
class FlightAlreadyExistsException extends Exception {
    FlightAlreadyExistsException() {
        super("The flight already exists");
    }
}

// Custom exception class for when a flight is not found
class FlightNotFoundException extends Exception {
    FlightNotFoundException() {
        super("The flight is not found");
    }
}

// Custom exception class for when no flights are found
class NoFlightsFoundException extends Exception {
    NoFlightsFoundException() {
        super("There are no flights available");
    }
}

// To create a Flight
class Flight implements Comparable<Flight> {
    // Attributes
    private String origin;
    private String destination;
    private String flightNumber; 
    private String departureTime;
    private String arrivalTime;
    
    // Constructor
    public Flight(String origin, String destination, String flightNumber, String departureTime, String arrivalTime) {
        this.origin = origin;
        this.destination = destination;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }
    
    // Getter methods
    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof Flight) {
            Flight other = (Flight) o;
            // Compare the attributes of two Flight objects for equality
            return this.flightNumber.equals(other.getFlightNumber());
        }
        else
            return false;
    }

    @Override
    // To compare flights
    public int compareTo(Flight o) {
        // To Compare flights based on Departure Time. If their equal then based on Flight number
        int  departureTimeComparison  = this.departureTime.compareTo(o.departureTime);
        if(departureTimeComparison != 0)
            return departureTimeComparison;
        else
            return this.flightNumber.compareTo(o.flightNumber);
    }
    
    @Override
    public String toString() {
        // Return a string representation of the Flight object
        return  "Origin: " + getOrigin() + ", " +
                "Destination: " + getDestination() + ", " +
                "Flight Number: " + getFlightNumber() + ", " +
                "Departure Time: " + getDepartureTime() + ", " +
                "Arrival Time: " + getArrivalTime();
    }
}

// To create the system
public class FlightScheduler {
    // The AVL tree that has the flights data
    private AVLTree<Flight> flightsTree = new AVLTree<>();
    // To initialize an empty flight scheduling system 
    public FlightScheduler() {}
    // To initialize a flight scheduling system using a file
    public FlightScheduler(File f) {
        // To read the file
        try (Scanner read = new Scanner(f)) {
            while (read.hasNext()) {
                // To store data
                String line = read.nextLine();
                String[] flightData = line.split(",");
                String origin = flightData[0].trim();
                String destination = flightData[1].trim();
                String flightNumber = flightData[2].trim();
                String departureTime = flightData[3].trim();
                String arrivalTime = flightData[4].trim();
                Flight flight = new Flight(origin, destination, flightNumber, departureTime, arrivalTime);
                //To add Flight To Data Structures(AVL)
                flightsTree.insertAVL(flight);
            }
        } catch (FileNotFoundException e) {
            System.out.println("This file does not exists");
        }
    }
    // This method adds a new flight to the system 
    public void addFlight(String origin, String destination, String flightNumber, 
    String departureTime, String arrivalTime) throws FlightAlreadyExistsException {
        Flight flight = new Flight(origin, destination, flightNumber, departureTime, arrivalTime);
        // Check if the flight already exists
        if (flightsTree.isInTree(flight)) {
            throw new FlightAlreadyExistsException();
        }
        // Add the flight to the AVL tree
        flightsTree.insertAVL(flight);
    }
    // This method removes a flight based on its flight number
    public void removeFlight(String flightNumber) throws FlightNotFoundException {
        Flight flightToRemove = null;
        // Find the flight with the given flightNumber
        for (Flight flight : flightsTree) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                flightToRemove = flight;
                break;
            }
        }
        // Check if the flight was found
        if (flightToRemove == null) {
            throw new FlightNotFoundException();
        }
        // Remove the flight from the AVL tree
        flightsTree.deleteAVL(flightToRemove);
    }
    // This method searches for flights based on origin and destination cities
    public Flight[] searchFlights(String origin, String destination) {
        SLL<Flight> matchingFlights = new SLL<Flight>();
        // Find the flight with the given origin and destination cities
        for (Flight flight : flightsTree) {
            if (flight.getDestination().equals(destination) && flight.getOrigin().equals(origin)) {
                matchingFlights.addToTail(flight);
            }
        }
        return matchingFlights.toArray(new Flight[matchingFlights.size()]);
    }
    // This method retrieves details of a flight based on its flight number
    public Flight getFlightDetails(String flightNumber) throws FlightNotFoundException {
        Flight flightDetails = null;
        // Find the flight with the given flightNumber
        for (Flight flight : flightsTree) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                flightDetails = flight;
                break;
            }
        }
        // Check if the flight was found
        if (flightDetails == null) {
            throw new FlightNotFoundException();
        }
        return flightDetails; 
    }
    // This method returns a table summary with all the available flights and their information ordered by departure time
    // If two flights have the same time, then order by flight number
    public void getFlightsSummary() throws NoFlightsFoundException {
        if (flightsTree.isEmpty()) {
            throw new NoFlightsFoundException(); // Throw an exception if no flights are found
        }
        System.out.println("Flight Summary:");
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("Departure Time\tArrival Time\tFlight Number\tOrigin\t\tDestination");
        System.out.println("------------------------------------------------------------------------------");
        
        for (Flight flight : flightsTree) {
            // Retrieve flight details
            String departureTime = flight.getDepartureTime();
            String arrivalTime = flight.getArrivalTime();
            String flightNumber = flight.getFlightNumber();
            String origin = flight.getOrigin();
            String destination = flight.getDestination();
            // Print flight summary
            System.out.printf("%-15s %-15s %-15s %-15s %s%n", departureTime, arrivalTime, flightNumber, origin, destination);
        }
        System.out.println("------------------------------------------------------------------------------");
    }
    public void saveFlightSchedule(String fileName) {
        try (PrintWriter write = new PrintWriter(fileName)) {
            // Open a PrintWriter object to write data to the specified file
            // The PrintWriter will automatically close when the try block ends
            for (Flight f : flightsTree) {
                // Iterate over each Flight object in the flightsTree collection
                // Write the Flight object to the file
                write.println(f.getOrigin() + ", " +
                f.getDestination() + ", " +
                f.getFlightNumber() + ", " +
                f.getDepartureTime() + ", " +
                f.getArrivalTime()
                );
            }
        } catch (FileNotFoundException e) {
            // Handle the FileNotFoundException if the specified file does not exist
            System.out.println("This file does not exist");
        }
    }
}