package Warehouse.app.Menu;

import Warehouse.app.Exception.TooManyThingsException;
import Warehouse.app.View.ServiceRepairView;
import Warehouse.app.View.StopRepairView;
import Warehouse.app.Main;
import Warehouse.app.Thread.LeaseCheckingThread;
import Warehouse.app.Thread.TimeLapseThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

public class MainMenu<T> extends JFrame {
    public static JTextArea jTextArea;
    public static JTextField jTextField;
    public static JScrollPane scroll;
    public static boolean choosePerson=false;
    public static boolean personIsChosen =false;
    public static boolean chooseFreeWarehouse =false;
    public static boolean chooseWarehouse =false;
    public static boolean chooseParking=false;
    public static boolean freeWarehouses =false;
    public static boolean allWarehouses =false;
    public static boolean addPermission =false;
    public static boolean permissionIsAdded =false;
    public static boolean addItem =false;
    public static boolean warehouseToItem =false;
    public static boolean parkingChosen =false;
    public static boolean carToPark =false;
    public static boolean carChosen =false;
    public static boolean carService =false;
    public static boolean carToRemove =false;
    public static boolean makeCarService=false;
    public static boolean warehouseChosen=false;
    public static boolean needService=false;
    public static boolean itemIsChosen=false;
    public static boolean serviceSpotChosen=false;
    public static boolean repairPlace=false;
    public static boolean finishICSS=false;
    public static boolean finishCSS=false;
    public static JFrame frame;
    public static JButton buttonOK;
    public static List<JButton> jButtonList = new LinkedList<>();

    public static List<JButton> getjButtonList() {
        return jButtonList;
    }

