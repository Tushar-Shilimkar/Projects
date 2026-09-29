/*
    ParkingLot Automation System

    Step 1 : Create requred enums
    Step 2 : Vehicle Hierarchy Creation
    Step 3 : VehicleFactory creation (Factory Pattern)
    Step 4 : ParkingSpot Hirrarchy
    Step 5 : ParkingObserve Class
    Step 6 : ParkingFloor class
    Step 7 : ParkingDisplayBoard (Observer Pattern)
    Step 8 : ParkingStrategy Class (Strategy Pattern)
    Step 9 : PricingStrategy Class (Strategy Pattern)
    Step 10 : PaymentStrategy Class
    Step 11 : ParkingTicket Class
    Step 12 : EntryGate Class
    Step 13 : ExitGate Class
    Step 14 : ParkingLot Class (Singleton Pattern)
    Step 15 : Main Class (Controller)
*/

import java.util.*;
//import java.time.Duration;
//import java.time.LocalDateTime;

////////////////////////////////////////////////////////////////////////////////
//  Step 1 : Create Enum
//  It is used to create fixed constants which are required through the project
////////////////////////////////////////////////////////////////////////////////

//  Repreaents the different types of vehicle supported by the project
enum VehicleType
{
    BIKE,
    CAR,
    TRUCK
}

//  Different type of parking spots
enum SpotType
{
    BIKE,
    CAR,
    TRUCK
}

//  Represent the Different state of parking ticket
enum TicketStatus
{
    ACTIVE,
    CLOSED
}

////////////////////////////////////////////////////////////////////////////////
//  Step 2 : Create Vehicle Class Hierarchy
//  It is used to create multiple types of classes which represents 
//  the types of vehicle
//  Concepts : Abstraction, Inheritance, Polymorphism, Encapsulation
////////////////////////////////////////////////////////////////////////////////

// Class which represents a generic vehicle type
abstract class Vehicle
{
    // Abstraction (Hidden) characteristics of class

    private String vehicleNumber;

    private VehicleType vehicleType;

    public Vehicle(String vehicleNumber, VehicleType vehicleType)
    {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
    }

    //  Conctere getter method
    public VehicleType getVehicleType()
    {
        return this.vehicleType;
    }

    //  Conctere getter method
    public String getVehicleNumber()
    {
        return this.vehicleNumber;
    }

    // Every concrete class will provide its own defination
    public abstract void display();
}

// Class which represents the Vehicle type as Bike
class Bike extends Vehicle
{
    // Parameterise Constructor
    public Bike(String vehicleNumber)
    {
        // Calls vehicle class constructor
        super(vehicleNumber, VehicleType.BIKE);
    }

    // Methiod overriding
    @Override
    public void display()
    {
        System.out.println("Bike : "+getVehicleNumber());
    }
}

// Class which represents the Vehicle type as Car
class Car extends Vehicle
{
    // Parameterise Constructor
    public Car(String vehicleNumber)
    {
        // Calls vehicle class constructor
        super(vehicleNumber, VehicleType.CAR);
    }

    // Methiod overriding
    @Override
    public void display()
    {
        System.out.println("Car : "+getVehicleNumber());
    }
}

// Class which represents the Vehicle type as Truck
class Truck extends Vehicle
{
    // Parameterise Constructor
    public Truck(String vehicleNumber)
    {
        // Calls vehicle class constructor
        super(vehicleNumber, VehicleType.TRUCK);
    }

    // Methiod overriding
    @Override
    public void display()
    {
        System.out.println("Truck : "+getVehicleNumber());
    }
}

////////////////////////////////////////////////////////////////////////////////
//  Step 3 : Create VehicleFactory Class
//  It is used to centralised the creation of vehicle objects
//  Concepts : Factory Design Pattern
////////////////////////////////////////////////////////////////////////////////

class VehicleFactory
{
    // Creates and return the desired class object
    
    public static Vehicle creatVehicle(VehicleType type, String number)
    {
        switch(type)
        {
            case BIKE:
                return new Bike(number);

            case CAR:
                return new Car(number);

            case TRUCK:
                return new Truck(number);

            default:
                throw new IllegalArgumentException("Invalid Vehicle type");
        }
    }
}

////////////////////////////////////////////////////////////////////////////////
//  Step 4 : Create ParkingSpot Hierarchy
//  It is used to create hierarchy of Parking Spot
//  Concepts : Encapsulation, Abstraction, Inheritance, Polymorphism
////////////////////////////////////////////////////////////////////////////////

abstract class ParkingSpot
{
    // Unique number for parking spot (Primary key)
    private int spotNumber;

