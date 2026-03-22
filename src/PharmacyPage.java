import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Pharmacy page
class PharmacyPage extends JPanel {

    JButton recordPurchase;
    JButton manageStocks;
    JPanel dashboardCenter;
    public PharmacyPage(JPanel dashboardCenter) {
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);

        JLabel headerTitle = new JLabel("Pharmacy");
        headerTitle.setBounds(50,5,200,60);
        headerTitle.setForeground(Color.WHITE);
        headerTitle.setFont(new Font("Segoe UI",Font.PLAIN,40));
        this.add(headerTitle);


        recordPurchase = new JButton("Record new Purchase");
        recordPurchase.setBounds(50,110,180,60  );
        recordPurchase.setBackground(new Color(73, 126, 100));
        recordPurchase.setForeground(Color.WHITE);
        recordPurchase.setOpaque(true);
        recordPurchase.setFocusPainted(false);
        recordPurchase.setRolloverEnabled(false);
        this.add(recordPurchase);


        recordPurchase.addActionListener(e ->{

            CardLayout cl = (CardLayout) dashboardCenter.getLayout();
            cl.show(dashboardCenter,"PharmacyPurchase" ); // class name
        });



        manageStocks = new JButton("Manage Stocks");
        manageStocks.setBounds(50,190,180,60 );
        manageStocks.setBackground(new Color(73, 126, 100));
        manageStocks.setForeground(Color.WHITE);
        manageStocks.setOpaque(true);
        manageStocks.setRolloverEnabled(false);
        manageStocks.setFocusPainted(false);
        this.add(manageStocks);

        manageStocks.addActionListener(e ->{
            CardLayout cl = (CardLayout) dashboardCenter.getLayout();
            cl.show(dashboardCenter,"PharmacyStocks");
        });

        JLabel alertHeader = new JLabel("Alerts");
        alertHeader.setBounds(50,300,180,60);
        alertHeader.setFont(new Font("Segoe UI",Font.PLAIN,24));
        this.add(alertHeader);


        JScrollPane notificationsContainer;
        notificationsContainer = new JScrollPane();
        notificationsContainer.setBounds(30,350,400,430);
        this.add(notificationsContainer);

        JScrollPane prescriptionsContainer;
        prescriptionsContainer = new JScrollPane();
        prescriptionsContainer.setBounds(500,212,700,567);
        this.add(prescriptionsContainer);

        JLabel prescriptionHeader;
        prescriptionHeader = new JLabel("Prescriptions");
        prescriptionHeader.setBounds(500,20,400,40);
        prescriptionHeader.setFont(new Font("Segoe UI",Font.BOLD,24));
        prescriptionHeader.setForeground(Color.WHITE);
        this.add(prescriptionHeader);

        JPanel filtersPanel = new JPanel();
        filtersPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        filtersPanel.setBounds(500,80,590,30);
        filtersPanel.setBackground(Color.LIGHT_GRAY);
        this.add(filtersPanel);

        JPanel filterPanel2 = new JPanel();
        filterPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
        filterPanel2.setBackground(Color.LIGHT_GRAY);
        filterPanel2.setBounds(500,120,590,30);
        this.add(filterPanel2);





        JLabel id = new JLabel("ID ");
        JTextField idFilter = new JTextField();
        idFilter.setPreferredSize(new Dimension(90,20));

        JLabel patientName = new JLabel("  Patient Name ");
        JTextField patientNameFilter = new JTextField();
        patientNameFilter.setPreferredSize(new Dimension(140,20));

        JLabel doctorName = new JLabel("  Doctor name ");
        JTextField doctorFilter = new JTextField();
        doctorFilter.setPreferredSize(new Dimension(140,20));
        filtersPanel.add(doctorFilter);


        JLabel date = new JLabel("Date ");
        JTextField dateFilter = new JTextField();
        dateFilter.setPreferredSize(new Dimension(130,20));

        JLabel time = new JLabel("  Time ");
        JTextField timeFilter = new JTextField();
        timeFilter.setPreferredSize(new Dimension(130,20));
        filterPanel2.add(date);
        filterPanel2.add(dateFilter);
        filterPanel2.add(time);
        filterPanel2.add(timeFilter);

        JLabel statusLabel = new JLabel("   Status ");
        filterPanel2.add(statusLabel);


        String [] statuses = {"Pending","Cleared"};
        JComboBox <String> status = new JComboBox<>(statuses);
        status.setPreferredSize(new Dimension(160,20));
        status.setSelectedItem("Pending");
        filterPanel2.add(status);




        JButton showAll = new JButton("Show all");
        showAll.setBounds(1090,85,100,30 );
        showAll.setBackground(new Color(99, 154, 124, 255));
        showAll.setRolloverEnabled(false);
        showAll.setFocusPainted(false);
        this.add(showAll);

        JButton search = new JButton("Search");
        search.setBounds(1090, 120 ,100,30);
        search.setBackground(new Color(99, 154, 124, 255));
        search.setFocusPainted(false);
        search.setRolloverEnabled(false);
        this.add(search);





        filtersPanel.add(id);
        filtersPanel.add(idFilter);
        filtersPanel.add(patientName);
        filtersPanel.add(patientNameFilter);
        filtersPanel.add(doctorName);
        filtersPanel.add(doctorFilter);


















    }

}

