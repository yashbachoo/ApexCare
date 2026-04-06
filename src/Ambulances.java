import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class Ambulances extends JPanel {
    private JTable ambulanceTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> statusFilter;
    private JTextField searchField;
    private JPanel statsPanel;
    private JLabel availableCountLabel;
    private JLabel enRouteCountLabel;
    private JLabel onSceneCountLabel;
    private JLabel outOfServiceCountLabel;

    // Modern color palette
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private final Color WARNING_COLOR = new Color(241, 196, 15);
    private final Color DANGER_COLOR = new Color(231, 76, 60);
    private final Color INFO_COLOR = new Color(52, 152, 219);
    private final Color DARK_BG = new Color(44, 62, 80);
    private final Color CARD_BG = new Color(255, 255, 255);
    private final Color SIDEBAR_BG = new Color(248, 249, 250);
    private final Color TEXT_MUTED = new Color(127, 140, 141);

    public Ambulances() {
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(240, 242, 245));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Main container with padding
        JPanel mainContainer = new JPanel(new BorderLayout(20, 20));
        mainContainer.setOpaque(false);

        mainContainer.add(createHeaderPanel(), BorderLayout.NORTH);
        mainContainer.add(createStatsPanel(), BorderLayout.CENTER);
        mainContainer.add(createContentPanel(), BorderLayout.SOUTH);

        add(mainContainer, BorderLayout.CENTER);

        loadSampleData();
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Title section
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Ambulance Dispatch Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(44, 62, 80));

        JLabel subtitleLabel = new JLabel("Real-time fleet tracking and deployment");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_MUTED);

        JPanel titleTextPanel = new JPanel(new GridLayout(2, 1));
        titleTextPanel.setOpaque(false);
        titleTextPanel.add(titleLabel);
        titleTextPanel.add(subtitleLabel);
        titlePanel.add(titleTextPanel, BorderLayout.WEST);

        // Current time and date
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        timePanel.setOpaque(false);

        JLabel dateLabel = new JLabel(LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE, MMM d, yyyy")));
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dateLabel.setForeground(TEXT_MUTED);

        JLabel timeLabel = new JLabel(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")));
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        timeLabel.setForeground(PRIMARY_COLOR);

        timePanel.add(dateLabel);
        timePanel.add(Box.createHorizontalStrut(10));
        timePanel.add(timeLabel);

        // Update time every second
//        Timer timer = new Timer(1000, e -> {
//            timeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")));
//        });
//        timer.start();

        titlePanel.add(timePanel, BorderLayout.EAST);
        header.add(titlePanel, BorderLayout.NORTH);

        return header;
    }

    private JPanel createStatsPanel() {
        statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        availableCountLabel = createStatCard("Available Units", "0", SUCCESS_COLOR);
        enRouteCountLabel = createStatCard("En Route", "0", INFO_COLOR);
        onSceneCountLabel = createStatCard("On Scene", "0", WARNING_COLOR);
        outOfServiceCountLabel = createStatCard("Out of Service", "0", DANGER_COLOR);

        return statsPanel;
    }

    private JLabel createStatCard(String title, String value, Color accentColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD_BG);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
                g2.dispose();
            }
        };
        card.setLayout(new BorderLayout());
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(TEXT_MUTED);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(accentColor);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel);
        textPanel.add(valueLabel);

        card.add(textPanel, BorderLayout.WEST);

        return valueLabel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout(0, 15));
        contentPanel.setOpaque(false);

        contentPanel.add(createControlPanel(), BorderLayout.NORTH);
        contentPanel.add(createTablePanel(), BorderLayout.CENTER);
        contentPanel.add(createActionPanel(), BorderLayout.SOUTH);

        return contentPanel;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new BorderLayout(15, 0));
        controlPanel.setOpaque(false);

        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);

        searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        searchField.putClientProperty("JTextField.placeholderText", "Search by unit ID, driver, or location...");
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
        });

        JPanel searchIconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchIconPanel.setOpaque(false);
        searchIconPanel.add(searchField);
        searchPanel.add(searchIconPanel, BorderLayout.WEST);

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        filterPanel.setOpaque(false);

        JLabel filterLabel = new JLabel("Status:");
        filterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        filterLabel.setForeground(TEXT_MUTED);

        statusFilter = new JComboBox<>(new String[]{"All Status", "Available", "En Route", "On Scene", "Out of Service"});
        statusFilter.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statusFilter.setBackground(CARD_BG);
        statusFilter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(4, 12, 4, 12)
        ));
        statusFilter.addActionListener(e -> filterTable());

        // Add ambulance button
        JButton addButton = createStyledButton("+ New Dispatch", PRIMARY_COLOR);
        addButton.addActionListener(e -> showAddAmbulanceDialog());

        filterPanel.add(filterLabel);
        filterPanel.add(statusFilter);
        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(addButton);

        controlPanel.add(searchPanel, BorderLayout.CENTER);
        controlPanel.add(filterPanel, BorderLayout.EAST);

        return controlPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);

        // Create table model
        String[] columns = {"Unit ID", "Driver", "Status", "Location", "ETA", "Last Updated", "Actions"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only actions column is editable
            }
        };

        ambulanceTable = new JTable(tableModel);
        ambulanceTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        ambulanceTable.setRowHeight(50);
        ambulanceTable.setIntercellSpacing(new Dimension(10, 5));
        ambulanceTable.setShowGrid(false);
        ambulanceTable.setSelectionBackground(new Color(41, 128, 185, 50));

        // Custom renderer for status column
        ambulanceTable.getColumnModel().getColumn(2).setCellRenderer(new StatusCellRenderer());
        ambulanceTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonCellRenderer());
        ambulanceTable.getColumnModel().getColumn(6).setCellEditor(new ButtonCellEditor());

        // Set column widths
        ambulanceTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        ambulanceTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        ambulanceTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        ambulanceTable.getColumnModel().getColumn(3).setPreferredWidth(180);
        ambulanceTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        ambulanceTable.getColumnModel().getColumn(5).setPreferredWidth(120);
        ambulanceTable.getColumnModel().getColumn(6).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(ambulanceTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(CARD_BG);
        scrollPane.setOpaque(false);

        // Customize scroll pane
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder()
        ));

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actionPanel.setOpaque(false);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JButton refreshButton = createStyledButton("Refresh Status", new Color(108, 117, 125));
        refreshButton.addActionListener(e -> refreshData());

        JButton exportButton = createStyledButton("Export Report", new Color(23, 162, 184));
        exportButton.addActionListener(e -> exportReport());

        actionPanel.add(refreshButton);
        actionPanel.add(exportButton);

        return actionPanel;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bgColor.brighter());
                } else {
                    g2.setColor(bgColor);
                }
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void loadSampleData() {
        Object[][] data = {
                {"AMB-001", "John Smith", "Available", "Station A - Downtown", "Ready", "Just now", "Actions"},
                {"AMB-002", "Sarah Johnson", "En Route", "Main St & 5th Ave", "3 min", "2 min ago", "Actions"},
                {"AMB-003", "Michael Brown", "On Scene", "123 Emergency Rd", "On scene", "5 min ago", "Actions"},
                {"AMB-004", "Emily Davis", "Available", "Station B - Northside", "Ready", "Just now", "Actions"},
                {"AMB-005", "David Wilson", "Out of Service", "Maintenance Bay 2", "N/A", "15 min ago", "Actions"},
                {"AMB-006", "Lisa Anderson", "En Route", "Highway 101, Mile 42", "8 min", "1 min ago", "Actions"},
                {"AMB-007", "Robert Taylor", "On Scene", "Community Hospital", "On scene", "3 min ago", "Actions"},
                {"AMB-008", "Maria Garcia", "Available", "Station C - Eastside", "Ready", "2 min ago", "Actions"}
        };

        for (Object[] row : data) {
            tableModel.addRow(row);
        }

        updateStats();
    }

    private void updateStats() {
        int available = 0, enRoute = 0, onScene = 0, outOfService = 0;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String status = (String) tableModel.getValueAt(i, 2);
            switch (status) {
                case "Available": available++; break;
                case "En Route": enRoute++; break;
                case "On Scene": onScene++; break;
                case "Out of Service": outOfService++; break;
            }
        }

        availableCountLabel.setText(String.valueOf(available));
        enRouteCountLabel.setText(String.valueOf(enRoute));
        onSceneCountLabel.setText(String.valueOf(onScene));
        outOfServiceCountLabel.setText(String.valueOf(outOfService));
    }

    private void filterTable() {
        String searchText = searchField.getText().toLowerCase();
        String selectedStatus = (String) statusFilter.getSelectedItem();

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        ambulanceTable.setRowSorter(sorter);

        RowFilter<DefaultTableModel, Object> filter = new RowFilter<DefaultTableModel, Object>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                boolean matchesSearch = true;
                boolean matchesStatus = true;

                if (!searchText.isEmpty()) {
                    String unitId = entry.getStringValue(0).toLowerCase();
                    String driver = entry.getStringValue(1).toLowerCase();
                    String location = entry.getStringValue(3).toLowerCase();
                    matchesSearch = unitId.contains(searchText) || driver.contains(searchText) || location.contains(searchText);
                }

                if (!selectedStatus.equals("All Status")) {
                    String status = entry.getStringValue(2);
                    matchesStatus = status.equals(selectedStatus);
                }

                return matchesSearch && matchesStatus;
            }
        };

        sorter.setRowFilter(filter);
    }

    private void showAddAmbulanceDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "New Ambulance Dispatch", true);
        dialog.setSize(450, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(CARD_BG);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Form fields
        JTextField unitField = new JTextField();
        JTextField driverField = new JTextField();
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Available", "En Route", "On Scene", "Out of Service"});
        JTextField locationField = new JTextField();
        JTextField etaField = new JTextField();
        JTextArea notesArea = new JTextArea(3, 20);
        notesArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        notesArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        JScrollPane notesScroll = new JScrollPane(notesArea);

        addFormRow(formPanel, gbc, "Unit ID:", unitField, 0);
        addFormRow(formPanel, gbc, "Driver Name:", driverField, 1);
        addFormRow(formPanel, gbc, "Status:", statusCombo, 2);
        addFormRow(formPanel, gbc, "Location:", locationField, 3);
        addFormRow(formPanel, gbc, "ETA:", etaField, 4);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.3;
        JLabel notesLabel = new JLabel("Notes:");
        notesLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        formPanel.add(notesLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(notesScroll, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(CARD_BG);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dialog.dispose());

        JButton saveBtn = createStyledButton("Create Dispatch", PRIMARY_COLOR);
        saveBtn.addActionListener(e -> {
            if (!unitField.getText().trim().isEmpty() && !driverField.getText().trim().isEmpty()) {
                tableModel.addRow(new Object[]{
                        unitField.getText().trim(),
                        driverField.getText().trim(),
                        statusCombo.getSelectedItem(),
                        locationField.getText().trim().isEmpty() ? "Not specified" : locationField.getText().trim(),
                        etaField.getText().trim().isEmpty() ? "Pending" : etaField.getText().trim(),
                        "Just now",
                        "Actions"
                });
                updateStats();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Please enter Unit ID and Driver Name", "Required Fields", JOptionPane.WARNING_MESSAGE);
            }
        });

        buttonPanel.add(cancelBtn);
        buttonPanel.add(saveBtn);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, String label, JComponent field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        panel.add(field, gbc);
    }

    private void refreshData() {
        // Simulate refreshing data
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String currentStatus = (String) tableModel.getValueAt(i, 2);
            // Randomly update some statuses for demo
            if (Math.random() > 0.7 && !currentStatus.equals("Out of Service")) {
                String[] statuses = {"Available", "En Route", "On Scene"};
                String newStatus = statuses[new Random().nextInt(statuses.length)];
                tableModel.setValueAt(newStatus, i, 2);
                tableModel.setValueAt("Just now", i, 5);
            }
        }
        updateStats();
        JOptionPane.showMessageDialog(this, "Status refreshed successfully!", "Refresh", JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportReport() {
        StringBuilder report = new StringBuilder();
        report.append("Ambulance Dispatch Report\n");
        report.append("Generated: ").append(LocalDateTime.now()).append("\n");
        report.append("=".repeat(50)).append("\n\n");

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            report.append("Unit: ").append(tableModel.getValueAt(i, 0)).append("\n");
            report.append("Driver: ").append(tableModel.getValueAt(i, 1)).append("\n");
            report.append("Status: ").append(tableModel.getValueAt(i, 2)).append("\n");
            report.append("Location: ").append(tableModel.getValueAt(i, 3)).append("\n");
            report.append("ETA: ").append(tableModel.getValueAt(i, 4)).append("\n");
            report.append("Last Updated: ").append(tableModel.getValueAt(i, 5)).append("\n");
            report.append("-".repeat(30)).append("\n");
        }

        JTextArea textArea = new JTextArea(report.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "Export Report", JOptionPane.INFORMATION_MESSAGE);
    }

    // Custom cell renderer for status column
    class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String status = (String) value;

            JLabel label = (JLabel) c;
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));

            Color bgColor;
            switch (status) {
                case "Available": bgColor = SUCCESS_COLOR; break;
                case "En Route": bgColor = INFO_COLOR; break;
                case "On Scene": bgColor = WARNING_COLOR; break;
                case "Out of Service": bgColor = DANGER_COLOR; break;
                default: bgColor = TEXT_MUTED;
            }

            label.setBackground(bgColor);
            label.setForeground(Color.WHITE);
            label.setOpaque(true);
            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            return label;
        }
    }

    // Button cell renderer and editor for actions column
    class ButtonCellRenderer extends JPanel implements TableCellRenderer {
        private JButton viewButton;
        private JButton editButton;

        public ButtonCellRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            setOpaque(true);
            setBackground(CARD_BG);

            viewButton = new JButton("View");
            viewButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            viewButton.setBackground(PRIMARY_COLOR);
            viewButton.setForeground(Color.WHITE);
            viewButton.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
            viewButton.setFocusPainted(false);

            editButton = new JButton("Edit");
            editButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            editButton.setBackground(new Color(108, 117, 125));
            editButton.setForeground(Color.WHITE);
            editButton.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
            editButton.setFocusPainted(false);

            add(viewButton);
            add(editButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setBackground(isSelected ? new Color(41, 128, 185, 50) : CARD_BG);
            return this;
        }
    }

    class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        private JButton viewButton;
        private JButton editButton;
        private int currentRow;

        public ButtonCellEditor() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            panel.setOpaque(true);
            panel.setBackground(CARD_BG);

            viewButton = new JButton("View");
            viewButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            viewButton.setBackground(PRIMARY_COLOR);
            viewButton.setForeground(Color.WHITE);
            viewButton.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
            viewButton.setFocusPainted(false);
            viewButton.addActionListener(e -> {
                viewAmbulanceDetails(currentRow);
                fireEditingStopped();
            });

            editButton = new JButton("Edit");
            editButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            editButton.setBackground(new Color(108, 117, 125));
            editButton.setForeground(Color.WHITE);
            editButton.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
            editButton.setFocusPainted(false);
            editButton.addActionListener(e -> {
                editAmbulanceDetails(currentRow);
                fireEditingStopped();
            });

            panel.add(viewButton);
            panel.add(editButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentRow = row;
            panel.setBackground(table.getSelectionBackground());
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "Actions";
        }
    }

    private void viewAmbulanceDetails(int row) {
        String unitId = (String) tableModel.getValueAt(row, 0);
        String driver = (String) tableModel.getValueAt(row, 1);
        String status = (String) tableModel.getValueAt(row, 2);
        String location = (String) tableModel.getValueAt(row, 3);
        String eta = (String) tableModel.getValueAt(row, 4);
        String lastUpdated = (String) tableModel.getValueAt(row, 5);

        String details = String.format("""
            Ambulance Details
            ═══════════════════════
            
            Unit ID: %s
            Driver: %s
            Status: %s
            Current Location: %s
            Estimated Arrival: %s
            Last Updated: %s
            """, unitId, driver, status, location, eta, lastUpdated);

        JOptionPane.showMessageDialog(this, details, "Ambulance Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editAmbulanceDetails(int row) {
        // Get current values
        String currentUnitId = (String) tableModel.getValueAt(row, 0);
        String currentDriver = (String) tableModel.getValueAt(row, 1);
        String currentStatus = (String) tableModel.getValueAt(row, 2);
        String currentLocation = (String) tableModel.getValueAt(row, 3);
        String currentEta = (String) tableModel.getValueAt(row, 4);

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Ambulance - " + currentUnitId, true);
        dialog.setSize(450, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(CARD_BG);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Form fields with current values
        JTextField unitField = new JTextField(currentUnitId);
        JTextField driverField = new JTextField(currentDriver);
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Available", "En Route", "On Scene", "Out of Service"});
        statusCombo.setSelectedItem(currentStatus);
        JTextField locationField = new JTextField(currentLocation);
        JTextField etaField = new JTextField(currentEta);
        JTextArea notesArea = new JTextArea(3, 20);
        notesArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        notesArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        JScrollPane notesScroll = new JScrollPane(notesArea);

        addFormRow(formPanel, gbc, "Unit ID:", unitField, 0);
        addFormRow(formPanel, gbc, "Driver Name:", driverField, 1);
        addFormRow(formPanel, gbc, "Status:", statusCombo, 2);
        addFormRow(formPanel, gbc, "Location:", locationField, 3);
        addFormRow(formPanel, gbc, "ETA:", etaField, 4);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.3;
        JLabel notesLabel = new JLabel("Notes:");
        notesLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        formPanel.add(notesLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(notesScroll, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(CARD_BG);

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBackground(DANGER_COLOR);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        deleteBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        deleteBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(dialog,
                    "Are you sure you want to delete " + currentUnitId + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                tableModel.removeRow(row);
                updateStats();
                dialog.dispose();
            }
        });

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dialog.dispose());

        JButton saveBtn = createStyledButton("Save Changes", PRIMARY_COLOR);
        saveBtn.addActionListener(e -> {
            if (!unitField.getText().trim().isEmpty() && !driverField.getText().trim().isEmpty()) {
                tableModel.setValueAt(unitField.getText().trim(), row, 0);
                tableModel.setValueAt(driverField.getText().trim(), row, 1);
                tableModel.setValueAt(statusCombo.getSelectedItem(), row, 2);
                tableModel.setValueAt(locationField.getText().trim().isEmpty() ? "Not specified" : locationField.getText().trim(), row, 3);
                tableModel.setValueAt(etaField.getText().trim().isEmpty() ? "Pending" : etaField.getText().trim(), row, 4);
                tableModel.setValueAt("Just now", row, 5);
                updateStats();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Please enter Unit ID and Driver Name", "Required Fields", JOptionPane.WARNING_MESSAGE);
            }
        });

        buttonPanel.add(deleteBtn);
        buttonPanel.add(cancelBtn);
        buttonPanel.add(saveBtn);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}