import javax.swing.*;
import java.awt.*;

// Pharmacy page
class PharmacyPage extends JPanel {

    JButton recordPurchase;
    JButton manageStocks;

    public PharmacyPage() {
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

        manageStocks = new JButton("Manage Stocks");
        manageStocks.setBounds(50,190,180,60 );
        manageStocks.setBackground(new Color(73, 126, 100));
        manageStocks.setForeground(Color.WHITE);
        manageStocks.setOpaque(true);
        manageStocks.setRolloverEnabled(false);
        manageStocks.setFocusPainted(false);
        this.add(manageStocks);

        JLabel alertHeader = new JLabel("Alerts");
        alertHeader.setBounds(50,300,180,60);
        alertHeader.setFont(new Font("Segoe UI",Font.PLAIN,24));
        this.add(alertHeader);


        JScrollPane notificationsContainer;
        notificationsContainer = new JScrollPane();
        notificationsContainer.setBounds(30,350,300,430);
        this.add(notificationsContainer);

        JScrollPane prescriptionsContainer;
        prescriptionsContainer = new JScrollPane();
        prescriptionsContainer.setBounds(450,160,700,622);
        this.add(prescriptionsContainer);

        JLabel prescriptionHeader;
        prescriptionHeader = new JLabel("Incoming Prescriptions");
        prescriptionHeader.setBounds(450,20,400,40);
        prescriptionHeader.setFont(new Font("Segoe UI",Font.PLAIN,24));
        this.add(prescriptionHeader);








    }
}

