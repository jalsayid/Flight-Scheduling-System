import java.io.File;
import java.util.Scanner;

public class FlightSchedulingTester {
    
    // Display the menu options
    public static void menu() {
        System.out.println("\nFlight Schedule Menu:");
        System.out.println("1. Load the flight schedule from a file");
        System.out.println("2. Add a flight to the schedule");
        System.out.println("3. Remove a flight from the schedule");
        System.out.println("4. Search for flights");
        System.out.println("5. Get flight details");
        System.out.println("6. Save the updated flight schedule as a text file");
        System.out.println("7. Print the flight summary");
        System.out.println("8. Exit");
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FlightScheduler flightScheduler = new FlightScheduler();
        int choice;
        boolean exit = false;
        
        // Main loop for user interaction
        while (!exit) {
            menu(); // Display the menu
            
            // Get user choice
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            
            try {
                switch (choice) {
                    case 1:
                        // Load flight schedule from a file
                        System.out.print("Enter filename: ");
                        String filename = scanner.next();
                        File file = new File(filename);
                        flightScheduler = new FlightScheduler(file);
                        System.out.println("Flight schedule loaded successfully.");
                        flightScheduler.getFlightsSummary();
                        break;
                    case 2:
                        // Add a flight to the schedule
                        System.out.print("Add flight: ");
                        String[] flightData1 = scanner.nextLine().split(", ");
                        flightScheduler.addFlight(flightData1[0], flightData1[1], flightData1[2], flightData1[3], flightData1[4]);
                        System.out.println("Flight added successfully.");
                        flightScheduler.getFlightsSummary();
                        break;
                    case 3:
                        // Remove a flight from the schedule
                        System.out.print("Remove flight: ");
                        flightScheduler.removeFlight(scanner.next());
                        System.out.println("Flight removed successfully.");
                        flightScheduler.getFlightsSummary();
                        break;
                    case 4:
                        // Search for flights
                        System.out.print("Search flights: ");
                        String[] flightData2 = scanner.nextLine().split(", ");
                        Flight[] flights = flightScheduler.searchFlights(flightData2[0], flightData2[1]);
                        System.out.print("Flights: ");
                        for(int i = 0; i < flights.length - 1; i++)
                            System.out.print(flights[i].getFlightNumber() + ", ");
                        System.out.println(flights[flights.length - 1].getFlightNumber());
                        flightScheduler.getFlightsSummary();
                        break;
                    case 5:
                        // Get details of a specific flight
                        System.out.print("Flight details: ");
                        Flight flight = flightScheduler.getFlightDetails(scanner.next());
                        System.out.println(flight);
                        flightScheduler.getFlightsSummary();
                        break;
                    case 6:
                        // Save the updated flight schedule to a file
                        System.out.print("Save Updated Schedule (Y/N): ");
                        if(scanner.next().equals("Y")) {
                            System.out.print("Enter filename: ");
                            flightScheduler.saveFlightSchedule(scanner.next());
                            System.out.println("Flight schedule saved successfully.");
                        }
                        else {
                            System.out.println("Flight schedule is not saved.");
                        }
                        break;
                    case 7:
                        // Print the flight summary
                        flightScheduler.getFlightsSummary();
                        break;
                    case 8:
                        // Exit the program
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                        break;
                }
            } catch(FlightAlreadyExistsException e) {
                System.out.println(e.getMessage());
            } catch(FlightNotFoundException e) {
                System.out.println(e.getMessage());
            } catch(NoFlightsFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        
        // Close the scanner
        scanner.close();
    }
}
