package com.example.trainManagementProject;

import com.example.trainManagementProject.backendClasses.StationManagement.StationManagement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class paymentPageController implements Initializable
{
    @FXML
    private Button payButton;
    @FXML
    private Button homeButton;
    @FXML
    private TextField id;
    @FXML
    private TextField payment;
    @FXML
    private TextField amountDue;
    @FXML
    private TextField change;
    @FXML
    private Button backButton;

    public void onPayButton()
    {
        if(id.getText().isEmpty()|| payment.getText().isEmpty())
        {
            payButton.setText("Empty Field!");
        }

        int ID= Integer.parseInt(id.getText());
        Double PAYMENT= Double.valueOf(payment.getText());
        Double AmountDue= Double.valueOf(amountDue.getText());

        Boolean passengerFound=false;

        if(PAYMENT<AmountDue)
        {
            payButton.setText("Insufficient Funds !");
            payButton.setDisable(true);
        }

        if (PAYMENT>=AmountDue)
        {
            for (int i = 0; i < StationManagement.getPassengers().size(); i++) {

                if(ID == StationManagement.getPassengers().get(i).getPassengerID())
                {
                    StationManagement.getPassengers().get(i).setPassengerTicket(StationManagement.getPassengerTicket());
                    passengerFound=true;
                    payButton.setText("Ticket Purchased!");
                    double calculatedChange=StationManagement.getPassengerTicket().calculateChange(PAYMENT,AmountDue);
                    String cc= String.valueOf(calculatedChange);
                    change.setText(cc);
                }
            }
            if(!passengerFound)
            {
                payButton.setText("Invalid ID!");
            }

        }

    }
    public void resetPayButton()
    {
        payButton.setText("Pay");
        payButton.setDisable(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        amountDue.setEditable(false);
        change.setEditable(false);
        String fare= String.valueOf(StationManagement.getPassengerTicket().calculateFare());
        amountDue.setText(fare);
    }
    public void onBackButton() throws IOException {
        Stage stage=(Stage) backButton.getScene().getWindow();

        FXMLLoader fxmlLoader=new FXMLLoader(HelloApplication.class.getResource("buyTicket.FXML"));

        Scene scene= new Scene(fxmlLoader.load());

        stage.setScene(scene);

        stage.show();
    }

    public void onHomeButton() throws IOException {
        Stage stage=(Stage) backButton.getScene().getWindow();

        FXMLLoader fxmlLoader=new FXMLLoader(HelloApplication.class.getResource("buyTicket.FXML"));

        Scene scene= new Scene(fxmlLoader.load());

        stage.setScene(scene);

        stage.show();
    }
}