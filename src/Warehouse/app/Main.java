package Warehouse.app;

import Warehouse.app.CarSpot.CarServiceSpot;
import Warehouse.app.CarSpot.IndependentCarServiceSpot;
import Warehouse.app.Exception.TooManyThingsException;
import Warehouse.app.Menu.Homepage;
import Warehouse.app.Menu.MainMenu;
import Warehouse.app.Obj.Data;
import Warehouse.app.Obj.Objects;
import Warehouse.app.Obj.ParkingSpace;
import Warehouse.app.Obj.Person;
import Warehouse.app.Thread.LeaseCheckingThread;
import Warehouse.app.Thread.TimeLapseThread;
import Warehouse.app.Vehicle.Vehicle;
import Warehouse.app.Warehouse.*;
import Warehouse.app.CarSpot.CarService;
import Warehouse.app.View.ParkingSpaceView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Main extends JFrame {
    public static List<ConsumerWarehouse> warehousePremises;
    public static List<ServiceWarehouse> servicePremises;
    public static List<CarServiceSpot> repairPlaces;
    public static List<IndependentCarServiceSpot> serviceSpots;
    public static Person person;
    public static Objects objects;
    public static Vehicle vehicle;
    public static CarService carService;
    public static ParkingSpace parkingSpace;
    public static Warehouse warehouse;
    public static List<Person> peopleList = new ArrayList<>();
    public static List<Warehouse> warehouseList = new ArrayList<>();
    public static List<Warehouse> freeWarehouseList = new ArrayList<>();
    public static List<Warehouse> occupiedWarehouseList = new ArrayList<>();
    public static Set<Objects> objectsList = new HashSet<>();
    public static List<Vehicle> vehiclesList = new LinkedList<>();
    public static List<ParkingSpace> parkingSpaceList = new LinkedList<>();
    public static List<CarService> carServiceList = new LinkedList<>();
    public static List<ParkingSpace> freeParkingSpaceList = new LinkedList<>();
    public static List<Warehouse> authorizedWarehouses = new ArrayList<>();
    public static int number = 0;
    public static String text;
    public static CarServiceSpot carServiceSpot;
    public static IndependentCarServiceSpot independentCarServiceSpot;
    public static boolean typeRight = false;
    public static boolean needParking = false;
    public static Service service;
    public static boolean exit = false;

    public static void main(String[] args) throws Exception {
        int select;
        Homepage homepage = new Homepage();

        // Data
        service = Data.loadData(service);
        warehousePremises = Arrays.asList(ConsumerWarehouse.getConsumerWarehouseList());
        servicePremises = Arrays.asList(ServiceWarehouse.getServicePremisesList());
        repairPlaces = Arrays.asList(CarServiceSpot.getCarServiceSpots());
        serviceSpots = Arrays.asList(IndependentCarServiceSpot.getIndependentCarServiceSpots());
        peopleList = Person.getPersonList();
        warehouseList = Warehouse.getWarehousesList();
        objectsList = Objects.getObjectList();
        vehiclesList = Vehicle.getVehiclesList();
        parkingSpaceList = ParkingSpace.getParkingSpaceList();
        carServiceList = CarService.getCarServiceList();


        // Menu
        while (!exit) {
            System.out.println();
            System.out.println("----Warhouse.app.Menu----");
            System.out.println("WYBIERZ OPCJE:");
            System.out.println(" 1 - Wyjscie z programu");
            System.out.println(" 2 - Wybierz osobe");
            System.out.println(" 3 - Wyswietl informacje");
            System.out.println(" 4 - Wyswietl wolne pomieszczenia");
            System.out.println(" 5 - Wynajmij pomieszczenie");
            System.out.println(" 6 - Sprawdz zawartosc pomieszczenia");
            System.out.println(" 7 - Wloz przedmiot do magazynu");
            System.out.println(" 8 - Dodaj uprawnienia do magazynu");
            System.out.println(" 9 - Zaparkuj pojazd na miejscu parkingowym");
            System.out.println(" 10 - Zabierz przedmiot z magazynu");
            System.out.println(" 11 - Zabierz auto z parkingu");
            System.out.println(" 12 - Wynajmij miejsce serwisowe lub naprawcze");
            System.out.println(" 13 - Zglos potrzebe naprawy lub rozpocznij serwisowanie pojazdu");
            System.out.println(" 14 - Zakoncz naprawe pojazdu");
            System.out.println(" 15 - Zapisz aktualny stan");
            System.out.println(" 16 - Uruchom upływ czasu");


            Scanner sc = new Scanner(System.in);
            while (!sc.hasNextInt()) {
                sc.next();
            }
            select = sc.nextInt();
            switch (select) {

                case 1:
                    exit();
                    break;
                case 2:
                    choosePerson(sc);
                    break;
                case 3:
                    if (person == null) {
                        System.out.println("Najpierw wybierz Osobe:");
                        choosePerson(sc);
                    }
                    displayPersonDetails();
                    break;
                case 4:
                    displayFreeWarehouse();
                    break;
                case 5:
                    if (person == null) {
                        System.out.println("Najpierw wybierz Osobe:");
                        choosePerson(sc);
                    }
                    System.out.println("Wybierz magazyn:");
                    chooseFreeWarehouse(sc);
                    rentParking(sc);
                    break;
                case 6:
                    if (person == null) {
                        System.out.println("Najpierw wybierz Osobe:");
                        choosePerson(sc);
                    }
                    if (person.getRentedWarehousesList().isEmpty()) {
                        System.out.println("Osoba nic nie wynajmuje.");
                    } else {
                        displayPersonDetails();
                        System.out.println("Wybierz id magazynu by zobaczyc jego zawartosc:");
                        displayListOfRentedPremises(sc);
                    }
                    break;
                case 7:
                    if (person == null) {
                        System.out.println("Wybierz osobe:");
                        choosePerson(sc);
                    }
                    addItemToWarehouse(sc);
                    break;
                case 8:
                    if (person == null) {
                        System.out.println("Wybierz osobe ktorej chcesz nadac uprawnienia:");
                        choosePerson(sc);
                    }
                    System.out.println("Wybierz magazyn do ktorego nadasz uprawnienia:");
                    chooseWarehouse(sc);
                    warehouse.addPermission(person);
                    break;
                case 9:
                    if (person == null) {
                        System.out.println("Wybierz osobe:");
                        choosePerson(sc);
                    }
                    System.out.println("Wybierz Miejsce parkingowe:");
                    displayParkingSpace(sc);
                    System.out.println("Wybierz pojazd:");
                    chooseVehicle(sc);
                    parkingSpace.addVehicle(person, vehicle);
                    break;
                case 10:
                    if (person == null) {
                        System.out.println("Wybierz osobe:");
                        choosePerson(sc);
                    }
                    System.out.println("Wybierz magazyn:");
                    chooseWarehouse(sc);
                    System.out.println("Wybierz przedmiot:");
                    System.out.println(warehouse.getWarehouseItems());
                    if (warehouse.getWarehouseItems().isEmpty()) {
                        System.out.println("Brak przedmiotow w magazynie.");
                        break;
                    }
                    number = sc.nextInt();
                    objectsList.forEach(
                            O -> {
                                if (O.getID() == number)
                                    objects = O;

                            }
                    );
                    warehouse.takeOutItem(person, objects);
                    break;
                case 11:
                    if (person == null) {
                        System.out.println("Wybierz osobe:");
                        choosePerson(sc);
                    }
                    System.out.println("Wybierz miejsce parkingowe:");
                    displayParkingSpace(sc);

                    parkingSpace.removeVehicle(person);

                    break;
                case 12:
                    if (person == null) {
                        System.out.println("Wybierz osobe:");
                        choosePerson(sc);
                    }
                    System.out.println("Wybierz pojazd:");
                    chooseVehicle(sc);
                    chooseCarService(sc);
                    carService.rentSpot(person, vehicle);
                    break;
                case 13:
                    if (person == null) {
                        System.out.println("Wybierz osobe:");
                        choosePerson(sc);
                    }
                    System.out.println("Wybierz pojazd:");
                    chooseVehicle(sc);
                    System.out.println("Czy chcesz samodzielnie serwisowac pojazd? Wpisz: T jesli TAK. Wpisz dowolny znak jesli ma byc naprawiany przez mechaników:");
                    text = sc.next();
                    if (text.equals("T") || text.equals("t") || text.equals("TAK") || text.equals("tak")) {
                        System.out.println("Wybrano samodzielna naprawe.");
                        System.out.println("Wybierz miejsce serwisowe:");
                        chooseServiceSpot(sc);
                        independentCarServiceSpot.startSelfRepair(person, vehicle);
                    } else {
                        System.out.println("Wybrano naprawe przez mechaników.");
                        System.out.println("Wybierz miejsce naprawcze:");
                        chooseRepairPlace(sc);
                        carServiceSpot.startRepair(vehicle);
                    }
                    break;
                case 14:
                    System.out.println("Jaki rodzaj naprawy chcesz zakonczyc? Wpisz: TAK jesli Samodzielna lub wpisz dowolny znak jesli NIE.");
                    text = sc.next();
                    if (text.equals("t") || text.equals("T") || text.equals("TAK") || text.equals("tak") || text.equals("Tak")) {
                        finishRepairOfICSS(sc);
                    } else {
                        finishRepairOfCSS(sc);
                    }
                    break;
                case 15:
                    writeToFile();
                    break;
                case 16:
                    TimeLapseThread t1 = new TimeLapseThread();
                    LeaseCheckingThread t2 = new LeaseCheckingThread();
                    t1.start();
                    t2.start();
                    break;
                default:
                    System.out.println("Prosze podac numer opcji zgodny z MENU.");
            }
        }
    }


    //Functions
    public static void exit() {
        exit = true;
    }

    //GUIFunction
    public static void startThread(){

    }

    //ConsoleFunction
    public static void choosePerson(Scanner sc) {
        System.out.println(peopleList);
        number = sc.nextInt();
        peopleList.forEach(
                P -> {
                    if (P.getId() == number)
                        person = P;
                });
        System.out.println("Wybrano: " + person.firstName + " " + person.lastName);
        System.out.println();
    }

    //GUIFunction
    public static void choosePerson(String sc) {
        AtomicBoolean find = new AtomicBoolean(false);
        number = Integer.parseInt(sc);
        peopleList.forEach(
                P -> {
                    if (P.getId() == number) {
                        person = P;
                        find.set(true);
                    }
                });
        if (!find.get()) MainMenu.getTextArea().setText("Wybrano bledny identyfikator osoby." + "\n");
        else {
            MainMenu.getTextArea().setText("Wybrano: " + person.firstName + " " + person.lastName + "\n");
            MainMenu.personIsChosen = true;
            MainMenu.jTextField.setEnabled(false);
            MainMenu.buttonOK.setEnabled(false);
            MainMenu.jTextField.setText("");
        }

    }

    //GUIFunction
    public static void checkPeopleFunc() {
        if (person == null) {
            MainMenu.getTextArea().setText("Aby kontynuowac wybierz osobe.");
            MainMenu.choosePerson = true;
        } else if (MainMenu.freeWarehouses) {
            displayFreeWarehouse();
            MainMenu.chooseFreeWarehouse = true;
            MainMenu.freeWarehouses = false;
        } else if (MainMenu.allWarehouses) {
            if (person.getRentedWarehousesList().isEmpty()) {
                MainMenu.getTextArea().setText("Osoba nic nie wynajmuje.");
            } else {
                MainMenu.jTextField.setEnabled(true);
                MainMenu.buttonOK.setEnabled(true);
                MainMenu.getTextArea().setText("Wpisz id magazynu by sprawdzic jego zawartosc:" + "\n\n");
                displayPersonDetails2();
                MainMenu.chooseWarehouse = true;
            }
            MainMenu.allWarehouses = false;
        } else if (MainMenu.addPermission) {
            MainMenu.getTextArea().setText("Wybierz magazyn do ktorego nadasz uprawnienia:" + "\n\n");
            String listToPrint = warehouseList.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("\n"));
            MainMenu.getTextArea().append(listToPrint);
            MainMenu.permissionIsAdded = true;
            MainMenu.addPermission = false;
            MainMenu.jTextField.setEnabled(true);
            MainMenu.buttonOK.setEnabled(true);
        }
    }

    //ConsoleFunction
    public static void displayParkingList() {
        MainMenu.unlockViev();
        MainMenu.getTextArea().setText("Wybierz Miejsce parkingowe:\n\n");
        String listToPrint = parkingSpaceList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
        MainMenu.getTextArea().append(listToPrint);
        MainMenu.parkingChosen=true;
    }

    //GUIFunction
    public static void selectParkingSpace(String sc) {
        number = Integer.parseInt(sc);
        parkingSpaceList.forEach(
                L -> {
                    if (L.getID() == number)
                        parkingSpace = L;

                });
        MainMenu.jTextField.setText("");
        MainMenu.getTextArea().setText("Wybierz pojazd:\n\n");
        String listToPrint = vehiclesList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
        MainMenu.getTextArea().append(""+listToPrint);
        MainMenu.carToPark =true;
    }

    //GUIFunction
    public static void displayPeople() {
        String listToPrint = peopleList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
        MainMenu.getTextArea().setText(listToPrint);
    }

    //ConsoleFunction
    public static void chooseWarehouse(Scanner sc) {
        System.out.println(warehouseList);
        number = sc.nextInt();
        warehouseList.forEach(
                W -> {
                    if (W.getId() == number)
                        warehouse = W;
                });
    }

    //GUIFunction
    public static void chooseWarehouseToAddPermission(String sc) {
        number = Integer.parseInt(sc);
        warehouseList.forEach(
                W -> {
                    if (W.getId() == number)
                        warehouse = W;
                });

        warehouse.addPermission(person);
    }

    //GUIFunction
    public static void chooseWarehouse(String sc){
        number = Integer.parseInt(sc);
        warehouseList.forEach(
                W -> {
                    if (W.getId() == number)
                        warehouse = W;
                });
    }

    //ConsoleFunction
    public static void finishRepairOfICSS(Scanner sc) {
        Map<IndependentCarServiceSpot, Vehicle> lista = IndependentCarServiceSpot.getAllServicedVehiclesList();
        if (!lista.isEmpty()) {
            if (person == null) {
                System.out.println("Wybierz osobe:");
                choosePerson(sc);
            }
            System.out.println("Wybierz miejsce serwisowe:");
            System.out.println(lista);
            number = sc.nextInt();
            for (Map.Entry<IndependentCarServiceSpot, Vehicle> entry : lista.entrySet()) {
                if (entry.getKey().getId() == number)
                    independentCarServiceSpot = entry.getKey();

            }
            independentCarServiceSpot.finishSelfRepair(person);
        } else
            System.out.println("Nie ma obecnie zadnych napraw.");
    }

    //GUIFunction
    public static void finishRepairOfCSS(String sc) {
        Map<CarServiceSpot, Vehicle> lista = CarServiceSpot.getAllRepairedVehicles();
        MainMenu.getTextArea().setText("NR :"+sc+"\n\n");
        MainMenu.getTextArea().append(""+lista);
        number = Integer.parseInt(sc);
        for (Map.Entry<CarServiceSpot, Vehicle> entry : lista.entrySet()) {
            if (entry.getKey().getId() == number)
                carServiceSpot = entry.getKey();
        }
        carServiceSpot.finishRepair();
    }

    //ConsoleFunction
    public static void finishRepairOfCSS(Scanner sc) {
        Map<CarServiceSpot, Vehicle> lista = CarServiceSpot.getAllRepairedVehicles();
        if (!lista.isEmpty()) {
            System.out.println("Wybierz miejsce Naprawcze:");
            System.out.println(lista);
            number = sc.nextInt();
            for (Map.Entry<CarServiceSpot, Vehicle> entry : lista.entrySet()) {
                if (entry.getKey().getId() == number)
                    carServiceSpot = entry.getKey();
            }
            carServiceSpot.finishRepair();
        } else
            System.out.println("Nie ma obecnie zadnych napraw.");

    }

    //GUIFunction
    public static void finishRepairOfICSS(String sc) {
        Map<IndependentCarServiceSpot, Vehicle> lista = IndependentCarServiceSpot.getAllServicedVehiclesList();
        if (!lista.isEmpty()) {
            MainMenu.getTextArea().setText("Wybierz miejsce Naprawcze:");
            MainMenu.getTextArea().append(""+lista);
            number = Integer.parseInt(sc);
            for (Map.Entry<IndependentCarServiceSpot, Vehicle> entry : lista.entrySet()) {
                if (entry.getKey().getId() == number)
                    independentCarServiceSpot = entry.getKey();
            }
            independentCarServiceSpot.finishSelfRepair(person);
        } else
            MainMenu.getTextArea().setText("Nie ma obecnie zadnych napraw.");

    }

    //ConsoleFunction
    public static void chooseFreeWarehouse(Scanner sc) {
        freeWarehouseList = getFreeWarehouseList();
        System.out.println(freeWarehouseList);
        number = sc.nextInt();
        freeWarehouseList.forEach(
                W -> {
                    if (W.getId() == number) {
                        warehouse = W;
                    }
                });
    }

    //GUIFunction
    public static void chooseFreeWarehouse(String sc) {
        number = Integer.parseInt(sc);
        freeWarehouseList.forEach(
                W -> {
                    if (W.getId() == number) {
                        warehouse = W;
                    }
                });
        MainMenu.jTextField.setText("");
        ParkingSpaceView parkingSpaceView = new ParkingSpaceView();
    }

    //ConsoleFunction
    public static void rentParking(Scanner sc) {
        while (!typeRight) {
            System.out.println("Czy chcesz wynajac miejsce parkingowe? Wpisz:  T  jeśli tak, wpisz:  N  jeśli nie:");
            text = sc.next();
            if (text.equals("T") || text.equals("t") || text.equals("tak") || text.equals("TAK") || text.equals("Tak")) {
                typeRight = true;
                needParking = true;
            } else if (text.equals("N") || text.equals("n") || text.equals("nie") || text.equals("NIE")) {
                typeRight = true;
                needParking = false;
            } else {
                System.out.println("Nie wpisales dobrego znaku.");
            }
        }

        if (needParking) {
            System.out.println("Wybierz miejsce parkingowe ktore chcesz wynajac.");
            chooseParkingSpace(sc);
            warehouse.rentWarehouse(person, parkingSpace);
        } else {
            warehouse.rentWarehouse(person, null);
        }
        typeRight = false;
    }

    //GUIFunction
    public static void rentParking(String sc) {
        MainMenu.jTextField.setText("");
        MainMenu.jTextField.setEnabled(false);
        MainMenu.buttonOK.setEnabled(false);
        if (needParking) {
            chooseFreeParkingSpace(sc);
            warehouse.rentWarehouse(person, parkingSpace);
        } else {
            warehouse.rentWarehouse(person, null);
        }
    }

    //ConsoleFunction
    public static void displayListOfRentedPremises(Scanner sc) {
        number = sc.nextInt();
        person.getRentedWarehousesList().forEach(
                M -> {
                    if (M.getId() == number) {
                        warehouse = M;
                    }
                });
        System.out.print(warehouse.getWarehouseItems());

    }

    //GUIFunction
    public static void displayListOfRentedPremises(String sc) {
        number = Integer.parseInt(sc);
        person.getRentedWarehousesList().forEach(
                M -> {
                    if (M.getId() == number) {
                        warehouse = M;
                    }
                });

        HashMap<Warehouse, List<Objects>> map = warehouse.getWarehouseItems();
        if (map.isEmpty()) MainMenu.getTextArea().setText("Brak przedmiotow w magazynie");
        else MainMenu.getTextArea().setText("" + map);
        MainMenu.jTextField.setEnabled(false);
        MainMenu.buttonOK.setEnabled(false);
    }

    //ConsoleFunction
    public static void addItemToWarehouse(Scanner sc) throws TooManyThingsException {
        for (Map.Entry<Warehouse, Person> entry : Warehouse.authorizedPeople.entrySet()) {
            Warehouse key = entry.getKey();
            Person value = entry.getValue();
            if (value.equals(person) && !authorizedWarehouses.contains(key)) {
                authorizedWarehouses.add(key);
            }
        }
        if (!authorizedWarehouses.isEmpty()) {
            System.out.println("Wybierz magazyn do ktorego chcesz wlozyc przedmiot:");
            System.out.println(authorizedWarehouses);
            number = sc.nextInt();
            authorizedWarehouses.forEach(
                    W -> {
                        if (W.getId() == number) {
                            warehouse = W;
                        }
                    });
            System.out.println("Wybierz przedmiot:");
            warehouse.getWarehouseItems();
            chooseItem(sc);
            warehouse.addItem(person, objects);
        } else {
            System.out.println("Nie masz uprawnien do zadnego magazynu.");
        }
    }

    //GUIFunction
    public static void checkPermissionToAddItem() throws TooManyThingsException {
        for (Map.Entry<Warehouse, Person> entry : Warehouse.authorizedPeople.entrySet()) {
            Warehouse key = entry.getKey();
            Person value = entry.getValue();
            if (value.equals(person) && !authorizedWarehouses.contains(key)) {
                authorizedWarehouses.add(key);
            }
        }
        if (!authorizedWarehouses.isEmpty()) {
            MainMenu.unlockViev();
            MainMenu.getTextArea().setText("Wybierz magazyn do ktorego chcesz wlozyc przedmiot:" + "\n\n");
            MainMenu.getTextArea().append("" + authorizedWarehouses);
            MainMenu.addItem = false;
            MainMenu.warehouseToItem = true;

        } else {
            MainMenu.getTextArea().setText("Nie masz uprawnien do zadnego magazynu.");
        }
    }

    //GUIFunction
    public static void selectWarehouseToAddItem(String sc) throws TooManyThingsException {
            number = Integer.parseInt(sc);
            authorizedWarehouses.forEach(W -> {
                if (W.getId() == number) {
                    warehouse = W;
                }
            });
            SelectWarehouseView s1 = new SelectWarehouseView();
    }

    //GUIFunction
    public static void displayItems(){
        MainMenu.clear();
        MainMenu.getTextArea().setText("Wybierz przedmiot:\n\n");
        MainMenu.getTextArea().append(""+warehouse.getWarehouseItems());
        if (warehouse.getWarehouseItems().isEmpty()) {
            MainMenu.getTextArea().setText("Brak przedmiotow w magazynie.");
        }else {
            MainMenu.itemIsChosen=true;
        }
    }

    //GUIFunction
    public static void getItemOut(String sc){
        number = Integer.parseInt(sc);
        objectsList.forEach(
                O -> {
                    if (O.getID() == number)
                        objects = O;

                }
        );
        warehouse.takeOutItem(person, objects);

    }

    //ConsoleFunction
    public static void chooseParkingSpace(Scanner sc) {
        displayFreeWarehouseList();
        number = sc.nextInt();
        freeParkingSpaceList.forEach(
                L -> {
                    if (L.getID() == number)
                        parkingSpace = L;
                });
    }

    //GUIFunction
    public static void chooseFreeParkingSpace(String sc) {
        updateFreeParkingList();
        number = Integer.parseInt(sc);
        freeParkingSpaceList.forEach(
                L -> {
                    if (L.getID() == number)
                        parkingSpace = L;
                });
    }

    //GUIFunction
    public static void displayFreeWarehouse(){
        freeWarehouseList = getFreeWarehouseList();
        String listToPrint = freeWarehouseList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
        MainMenu.getTextArea().setText(listToPrint);
    }

    //GUIFunction
    public static void displayWarehouseList(){
        MainMenu.unlockViev();
        String listToPrint = warehouseList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
        MainMenu.getTextArea().setText(listToPrint);
        MainMenu.warehouseChosen=true;
    }

    //ConsoleFunction
    public static void displayParkingSpace(Scanner sc) {
        System.out.println(parkingSpaceList);
        number = sc.nextInt();
        parkingSpaceList.forEach(
                L -> {
                    if (L.getID() == number)
                        parkingSpace = L;

                });
    }

    //GUIFunction
    public static void displayParkingSpace() {
        String listToPrint = parkingSpaceList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
        MainMenu.unlockViev();
        MainMenu.getTextArea().setText("Wybierz miejsce parkingowe z pojazdem:\n\n");
        MainMenu.getTextArea().append(listToPrint);
        MainMenu.carToRemove=true;

    }

    //GUIFunction
    public static void selectParkingSpaceToRemove(String sc){
        number = Integer.parseInt(sc);
        parkingSpaceList.forEach(
                L -> {
                    if (L.getID() == number)
                        parkingSpace = L;

                });
        parkingSpace.removeVehicle(person);
    }

    //ConsoleFunction
    public static void chooseItem(Scanner sc) {
        System.out.println(objectsList);
        number = sc.nextInt();
        objectsList.forEach(
                O -> {
                    if (O.getID() == number)
                        objects = O;
                });
    }

    //GUIFunction
    public static void chooseItem(String sc) throws TooManyThingsException {
        number = Integer.parseInt(sc);
        objectsList.forEach(
                O -> {
                    if (O.getID() == number)
                        objects = O;
                });
        warehouse.addItem(person, objects);
    }

    //ConsoleFunction
    public static void chooseVehicle(Scanner sc) {
        System.out.println(vehiclesList);
        number = sc.nextInt();
        vehiclesList.forEach(
                V -> {
                    if (V.getVehicleID() == number)
                        vehicle = V;
                });
        System.out.println("Wybrano: "+ vehicle);
        System.out.println();
    }

    //GUIFunction
    public static void chooseVehicleAndParkIt(String sc) {
        number = Integer.parseInt(sc);
        vehiclesList.forEach(
                V -> {
                    if (V.getVehicleID() == number)
                        vehicle = V;
                });
        Main.parkingSpace.addVehicle(person, vehicle);
        System.out.println();
    }

    //GUIFunction
    public static void displayVehicles(){
        MainMenu.unlockViev();
        String listToPrint = vehiclesList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
        if(MainMenu.makeCarService) {
            MainMenu.getTextArea().setText("Wybierz pojazd ktory chcesz naprawic lub serwisowac:\n\n");
            MainMenu.getTextArea().append(listToPrint);
            MainMenu.makeCarService=false;
            MainMenu.needService=true;
        } else{
            MainMenu.getTextArea().setText("Wybierz pojazd dla ktorego chcesz wynajac miejsce:\n\n");
            MainMenu.getTextArea().append(listToPrint);
            MainMenu.carChosen=true;
        }
    }


    //GUIFunction
    public static void chooseVehicle(String sc) {
        number = Integer.parseInt(sc);
        vehiclesList.forEach(
                V -> {
                    if (V.getVehicleID() == number)
                        vehicle = V;
                });
    }

    //ConsoleFunction
    public static void chooseRepairPlace(Scanner sc) {
        System.out.println(repairPlaces);
        number = sc.nextInt();
        repairPlaces.forEach(
                V -> {
                    if (V.getId() == number)
                        carServiceSpot = V;
                });
    }

    //GUIFunction
    public static void chooseRepairPlace(String sc) {
        number = Integer.parseInt(sc);
        repairPlaces.forEach(
                V -> {
                    if (V.getId() == number)
                        carServiceSpot = V;
                });
    }

    //ConsoleFunction
    public static void chooseServiceSpot(Scanner sc) {
        System.out.println(serviceSpots);
        number = sc.nextInt();
        serviceSpots.forEach(
                V -> {
                    if (V.getId() == number)
                        independentCarServiceSpot = V;
                });
    }

    //ConsoleFunction
    public static void chooseServiceSpot(String sc) {
        System.out.println(serviceSpots);
        number = Integer.parseInt(sc);
        serviceSpots.forEach(
                V -> {
                    if (V.getId() == number)
                        independentCarServiceSpot = V;
                });
    }

    //ConsoleFunction
    public static void chooseCarService(Scanner sc) {
        System.out.println("Wybierz miejsce ktore chcesz wynajac");
        System.out.println(carServiceList);
        number = sc.nextInt();
        carServiceList.forEach(
                LMP -> {
                    if (LMP.getId() == number)
                        carService = LMP;
                });
    }

    //GUIFunction
    public static void displayCarService() {
        MainMenu.unlockViev();
        MainMenu.getTextArea().setText("Wybierz miejsce ktore chcesz wynajac:\n\n");
        String listToPrint = carServiceList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
        MainMenu.getTextArea().append(""+listToPrint);
        MainMenu.carService=true;
    }

    //GUIFunction
    public static void chooseCarService(String sc){
        number = Integer.parseInt(sc);
        carServiceList.forEach(
                LMP -> {
                    if (LMP.getId() == number)
                        carService = LMP;
                });
        carService.rentSpot(person, vehicle);
    }


    //ConsoleFunction
    public static List<Warehouse> getFreeWarehouseList() {
        freeWarehouseList = warehouseList.stream()
                .filter(e -> !e.ifBusy)
                .collect(toList());
        return freeWarehouseList;
    }

    //ConsoleFunction
    public static List<Warehouse> getOccupiedWarehouseList() {
        occupiedWarehouseList = warehouseList.stream()
                .filter(e -> e.ifBusy)
                .collect(toList());
        return occupiedWarehouseList;
    }

    //ConsoleFunction
    public static void displayFreeWarehouseList() {
        freeParkingSpaceList = parkingSpaceList.stream()
                .filter(e -> !e.ifRented)
                .collect(toList());
        System.out.println(freeParkingSpaceList);
    }

    //GUIFunction
    public static void updateFreeParkingList() {
        freeParkingSpaceList = parkingSpaceList.stream()
                .filter(e -> !e.ifRented)
                .collect(toList());
    }

    //GUIFunction
    public static List<ParkingSpace> getFreeParkingSpaceList() {
        return freeParkingSpaceList;
    }

    //ConsoleFunction
    public static void displayPersonDetails() {
        System.out.println(person.toString());
        System.out.print("Wynajete miejsca: ");
        person.getRentedWarehousesList().forEach(System.out::println);
        if (person.getRentedWarehousesList().isEmpty()) {
            System.out.print("brak. ");
        }

        List<Warehouse> magazyny = new LinkedList<>();
        for (Map.Entry<Warehouse, Person> os : Warehouse.authorizedPeople.entrySet()) {
            Warehouse key = os.getKey();
            Person value = os.getValue();
            if (value.equals(person)) {
                magazyny.add(key);
            }
        }
        System.out.println("Posiadane uprawnienia:" + magazyny);
    }

    //GUIFunction
    public static void displayPersonDetails2() {
        if (person == null) {
            MainMenu.getTextArea().setText("Aby kontynuowac wybierz osobe.");
        } else {
            MainMenu.blockViev();
            MainMenu.getTextArea().append(person.toString()+"\n");
            MainMenu.getTextArea().append("Wynajete miejsca: "+"\n");
            List<Warehouse> rentedWarehousesList = person.getRentedWarehousesList();
            rentedWarehousesList.forEach((s) -> MainMenu.getTextArea().append(s + "\n"));

            if (rentedWarehousesList.isEmpty()) {
                MainMenu.getTextArea().append("brak." +"\n");
            }


            List<Warehouse> magazyny = new LinkedList<>();
            for (Map.Entry<Warehouse, Person> os : Warehouse.authorizedPeople.entrySet()) {
                Warehouse key = os.getKey();
                Person value = os.getValue();
                if (value.equals(person)) {
                    magazyny.add(key);
                }
            }

            MainMenu.getTextArea().append("\n"+"Posiadane uprawnienia:" +"\n"+ magazyny+"\n");
        }
    }

    //ConsoleFunction
    public static void getWarehouseItems(PrintWriter writer) {
        writer.println("Przedmioty w magazynach: ");
        for (Warehouse l : warehouseList) {
            if(!l.getWarehouseItems().isEmpty()) writer.println(l.getWarehouseItems());
        }
        writer.println();
    }

    //ConsoleFunction
    public static Person getPerson() {
        return person;
    }

    //ConsoleFunction
    public static Map<CarServiceSpot,Queue<Vehicle>> getQueueWaitingForRepairCSS(){
        Map<CarServiceSpot,Queue<Vehicle>> map = new HashMap<>();
        for (CarServiceSpot c : repairPlaces) {
            if(!c.getVehicleQueue().isEmpty())
            map.put(c,c.getVehicleQueue());
        }
        return map;
    }

    //ConsoleFunction
    public static Map<IndependentCarServiceSpot,Queue<Vehicle>> getQueueWaitingForRepairICSS(){
        Map<IndependentCarServiceSpot,Queue<Vehicle>> map = new HashMap<>();
        for (IndependentCarServiceSpot c : serviceSpots) {
            if(!c.getQueueOfWaitingVehicles().isEmpty())
                map.put(c,c.getQueueOfWaitingVehicles());
        }
        return map;
    }

    //ConsoleFunction
    public static Map<CarService, Set<Vehicle>> getHistoryOfRepairs(){
        Map<CarService, Set<Vehicle>> map = CarService.getRepairHistory();
        return map;
    }

    //ConsoleFunction and GUIFunction
    public static void writeToFile() {
        PrintWriter writer = null;
        try {
            File file = new File("JavaGUI-Warehouses/src/warehouses.txt");
            file.createNewFile();
            writer = new PrintWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.println(service);
        writer.println();
        voidCollection("Lista pomieszczen: ", writer, warehouseList);
        voidCollection("Wolne magazyny: ", writer, getFreeWarehouseList());
        voidCollection("Zajete magazyny: ", writer, getOccupiedWarehouseList());
        voidCollection("Lista osob: ", writer, peopleList);
        voidCollection("Lista przedmiotow: ", writer, objectsList);
        voidCollection("Lista osob wynajmujacych: ", writer, Warehouse.getTenantsList());
        writer.println();
        getWarehouseItems(writer);
        writer.println();
        writer.close();

        PrintWriter writer2 = null;
        try {
            File file2 = new File("JavaGUI-Warehouses/src/services.txt");
            file2.createNewFile();
            writer2 = new PrintWriter(file2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        writer2.println(service);
        writer2.println();
        voidCollection("Miejsca parkingowe: ",writer2, parkingSpaceList);
        voidCollection("Miejsca serwisowe: ",writer2, serviceSpots);
        voidCollection("Miejsca naprawcze:",writer2, repairPlaces);
        voidCollection("Lista pojazdow: " ,writer2, vehiclesList);
        voidCollection2("Lista pojazdow naprawianych: ",writer2, CarServiceSpot.getAllRepairedVehicles());
        voidCollection4("Lista oczekujacych na naprawe: ",writer2, getQueueWaitingForRepairCSS());
        voidCollection3("Lista pojazdow serwisowanych",writer2, IndependentCarServiceSpot.getAllServicedVehiclesList());
        voidCollection5("Lista oczekujacych na serwis: ",writer2, getQueueWaitingForRepairICSS());
        voidCollection6("Historia napraw: ",writer2, getHistoryOfRepairs());

        writer2.println();
        writer2.close();
    }

    //ConsoleFunctions and GUIFunctions
    public static <T> void voidCollection(String message, PrintWriter wr, Collection<T> collection) {
        wr.println(message);
        collection.forEach(wr::println);
        wr.println();
    }

    public static void voidCollection(String message, PrintWriter wr, Map<Person, List<Warehouse>> collection) {
        wr.println(message);
        collection.forEach((person, warehouses) -> wr.println(person + " " + warehouses));
        wr.println();
    }

    public static void voidCollection2(String message, PrintWriter wr, Map<CarServiceSpot, Vehicle> collection) {

        wr.println(message);
        collection.forEach((carServiceSpot, vehicle) -> wr.println(carServiceSpot + ":  " + vehicle));
        wr.println();
    }

    public static void voidCollection3(String message, PrintWriter wr, Map<IndependentCarServiceSpot, Vehicle> collection) {
        wr.println(message);
        collection.forEach((carServiceSpot, vehicle) -> wr.println(carServiceSpot + ":  " + vehicle));
        wr.println();
    }

    public static void voidCollection4(String message, PrintWriter wr, Map<CarServiceSpot,Queue<Vehicle>> collection) {
        wr.println(message);
        collection.forEach((c, v) -> wr.println(c + ":  " + v));
        wr.println();
    }

    public static void voidCollection5(String message, PrintWriter wr, Map<IndependentCarServiceSpot,Queue<Vehicle>> collection) {
        wr.println(message);
        collection.forEach((c, v) -> wr.println(c + ":  " + v));
        wr.println();
    }

    public static void voidCollection6(String message, PrintWriter wr, Map<CarService, Set<Vehicle>> collection){
        wr.println(message);
        collection.forEach((c, v) -> wr.println(c + ":  " + v));
        wr.println();
    }
}
