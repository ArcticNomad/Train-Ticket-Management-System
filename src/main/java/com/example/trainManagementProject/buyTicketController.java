package com.example.trainManagementProject;

import com.example.trainManagementProject.backendClasses.Passenger.Passenger;
import com.example.trainManagementProject.backendClasses.Route.Route;
import com.example.trainManagementProject.backendClasses.Station.Station;
import com.example.trainManagementProject.backendClasses.StationManagement.StationManagement;
import com.example.trainManagementProject.backendClasses.Ticket.Ticket;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class buyTicketController implements Initializable
{

    @FXML
    private Button backButton;
    @FXML
    private Button refreshButton;
    @FXML
    private ChoiceBox<String> departStationName;
    @FXML
    private ChoiceBox<String> destinationStationName;
    @FXML
    private TextField distance;
    @FXML
    private TextField seatNo;
    @FXML
    private TextArea stationList;
    @FXML
    private TextArea seatList;
    @FXML
    private Button payButton;
    @FXML
    private Button businessClassButton;
    @FXML
    private Button economyClassButton;

    public static Boolean businessButtonPressed=false;
    public static Boolean economyButtonPressed=false;

    public void onPaymentButton() throws IOException {

        //System.out.println("Departure Station Name: " + departStationName.getText());
        //System.out.println("Destination Station Name: " + destinationStationName.getText());
        System.out.println("Seat No: " + seatNo.getText());
        System.out.println("Business Class Button Pressed: " + businessButtonPressed);
        System.out.println("Economy Class Button Pressed: " + economyButtonPressed);

        if (departStationName.getValue()==null || destinationStationName.getValue()==null || seatNo.getText().isEmpty()) {
            payButton.setText("Empty Field!");
            return;
        }

        if (!businessButtonPressed && !economyButtonPressed)
        {
            payButton.setText("Select Class!");
            return;
        }


        Boolean trainFound = false;

        try {
            for (int i = 0; i < StationManagement.getTrains().size(); i++)
            {
                if (StationManagement.getTrains().get(i).getTrainRoute().getDepartureStation().getStationName().equals(departStationName.getValue()) && StationManagement.getTrains().get(i).getTrainRoute().getArrivalStation().getStationName().equals(destinationStationName.getValue()))
                {
                    StationManagement.getPassengerTicket().setTicketTrain(StationManagement.getTrains().get(i));

                    int seat = Integer.parseInt(seatNo.getText());

                    if (seat<=0 || seat>StationManagement.getTrains().get(i).getCapacity())
                    {
                        payButton.setText("Invalid Sead Input!");
                        return;
                    }

                    if(StationManagement.checkSeatNo(seat))
                    {
                        payButton.setText("Seat Taken!");
                        return;
                    }


                    StationManagement.getPassengerTicket().setPassengerSeat(seat);

                    trainFound = true;

                    if (economyButtonPressed)
                    {
                        StationManagement.getPassengerTicket().setBusinessClass(null);
                        StationManagement.getPassengerTicket().setEconomyClass(MainAdminPageController.economyClass);
                    }
                    if (businessButtonPressed) {
                        StationManagement.getPassengerTicket().setBusinessClass(MainAdminPageController.businessClass);
                        StationManagement.getPassengerTicket().setEconomyClass(null);
                    }

                    break;
                }
            }

            if (!trainFound)
            {
                payButton.setText("Train Not Found !");
            }
            else
            {
                Stage stage = (Stage) payButton.getScene().getWindow();

                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("paymentPage.FXML"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();
            }
        } catch (NumberFormatException e) {
            payButton.setText("Invalid Input!");
        }
    }

    public void onStationRefreshButton()
    {
        stationList.clear();

        for (int i = 0; i < StationManagement.getTrains().size(); i++) {
            stationList.appendText((i + 1) + " - " +" From - "+ StationManagement.getTrains().get(i).getTrainRoute().getDepartureStation().getStationName() + "    To -   "+StationManagement.getTrains().get(i).getTrainRoute().getArrivalStation().getStationName()+"\n");
        }
    }
    public void onSeatRefreshButton()
    {

        seatList.clear();

        for (int i = 0; i <StationManagement.getTrains().size(); i++)
        {
            if(StationManagement.getTrains().get(i).getTrainRoute().getDepartureStation().getStationName().equals(departStationName.getValue()) && StationManagement.getTrains().get(i).getTrainRoute().getArrivalStation().getStationName().equals(destinationStationName.getValue()))
            {
                for (int j = 0; j < StationManagement.getTrains().get(i).getCapacity(); j++)
                {
                    seatList.appendText((j + 1) + "\n");
                }
                break;
            }
            else {
                refreshButton.setText("Error!");
            }

        }

    }

    public void onBusinessClassButton()
    {
        System.out.println("Pressed");
        businessButtonPressed=true;
        economyButtonPressed=false;
        StationManagement.getPassengerTicket().setBusinessClass(MainAdminPageController.businessClass);
        StationManagement.getPassengerTicket().setEconomyClass(null);
    }
    public void onEconomyClassButton()
    {
        System.out.println("Pressed");
        economyButtonPressed=true;
        businessButtonPressed=false;
        StationManagement.getPassengerTicket().setEconomyClass(MainAdminPageController.economyClass);
        StationManagement.getPassengerTicket().setBusinessClass(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String departNames="";
        String arrivalNames="";
        for (int i = 0; i < StationManagement.getStations().size(); i++)
        {

            departNames=StationManagement.getStations().get(i).getStationName();
            departStationName.getItems().addAll(departNames);
            arrivalNames=StationManagement.getStations().get(i).getStationName();
            destinationStationName.getItems().addAll(arrivalNames);
        }

        seatList.setEditable(false);
        stationList.setEditable(false);
    }
    public void resetRefreshButton()
    {
        refreshButton.setText("Refresh");
    }
    public void onBackButton() throws IOException {
        Stage stage=(Stage) backButton.getScene().getWindow();

        FXMLLoader fxmlLoader=new FXMLLoader(HelloApplication.class.getResource("mainPassengerPage.FXML"));

        Scene scene= new Scene(fxmlLoader.load());

        stage.setScene(scene);

        stage.show();
    }
    public void resetPayButton()
    {
        payButton.setText("Pay For Ticket");
    }

    public static Boolean getBusinessButtonPressed() {
        return businessButtonPressed;
    }

    public static void setBusinessButtonPressed(Boolean businessButtonPressed) {
        buyTicketController.businessButtonPressed = businessButtonPressed;
    }

    public static Boolean getEconomyButtonPressed() {
        return economyButtonPressed;
    }

    public static void setEconomyButtonPressed(Boolean economyButtonPressed) {
        buyTicketController.economyButtonPressed = economyButtonPressed;
    }
}
