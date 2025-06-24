import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.io.*;

// Represents a parking slot
class Slot {
    private int slotId;
    private boolean isOccupied;
    private boolean isVip;
    private Vehicle currentVehicle;

    public Slot(int slotId, boolean isVip) {
        this.slotId = slotId;
        this.isVip = isVip;
        this.isOccupied = false;
        this.currentVehicle = null;
    }

    public int getSlotId() {
        return slotId;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public boolean isVip() {
        return isVip;
    }

    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }

    public void occupy(Vehicle vehicle) {
        this.currentVehicle = vehicle;
        this.isOccupied = true;
    }

    public void free() {
        this.currentVehicle = null;
        this.isOccupied = false;
    }
}

// Represents a vehicle
class Vehicle {
    private String licensePlate;
    private boolean isVip;
    private LocalDateTime entryTime;

    public Vehicle(String licensePlate, boolean isVip) {
        this.licensePlate = licensePlate;
        this.isVip = isVip;
        this.entryTime = LocalDateTime.now();
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public boolean isVip() {
        return isVip;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }
}

// Circular Queue for regular slot management
class CircularQueue {
    private Slot[] slots;
    private int front, rear, size, capacity;

    public CircularQueue(int capacity) {
        this.capacity = capacity;
        this.slots = new Slot[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    public boolean enqueue(Slot slot) {
        if (isFull()) return false;
        rear = (rear + 1) % capacity;
        slots[rear] = slot;
        size++;
        return true;
    }

    public Slot dequeue() {
        if (isEmpty()) return null;
        Slot slot = slots[front];
        slots[front] = null;
        front = (front + 1) % capacity;
        size--;
        return slot;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }
}

// MaxHeap for VIP prioritization
class MaxHeap {
    private Slot[] heap;
    private int size;
    private int capacity;

    public MaxHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new Slot[capacity];
        this.size = 0;
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private int leftChild(int index) {
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }

    public boolean insert(Slot slot) {
        if (size >= capacity) return false;
        heap[size] = slot;
        int current = size;
        size++;

        while (current != 0 && heap[current].getSlotId() < heap[parent(current)].getSlotId()) {
            swap(current, parent(current));
            current = parent(current);
        }
        return true;
    }

    private void swap(int i, int j) {
        Slot temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public Slot extractMax() {
        if (size <= 0) return null;
        if (size == 1) {
            size--;
            return heap[0];
        }

        Slot max = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapify(0);
        return max;
    }

    private void heapify(int index) {
        int left = leftChild(index);
        int right = rightChild(index);
        int smallest = index;

        if (left < size && heap[left].getSlotId() < heap[smallest].getSlotId()) {
            smallest = left;
        }
        if (right < size && heap[right].getSlotId() < heap[smallest].getSlotId()) {
            smallest = right;
        }

        if (smallest != index) {
            swap(index, smallest);
            heapify(smallest);
        }
    }
}

// Entry/Exit log using LinkedList with file handling
class EntryExitLog {
    class LogEntry {
        String licensePlate;
        LocalDateTime entryTime;
        LocalDateTime exitTime;
        boolean isVip;
        int slotId;

        LogEntry(String licensePlate, LocalDateTime entryTime, boolean isVip, int slotId) {
            this.licensePlate = licensePlate;
            this.entryTime = entryTime;
            this.isVip = isVip;
            this.slotId = slotId;
            this.exitTime = null;
        }

        @Override
        public String toString() {
            return String.format("%s,%s,%s,%b,%d",
                    licensePlate, entryTime, exitTime == null ? "null" : exitTime, isVip, slotId);
        }
    }

    private LinkedList<LogEntry> logs;
    private static final String LOG_FILE = "parking_logs.txt";

    public EntryExitLog() {
        logs = new LinkedList<>();
        loadLogsFromFile();
    }

    private void loadLogsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    LogEntry entry = new LogEntry(
                            parts[0],
                            LocalDateTime.parse(parts[1]),
                            Boolean.parseBoolean(parts[3]),
                            Integer.parseInt(parts[4])
                    );
                    if (!parts[2].equals("null")) {
                        entry.exitTime = LocalDateTime.parse(parts[2]);
                    }
                    logs.add(entry);
                }
            }
        } catch (IOException e) {
            // File might not exist yet, that's okay
        }
    }