    // Type of parking spot
    private SpotType spotType;

    //  Indicates whether spot is currently occupied of not
    private boolean occupied;

    // Stores the information about the Vehicle
    private Vehicle vehicle;

    // Parametrised constructor
    public ParkingSpot(int spotNhmber, SpotType spotType)
    {
        this.spotNumber = spotNhmber;
        this.spotType = spotType;

        //  Initialised with default values
        this.occupied = false;
        this.vehicle = null;
    }

    public int getSpotNumber()
    {
        return this.spotNumber;
    }

    public SpotType getSpotType() 
    {
        return this.spotType;
    }

    public boolean isOccupied()
    {
        return this.occupied;
    }

    public Vehicle getVehicle()
    {
        return this.vehicle;
    }

    // It is use to park the Vehicle
    public void parkVehicle(Vehicle vehicle)
    {
        if(this.occupied == true)
        {
            throw new RuntimeException("Parking Spot is already occupied");

        }
        else
        {
            this.vehicle = vehicle;
            this.occupied = true;
        }
    }

    public Vehicle removeVehicle()
    {
        if(this.occupied == true)
        {
            Vehicle temp = vehicle;

            this.vehicle = null;
            this.occupied = false;

            return temp;
        }
        else
        {
            throw new RuntimeException("Parking spot is already empty");
        }
    }

    // This method Decides whether we can park it in the spot or not
    public abstract boolean canFitVehicle(Vehicle vehicle);

    public void display()
    {
        System.out.println("Spot : "+spotNumber+"["+this.spotType+"]");

        if (this.occupied == true)
        {
            System.out.println("Occupied by : "+vehicle.getVehicleNumber());
        }
        else
        {
            System.out.println("Spot is available");
        }
    }
}// End of ParkingSpot Class

class BikeSpot extends ParkingSpot
{
    public BikeSpot(int spotNumber)
    {
        super(spotNumber, SpotType.BIKE);
    }

