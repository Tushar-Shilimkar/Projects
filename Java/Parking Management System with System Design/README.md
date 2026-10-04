# ParkEngine – Parking Lot Automation System

A console-based, multi-floor parking lot system written in core Java. It handles vehicle entry, spot allocation, ticket generation, billing, payment, and live availability display, and is built to demonstrate object-oriented design and common design patterns.

## Features

- Multi-floor parking lot with Bike, Car, and Truck spots
- Automatic spot allocation (first available spot across floors)
- Ticket generation with unique ticket numbers, entry time, floor, and spot
- Duplicate-parking prevention (the same vehicle number cannot be parked twice)
- Vehicle search by vehicle number
- Hourly billing, rounded up, with a minimum of 1 hour
- Multiple payment methods: Cash, UPI, Card
- Live display boards that update automatically whenever a spot is occupied or released

## Design Patterns Used

| Pattern | Where it is used |
|---|---|
| **Singleton** | `ParkingLot` – a single controller instance for the whole system |
| **Factory** | `VehicleFactory` – creates `Bike`, `Car`, or `Truck` objects |
| **Strategy** | `ParkingStrategy` (spot selection), `PricingStrategy` (charges), `PaymentStrategy` (payment method) |
| **Observer** | `ParkingFloor` (subject) notifies `ParkingDispalyBoard` (observer) on availability changes |

OOP concepts demonstrated: abstraction, inheritance, polymorphism, encapsulation, and composition.

## Class Overview

```
Enums            VehicleType, SpotType, TicketStatus

Vehicles         Vehicle (abstract) -> Bike, Car, Truck
                 VehicleFactory

Spots            ParkingSpot (abstract) -> BikeSpot, CarSpot, TruckSpot

Floors           ParkingFloor, ParkingObserver (interface), ParkingDispalyBoard

Strategies       ParkingStrategy      -> FirstAvialableParkingStrategy
                 PricingStrategy      -> NormalPricingStrategy, WeekendPricingStrategy
                 PaymentStrategy      -> CashPayment, UPIPayment, CardPayment

Transactions     ParkingTicket, EntryGate, ExitGate

Controller       ParkingLot (Singleton), program1017 (main / menu)
```

## How It Works

**Parking a vehicle**

1. Check the vehicle is not already parked
2. Find an available, compatible spot using the active `ParkingStrategy`
3. Identify the floor containing that spot
4. Occupy the spot (display board is notified)
5. `EntryGate` generates a `ParkingTicket`
6. Ticket is stored by ticket number and by vehicle number

**Exiting a vehicle**

1. Look up the active ticket
2. `ExitGate` closes the ticket and calculates the duration
3. Charges are calculated by the active `PricingStrategy`
4. Payment is processed via the chosen `PaymentStrategy`
5. The spot is released (display board is notified) and the records are removed

## Pricing

Duration is rounded up to the next full hour, with a minimum of 1 hour.

| Vehicle | Normal (per hour) | Weekend (per hour) |
|---|---|---|
| Bike | Rs. 20 | Rs. 40 |
| Car | Rs. 50 | Rs. 100 |
| Truck | Rs. 100 | Rs. 200 |

`NormalPricingStrategy` is the default. `WeekendPricingStrategy` is available via `ParkingLot.setPricingStrategy(...)` but is not wired to the menu.

## Default Layout

Both floors are configured in `main` with the same spot layout:

| Floor | Bike spots | Car spots | Truck spots |
|---|---|---|---|
| 1 | 101, 102 | 103, 104 | 105, 106 |
| 2 | 201, 202 | 203, 204 | 205, 206 |

## Requirements

- Java 8 or later (JDK)

## Build and Run

```bash
javac ParkEngine.java
java ParkEngine
```

The entry point is the `ParkEngine` class, so run that class name rather than `ParkEngine`.

## Usage

```
---------- ParkEngine -----------
1 : Park Vechile
2 : Exit Vechile
3 : Search Vechile
4 : Display Parking Lot
5 : Exit
```

- **Park Vehicle** – choose a type (1 Bike, 2 Car, 3 Truck), enter the vehicle number, and a ticket is printed.
- **Exit Vehicle** – enter the ticket number and choose a payment method (1 Cash, 2 UPI, 3 Card).
- **Search Vehicle** – enter a vehicle number to see its active ticket.
- **Display Parking Lot** – shows every spot on every floor and its occupancy.

## Extending the System

- **New spot-selection logic** – implement `ParkingStrategy` (e.g. nearest-to-exit) and call `setParkingStrategy(...)`.
- **New pricing rules** – implement `PricingStrategy` (e.g. peak-hour pricing) and call `setPricingStrategy(...)`.
- **New payment method** – implement `PaymentStrategy`.
- **New observers** – implement `ParkingObserver` (e.g. a website or mobile notifier) and register it with `ParkingFloor.addObserver(...)`.
- **New vehicle type** – add to `VehicleType` and `SpotType`, create the `Vehicle` and `ParkingSpot` subclasses, and extend `VehicleFactory`.

## Known Limitations

- State is in memory only; all data is lost when the program exits.
- Not thread-safe beyond the singleton accessor; designed for a single console user.
- Menu input is not validated for non-numeric entries.
- Only one entry gate and one exit gate are created in `main`.
- Typos in some identifiers (e.g. `creatVehicle`, `ParkingDispalyBoard`, `findAvailabSpot`) are kept as-is in the source.
