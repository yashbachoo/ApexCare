import javax.swing.*;
import java.awt.*;

public class PharmacyStocks extends JPanel{
    JPanel dashboardCenter;
    public PharmacyStocks(JPanel dashboardCenter){
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);

        JLabel header = new JLabel("Pharmacy Stocks");
        header.setBounds(50,20,250,40);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI",Font.BOLD,26));

        JScrollPane container = new JScrollPane();

        container.setBounds(50,150,950,650);

        JPanel filters = new JPanel();
        filters.setBounds(50,110,950,300);
        filters.setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
        filters.setBackground(Color.LIGHT_GRAY);

        JLabel id = new JLabel("ID ");
        JTextField idFilter = new JTextField();
        idFilter.setPreferredSize(new Dimension(75  ,25));

        JLabel name = new JLabel("  name ");
        JTextField nameFilter = new JTextField();
        nameFilter.setPreferredSize(new Dimension(115,25));

        String[] avail = {"all","in stock", "nearly out of stock ", "out of stock"};
        JComboBox <String> availability = new JComboBox<>(avail);

        JButton search = new JButton("Search");
        search.setFocusPainted(false);
        search.setRolloverEnabled(false);
        search.setBackground(new Color(73, 126, 100));
        search.setForeground(Color.WHITE);

        JButton showAll = new JButton("Show all");
        showAll.setRolloverEnabled(false);
        showAll.setFocusPainted(false);
        showAll.setBackground(new Color(73, 126, 100));
        showAll.setForeground(Color.WHITE);

        JButton addStock = new JButton("+ Add New Stock");
        addStock.setRolloverEnabled(false);
        addStock.setFocusPainted(false);
        addStock.setBackground(new Color(73, 126, 100));
        addStock.setForeground(Color.WHITE);

        addStock.addActionListener(e -> {

            CardLayout cl = (CardLayout) dashboardCenter.getLayout();
            cl.show(dashboardCenter,"AddPharmacyStockPage");

        });





        filters.add(id);
        filters.add(idFilter);
        filters.add(name);
        filters.add(nameFilter);
        filters.add(availability);
        filters.add(search);
        filters.add(showAll);
        filters.add(addStock);




        this.add(header);
        this.add(container);
        this.add(filters);
    }
}