    private void saveLogsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE))) {
            // Sort logs by entry time before saving
            logs.sort(Comparator.comparing(log -> log.entryTime));
            for (LogEntry log : logs) {
                writer.write(log.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    public void addEntry(String licensePlate, boolean isVip, int slotId) {
        logs.add(new LogEntry(licensePlate, LocalDateTime.now(), isVip, slotId));
        saveLogsToFile();
    }

    public void addExit(String licensePlate) {
        for (LogEntry log : logs) {
            if (log.licensePlate.equals(licensePlate) && log.exitTime == null) {
                log.exitTime = LocalDateTime.now();
                saveLogsToFile();
                break;
            }
        }
    }

    public LinkedList<LogEntry> getLogs() {
        return logs;
    }
}

// Analytics Engine
class AnalyticsEngine {
    private EntryExitLog log;

    public AnalyticsEngine(EntryExitLog log) {
        this.log = log;
    }

    public double getAverageStayTime() {
        double totalMinutes = 0;
        int completedStays = 0;

        for (EntryExitLog.LogEntry entry : log.getLogs()) {
            if (entry.exitTime != null) {
                totalMinutes += java.time.Duration.between(entry.entryTime, entry.exitTime).toMinutes();
                completedStays++;
            }
        }
        return completedStays == 0 ? 0 : totalMinutes / completedStays;
    }

    public String getPeakHour() {
        int[] hourCount = new int[24];
        for (EntryExitLog.LogEntry entry : log.getLogs()) {
            if (entry.entryTime != null) {
                hourCount[entry.entryTime.getHour()]++;
            }
        }
        int maxHour = 0;
        int maxCount = 0;
        for (int i = 0; i < 24; i++) {
            if (hourCount[i] > maxCount) {
                maxCount = hourCount[i];
                maxHour = i;
            }
        }
        return String.format("%02d:00-%02d:00", maxHour, maxHour + 1);
    }
}

// Parking Manager
class ParkingManager {
    private CircularQueue regularSlots;
    private MaxHeap vipSlots;
    private HashMap<String, Slot> vehicleToSlot;
    private EntryExitLog log;
    private AnalyticsEngine analytics;

    public ParkingManager(int totalSlots, int vipSlotsCount) {
        regularSlots = new CircularQueue(totalSlots - vipSlotsCount);
        vipSlots = new MaxHeap(vipSlotsCount);
        vehicleToSlot = new HashMap<>();
        log = new EntryExitLog();
        analytics = new AnalyticsEngine(log);

        for (int i = 1; i <= totalSlots - vipSlotsCount; i++) {
            regularSlots.enqueue(new Slot(i, false));
        }
        for (int i = totalSlots - vipSlotsCount + 1; i <= totalSlots; i++) {
            vipSlots.insert(new Slot(i, true));
        }
    }

    public String checkInVehicle(String licensePlate, boolean isVip) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            return "Invalid license plate";
        }
        String compositeKey = licensePlate + "_" + System.currentTimeMillis();
        Slot slot = isVip ? vipSlots.extractMax() : regularSlots.dequeue();
        if (slot == null) {
            return "No available slots";
        }
        Vehicle vehicle = new Vehicle(licensePlate, isVip);
        slot.occupy(vehicle);
        vehicleToSlot.put(compositeKey, slot);
        log.addEntry(licensePlate, isVip, slot.getSlotId());
        return "Vehicle parked in slot " + slot.getSlotId();
    }

    public String checkOutVehicle(String licensePlate) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            return "Invalid license plate";
        }
        for (String key : vehicleToSlot.keySet()) {
            if (key.startsWith(licensePlate + "_")) {
                Slot slot = vehicleToSlot.get(key);
                slot.free();
                if (slot.isVip()) {
                    vipSlots.insert(slot);
                } else {
                    regularSlots.enqueue(slot);
                }
                vehicleToSlot.remove(key);
                log.addExit(licensePlate);
                return "Vehicle exited from slot " + slot.getSlotId();
            }
        }
        return "Vehicle not found";
    }

    public AnalyticsEngine getAnalytics() {
        return analytics;
    }

    public HashMap<String, Slot> getVehicleToSlot() {
        return vehicleToSlot;
    }
}