    @Override
    public boolean canFitVehicle(Vehicle vehicle)
    {
        if(vehicle.getVehicleType() == VehicleType.BIKE)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

class CarSpot extends ParkingSpot
{
    public CarSpot(int spotNumber)
    {
        super(spotNumber, SpotType.CAR);
    }

    @Override
    public boolean canFitVehicle(Vehicle vehicle)
    {
        if(vehicle.getVehicleType() == VehicleType.CAR)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

class TruckSpot extends ParkingSpot
{
    public TruckSpot(int spotNumber)
    {
        super(spotNumber, SpotType.TRUCK);
    }

    @Override
    public boolean canFitVehicle(Vehicle vehicle)
    {
        if(vehicle.getVehicleType() == VehicleType.TRUCK)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

////////////////////////////////////////////////////////////////////////////////
//  Step 5 : Create ParkingObserve Class
//  It is used to Automatically update Display board when 
//  the parking Availability changes
//  Concepts : Observer
////////////////////////////////////////////////////////////////////////////////

interface ParkingObserver
{
    void update();

}

////////////////////////////////////////////////////////////////////////////////
//  Step 6 : Create ParkingFloor Class
//  It is used to manage parking floor 
//  Concepts : Composition, ArrayList, Object Management
////////////////////////////////////////////////////////////////////////////////

class ParkingFloor
{
    // Unique floor number 
    private int floorNumber;

    // Collection of all parking spot
    private List<ParkingSpot> parkingSpots;

    // Collection of observers registered for the floor
    private List<ParkingObserver> observers;

    public ParkingFloor(int floorNumber)
    {
        this.floorNumber = floorNumber;

        this.parkingSpots = new ArrayList<>();

        this.observers = new ArrayList<>();
    }

    public int getFloorNumber()
    {
        return this.floorNumber;
    }

    public void addParkingSpot(ParkingSpot spot)
    {
        parkingSpots.add(spot);
    }

    public void addObserver(ParkingObserver observer)
    {
        observers.add(observer);
    }

    private void notifyObservers()
    {
        for(ParkingObserver observer : observers)
        {
            observer.update();
        }
    }

    //  Method is going to search parking spot for specific type of vehicle
    public ParkingSpot findAvailableSpot(Vehicle vehicle)
    {
        for(ParkingSpot spot : parkingSpots)
        {
            if(!spot.isOccupied() && spot.canFitVehicle(vehicle))
            {
                return spot;
            }
        }
        return null;
    }
    
    //  called when new vehicle gets parked
    public void occupySpot(ParkingSpot spot, Vehicle vehicle)
    {
        // allocate spot for the vehicle
        spot.parkVehicle(vehicle);

        //  Notify all observers about the avaibilaty of spots
        notifyObservers();
    }

    public void reseaseSpot(ParkingSpot spot)
    {
        // relese the already allocated spot
        spot.removeVehicle();

        //  Notify all observers about the avaibilaty of spots
        notifyObservers();
    }

    public int getAvailableCount(SpotType type)
    {
        int count = 0;

        for(ParkingSpot spot : parkingSpots)
        {
            if(spot.getSpotType() == type && !spot.isOccupied())
            {
                count++;
            }
        }

        return count;
    }

    //  Display all parking spots on all specific floor 
    public void displayFloor()
    {
        System.out.println();

        System.out.println("Floor : "+floorNumber);

        for(ParkingSpot spot : parkingSpots)
        {
            spot.display();
        }
    }
}

////////////////////////////////////////////////////////////////////////////////
//  Step 7 : Create a parking Display Board class 
//  It is used to create a class which displays the parking Status
//  Subjects -> ParkingFloor
//  Observer -> ParkingDisplayBoard

//  Note : Any observer is going to observe the subject
//  There will be multiple observers for one subject

//  Concepts : Observer Design Patterns
////////////////////////////////////////////////////////////////////////////////

class ParkingDisplayBoard implements ParkingObserver
{
    //  Floor whose avaibility is displayed by this board
    private ParkingFloor floor;

    //  Constructor
    public ParkingDisplayBoard(ParkingFloor floor)
    {
        this.floor = floor;
    }

    //  Automatically called whenever floor availablity changes
    @Override
    public void update()
    {
        System.out.println();
        System.out.println("------------ Display Board ----------");
        
        System.out.println("Floor : "+floor.getFloorNumber());

        System.out.println("Available Bike Spots : "+floor.getAvailableCount(SpotType.BIKE));

        System.out.println("Available Car Spots : "+floor.getAvailableCount(SpotType.CAR));

        System.out.println("Available Truck Spots : "+floor.getAvailableCount(SpotType.TRUCK));

        System.out.println("-------------------------------------");
        System.out.println();
    }
}

//  We can create new observers for the same subject
/*
    class ParkingWebsites impliments ParkingObserver
    {
        public void update()
        {
        
        }
    }
*/

////////////////////////////////////////////////////////////////////////////////
//  Step 8 : Create a ParkingStrategy classs

//  It is used to create a class ParkingStrategy which is responcible to
//  decide the parking spot selection

//  Concepts : Strategy Design Patterns
////////////////////////////////////////////////////////////////////////////////


//  Defines a common concepts for parking spot selection algorithm
interface ParkingStrategy
{
    ParkingSpot findSpot(List<ParkingFloor> floors, Vehicle vehicle);
}

//  Selects the first available parking spot
class FirstAvailableParkingStrategy implements ParkingStrategy
{
    @Override
    public ParkingSpot findSpot(List<ParkingFloor> floors, Vehicle vehicle)
    {
        //  Iterate over all available floors
        for(ParkingFloor floor : floors)
        {
            ParkingSpot spot = floor.findAvailableSpot(vehicle);

            if(spot != null)
            {
                return spot;
            }
        }

        return null;
    }
}
/*
    class NearestFirstAvailableParkingStrategy implements ParkingStrategy
    {

    }
*/

////////////////////////////////////////////////////////////////////////////////
//  Step 9 : Create a PricingStrategy classs

//  It is used to create a class PricingStrategy 
//  It keeps the pricing algorithms independent of exit logic

//  Concepts : Strategy Design Patterns
////////////////////////////////////////////////////////////////////////////////

interface PricingStrategy
{
    double calculatePrice(Vehicle vehicle, long hours);
}

class NormalPricingStrategy implements PricingStrategy
{
    @Override
    public double calculatePrice(Vehicle vehicle, long hours)
    {
        if(hours <= 0)
        {
            hours = 1;
        }

        switch(vehicle.getVehicleType())
        {
            case BIKE:
                return hours * 20;

            case CAR:
                return hours * 50;

            case TRUCK:
                return hours * 100;

            default:
                return 0;
        }
    }
}

class WeekendPricingStrategy implements PricingStrategy
{
    @Override
    public double calculatePrice(Vehicle vehicle, long hours)
    {
        if(hours <= 0)
        {
            hours = 1;
        }

        switch(vehicle.getVehicleType())
        {
            case BIKE:
                return hours * 40;

            case CAR:
                return hours * 100;

            case TRUCK:
                return hours * 200;

            default:
                return 0;
        }
    }
}

public class program1006
{
    public static void main(String A[])
    {

    }
}
