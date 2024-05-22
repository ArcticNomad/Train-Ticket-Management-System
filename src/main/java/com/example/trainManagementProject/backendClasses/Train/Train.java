package com.example.trainManagementProject.backendClasses.Train;

import com.example.trainManagementProject.backendClasses.Passenger.Passenger;
import com.example.trainManagementProject.backendClasses.Route.*;

import java.util.Scanner;

public class Train
{
  private int trainNUmber;
  private int trainSpeed;
  private int capacity;
  private int remainingSeats;
  private BusinessClass businessClass;
  private EconomyClass economyClass;

  private Route trainRoute;

  private Passenger passenger;


  Scanner input=new Scanner(System.in);

  //Schedule trainSchedule;

  public Train(int trainNUmber, int trainSpeed, int capacity,BusinessClass businessClass,EconomyClass economyClass)
  {
    setCapacity(capacity);
    setTrainSpeed(trainSpeed);
    setTrainNUmber(trainNUmber);
    setClass(businessClass,economyClass);
  }

  public Train(){}

  public Train(int trainNumb) {
    setTrainNUmber(trainNumb);
  }


  public int getTrainNUmber() {
    return trainNUmber;
  }

  public void setTrainNUmber(int trainNUmber) {
    this.trainNUmber = trainNUmber;
  }

  public int getTrainSpeed() {
    return trainSpeed;
  }

  public void setTrainSpeed(int trainSpeed) {
    this.trainSpeed = trainSpeed;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }


  public BusinessClass getBusinessClass() {
    return businessClass;
  }

  public void setBusinessClass(BusinessClass businessClass) {
    this.businessClass = businessClass;
  }

  public EconomyClass getEconomyClass() {
    return economyClass;
  }

  public void setEconomyClass(EconomyClass economyClass) {
    this.economyClass = economyClass;
  }

  public Route getTrainRoute() {
    return trainRoute;
  }

  public void setTrainRoute(Route trainRoute) {
    this.trainRoute = trainRoute;
  }

  public Passenger getPassenger() {
    return passenger;
  }

  public void setPassenger(Passenger passenger) {
    this.passenger = passenger;
  }

  public void setClass(BusinessClass businessClass, EconomyClass economyClass)
  {
    if(businessClass.getBusinessCapacity()+economyClass.getEconomyCapacity()<=capacity)
    {
      this.businessClass = businessClass;
      this.economyClass = economyClass;
    }
    else
    {
      System.out.println("Error: Train Capacity Exceeded!");
    }
  }

  public int getRemainingSeats() {
    return remainingSeats;
  }

  public void setRemainingSeats(int remainingSeats) {
    this.remainingSeats = remainingSeats;
  }
}