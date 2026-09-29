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

public class program997
{
    public static void main(String A[])
    {

    }
}