// Main Swing Application
public class SmartParkingSystem {
    private ParkingManager manager;
    private JFrame frame;
    private JTextArea statusArea;
    private JList<String> vehicleList;
    private DefaultListModel<String> listModel;

    public SmartParkingSystem() {
        manager = new ParkingManager(1000, 100); // 1000 total slots, 100 VIP
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Smart Parking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        // Entry Panel
        JPanel entryPanel = new JPanel();
        entryPanel.setLayout(new FlowLayout());
        entryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField licensePlateField = new JTextField(15);
        licensePlateField.setToolTipText("Enter License Plate");
        JCheckBox vipCheck = new JCheckBox("VIP");
        JButton checkInButton = new JButton("Check In");
        checkInButton.setBackground(new Color(76, 175, 80));
        checkInButton.setForeground(Color.WHITE);
        JButton checkOutButton = new JButton("Check Out");
        checkOutButton.setBackground(new Color(244, 67, 54));
        checkOutButton.setForeground(Color.WHITE);

        entryPanel.add(new JLabel("License Plate:"));
        entryPanel.add(licensePlateField);
        entryPanel.add(vipCheck);
        entryPanel.add(checkInButton);
        entryPanel.add(checkOutButton);

        // Status and Analytics
        statusArea = new JTextArea(10, 40);
        statusArea.setEditable(false);
        statusArea.setBackground(Color.WHITE);
        JScrollPane statusScroll = new JScrollPane(statusArea);

        JButton analyticsButton = new JButton("Show Analytics");
        analyticsButton.setBackground(new Color(33, 150, 243));
        analyticsButton.setForeground(Color.WHITE);
        JPanel analyticsPanel = new JPanel(new FlowLayout());
        analyticsPanel.add(analyticsButton);

        // Vehicle List
        listModel = new DefaultListModel<>();
        vehicleList = new JList<>(listModel);
        vehicleList.setBackground(Color.WHITE);
        JScrollPane vehicleScroll = new JScrollPane(vehicleList);
        vehicleScroll.setPreferredSize(new Dimension(550, 150));
        updateVehicleList();

        // Main Layout
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.add(statusScroll, BorderLayout.CENTER);
        centerPanel.add(analyticsPanel, BorderLayout.SOUTH);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        frame.add(new JLabel("Smart Parking System", SwingConstants.CENTER), BorderLayout.NORTH);
        frame.add(entryPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(vehicleScroll, BorderLayout.SOUTH);

        // Event Handlers
        checkInButton.addActionListener(e -> {
            String result = manager.checkInVehicle(licensePlateField.getText(), vipCheck.isSelected());
            statusArea.append(result + "\n");
            updateVehicleList();
            licensePlateField.setText("");
        });

        checkOutButton.addActionListener(e -> {
            String result = manager.checkOutVehicle(licensePlateField.getText());
            statusArea.append(result + "\n");
            updateVehicleList();
            licensePlateField.setText("");
        });

        analyticsButton.addActionListener(e -> {
            AnalyticsEngine analytics = manager.getAnalytics();
            String analyticsText = String.format("Average Stay Time: %.2f minutes\nPeak Hour: %s\n",
                    analytics.getAverageStayTime(), analytics.getPeakHour());
            statusArea.append(analyticsText);
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void updateVehicleList() {
        listModel.clear();
        for (Map.Entry<String, Slot> entry : manager.getVehicleToSlot().entrySet()) {
            Slot slot = entry.getValue();
            String vehicleInfo = String.format("Slot %d: %s (%s)",
                    slot.getSlotId(),
                    slot.getCurrentVehicle().getLicensePlate(),
                    slot.isVip() ? "VIP" : "Regular");
            listModel.addElement(vehicleInfo);
        }
    }

    public static void main(String[] args) {
        new SmartParkingSystem();
    }
}