    public MainMenu() {
        frame = new JFrame("Warhouse.app.Warehouse.Warhouse.app.Warehouse.app");
        frame.setContentPane(new JLabel(new ImageIcon("images\\photo1.jpg")));
        ImageIcon img = new ImageIcon("images\\photo2.png");
        frame.setIconImage(img.getImage());
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setSize(1380,890);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JButton choosePersonButton = createButton("Wybierz osobe");
        JButton showInfoButton = createButton("Wyświetl informacje");
        JButton freeWarehouseButton = createButton("Wyświetl wolne pomieszczenia");
        JButton rentWarehouseButton = createButton("Wynajmij pomieszczenie");
        JButton checkContentButton = createButton("Sprawdz zawartosc pomieszczenia");
        JButton addPermissionButton = createButton("Dodaj uprawnienia do magazynu");
        JButton addItemButton = createButton("Wloz przedmiot do magazynu");
        JButton parkTheCarButton = createButton("Zaparkuj pojazd na miejscu parkingowym");
        JButton removeItemButton = createButton("Zabierz przedmiot z magazynu");
        JButton takeOutTheCarButton = createButton("Zabierz auto z parkingu");
        JButton rentCarServiceSpotButton = createButton("Wynajmij miejsce serwisowe lub naprawcze");
        JButton needServiceButton = createButton("Zglos potrzebe naprawy lub rozpocznij serwisowanie");
        JButton stopServiceButton = createButton("Zakoncz naprawe pojazdu");
        JButton writeToFileButton = createButton("Zapisz aktualny stan");
        JButton startThreadButton = createButton("Uruchom upływ czasu");
        JButton exitButton = createButton("Wyjście z programu");

        jTextArea = new JTextArea();
        jTextArea.setBounds(415,100,550,550);
        jTextArea.setText("Wybierz opcje z MENU aby rozpoczac...");
        jTextArea.setEditable(false);
        Font font = new Font("Franklin Gothic Heavy", Font.ITALIC, 20);
        jTextArea.setFont(font);
        jTextArea.setForeground(Color.BLACK);
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        scroll = new JScrollPane(jTextArea);
        scroll.setBounds(415,100,550,550);
        frame.add(scroll);

        jTextField = new JTextField();
        jTextField.setBounds(415,650,450,50);
        frame.add(jTextField);
        buttonOK = new JButton("OK");
        buttonOK.setBounds(865,650,100,50);
        buttonOK.setFocusPainted(false);
        frame.add(buttonOK);
        jTextField.setEnabled(false);
        buttonOK.setEnabled(false);

        ButtonOptions choosePersonButtonOptions = new ButtonOptions(choosePersonButton,frame);
        choosePersonButtonOptions.setSize(choosePersonButton,25,100,350,60);

        ButtonOptions showInfoButtonOptions = new ButtonOptions(showInfoButton,frame);
        showInfoButtonOptions.setSize(showInfoButton,25,180,350,60);

        ButtonOptions freeWarehouseButtonOptions = new ButtonOptions(freeWarehouseButton,frame);
        freeWarehouseButtonOptions.setSize(freeWarehouseButton,25,260,350,60);

        ButtonOptions rentWarehouseButtonOptions = new ButtonOptions(rentWarehouseButton,frame);
        rentWarehouseButtonOptions.setSize(rentWarehouseButton,25,340,350,60);

        ButtonOptions checkContentButtonOptions = new ButtonOptions(checkContentButton,frame);
        checkContentButtonOptions.setSize(checkContentButton,25,420,350,60);

        ButtonOptions addPermissionButtonOptions = new ButtonOptions(addPermissionButton,frame);
        addPermissionButtonOptions.setSize(addPermissionButton,25,500,350,60);

        ButtonOptions addItemButtonOptions = new ButtonOptions(addItemButton,frame);
        addItemButtonOptions.setSize(addItemButton,25,580,350,60);

        ButtonOptions parkTheCarButtonOptions = new ButtonOptions(parkTheCarButton,frame);
        parkTheCarButtonOptions.setSize(parkTheCarButton,25,660,350,60);

        ButtonOptions removeItemButtonOptions = new ButtonOptions(removeItemButton,frame);
        removeItemButtonOptions.setSize(removeItemButton,1000,100,350,60);

        ButtonOptions takeOutTheCarButtonOptions = new ButtonOptions(takeOutTheCarButton,frame);
        takeOutTheCarButtonOptions.setSize(takeOutTheCarButton,1000,180,350,60);

        ButtonOptions rentCarServiceSpotButtonOptions = new ButtonOptions(rentCarServiceSpotButton,frame);
        rentCarServiceSpotButtonOptions.setSize(rentCarServiceSpotButton,1000,260,350,60);

        ButtonOptions needServiceButtonOptions = new ButtonOptions(needServiceButton,frame);
        needServiceButtonOptions.setSize(needServiceButton,1000,340,350,60);

        ButtonOptions stopServiceButtonOptions = new ButtonOptions(stopServiceButton,frame);
        stopServiceButtonOptions.setSize(stopServiceButton,1000,420,350,60);

        ButtonOptions writeToFileButtonOptions = new ButtonOptions(writeToFileButton,frame);
        writeToFileButtonOptions.setSize(writeToFileButton,1000,500,350,60);

        ButtonOptions startThreadButtonOptions = new ButtonOptions(startThreadButton,frame);
        startThreadButtonOptions.setSize(startThreadButton,1000,580,350,60);

        ButtonOptions exitButtonOptions = new ButtonOptions(exitButton,frame);
        exitButtonOptions.setSize(exitButton,1000,660,350,60);

        //Actions
        choosePersonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
                choosePerson=true;
                jTextField.setEnabled(true);
                buttonOK.setEnabled(true);
                Main.displayPeople();
            }});

        showInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
                jTextField.setEnabled(false);
                buttonOK.setEnabled(false);
                Main.displayPersonDetails2();
            }});

        freeWarehouseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
                jTextField.setEnabled(false);
                buttonOK.setEnabled(false);
                Main.displayFreeWarehouse();
            }});

        rentWarehouseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
                choosePerson=false;
                if(personIsChosen) {
                    jTextField.setEnabled(true);
                    buttonOK.setEnabled(true);
                    jTextField.setText("");
                }
                    freeWarehouses =true;
                    Main.checkPeopleFunc();
            }});

        checkContentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clear();
                    allWarehouses=true;
                    Main.checkPeopleFunc();
                }
            });

        addPermissionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
                choosePerson=false;
                addPermission=true;
                Main.checkPeopleFunc();
                }
            });

        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
                choosePerson=false;
                if (!personIsChosen) {
                    addItem = true;
                    Main.checkPeopleFunc();
                }
                try {
                    Main.checkPermissionToAddItem();
                } catch (TooManyThingsException ex) {
                    ex.printStackTrace();
                }
            }
            });

            parkTheCarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clear();
                    choosePerson=false;
                    if (!personIsChosen) {
                        Main.checkPeopleFunc();
                    } else {
                        Main.displayParkingList();
                    }
                }
            });

        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
                choosePerson=false;
                if (!personIsChosen) {
                    Main.checkPeopleFunc();
                } else {
                    Main.displayWarehouseList();
                }
            }
        });

        takeOutTheCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
                choosePerson=false;
                if (!personIsChosen) {
                    Main.checkPeopleFunc();
                } else {
                    Main.displayParkingSpace();
                }
            }
        });

        rentCarServiceSpotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
                choosePerson=false;
                if (!personIsChosen) {
                    Main.checkPeopleFunc();
                } else {
                    Main.displayVehicles();
                }
            }
        });

        needServiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
                choosePerson=false;
                if (!personIsChosen) {
                    Main.checkPeopleFunc();
                } else {
                    makeCarService=true;
                    Main.displayVehicles();
                }
            }
        });

        stopServiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
                choosePerson = false;
                if (!personIsChosen) {
                    Main.checkPeopleFunc();
                } else {
                    StopRepairView stopRepairView = new StopRepairView();
                }
            }
        });

        writeToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
                choosePerson=false;
                Main.writeToFile();
            }
        });

        startThreadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choosePerson=false;
                clear();
                TimeLapseThread t1 = new TimeLapseThread();
                LeaseCheckingThread t2 = new LeaseCheckingThread();
                t1.start();
                t2.start();
            }
        });

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = jTextField.getText();
                if(choosePerson) {
                    Main.choosePerson(text);
                    choosePerson=false;
                }
                else if(chooseFreeWarehouse){
                    Main.chooseFreeWarehouse(text);
                    chooseFreeWarehouse =false;
                }
                else if(chooseParking){
                    Main.rentParking(text);
                    chooseParking=false;
                }
                else if(chooseWarehouse){
                    Main.displayListOfRentedPremises(text);
                    chooseWarehouse =false;
                }
                else if(permissionIsAdded){
                    Main.chooseWarehouseToAddPermission(text);
                    permissionIsAdded =false;
                }
                else if(warehouseToItem){
                    try {
                        Main.selectWarehouseToAddItem(text);
                    } catch (TooManyThingsException ex) {
                        ex.printStackTrace();
                    }
                    warehouseToItem =false;
                }
                else if(parkingChosen){
                   Main.selectParkingSpace(text);
                   parkingChosen=false;
                }
                else if(carToPark){
                    Main.chooseVehicleAndParkIt(text);
                    carToPark =false;
                }
                else if(warehouseChosen){
                    Main.chooseWarehouse(text);
                    warehouseChosen=false;
                    Main.displayItems();
                }
                else if(itemIsChosen){
                    Main.getItemOut(text);
                    itemIsChosen=false;
                }
                else if(carToRemove){
                    Main.selectParkingSpaceToRemove(text);
                    carToRemove=false;
                }
                else if(carChosen){
                    Main.chooseVehicle(text);
                    carChosen=false;
                    Main.displayCarService();
                }
                else if(carService){
                    Main.chooseCarService(text);
                    carService=false;
                }
                else if(needService){
                    Main.chooseVehicle(text);
                    ServiceRepairView serviceRepairView = new ServiceRepairView();
                    needService=false;
                }
                else if(serviceSpotChosen){
                    Main.chooseServiceSpot(text);
                    Main.independentCarServiceSpot.startSelfRepair(Main.person, Main.vehicle);
                    serviceSpotChosen=false;
                }
                else if(repairPlace){
                    Main.chooseRepairPlace(text);
                    Main.carServiceSpot.startRepair(Main.vehicle);
                    repairPlace=false;
                }
                else if(finishCSS){
                   Main.finishRepairOfCSS(text);
                }
                else if(finishICSS){
                    Main.finishRepairOfICSS(text);
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    public static JTextArea getTextArea()
    {
        return jTextArea;
    }

    public static void blockViev(){
        MainMenu.getTextArea().setText("");
        MainMenu.jTextField.setText("");
        MainMenu.jTextField.setEnabled(false);
        MainMenu.buttonOK.setEnabled(false);
    }
    public static void unlockViev(){
        MainMenu.getTextArea().setText("");
        MainMenu.jTextField.setText("");
        MainMenu.jTextField.setEnabled(true);
        MainMenu.buttonOK.setEnabled(true);
    }

    public static void clear(){
        MainMenu.jTextField.setText("");
    }

    public static JButton createButton(String txt){
        JButton newButton  = new JButton(txt);
        jButtonList.add(newButton);
        return newButton;
    }

}
