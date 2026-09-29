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
import java.time.Duration;
import java.time.LocalDateTime          //

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


public class program996
{
    public static void main(String A[])
    {

    }
}
