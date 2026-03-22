import javax.swing.*;
import java.awt.*;

public class PharmacyPurchase extends JPanel{
    public PharmacyPurchase(){
        this.setLayout(null);
        this.setBackground(Color.LIGHT_GRAY);

        JPanel cart = new JPanel();
        cart.setBounds(50,110,500,600);

        JPanel idPane = new JPanel();
        idPane.setBounds(50,50,500,30);


        JPanel totalPane = new JPanel();
        totalPane.setBounds(50,725,500,50);

        JPanel itemSearch = new JPanel();
        itemSearch.setBounds(570,110,500,500);

        JLabel search = new JLabel(" Search Item ");
        search.setBounds(570,50,82,30);
        search.setBackground(new Color(140, 193, 163));
        search.setForeground(Color.WHITE);
        search.setOpaque(true);


        JTextField searchBar = new JTextField();
        searchBar.setBounds(650,50,270,30);

        JButton searchButton = new JButton("search");
        searchButton.setBounds(940,50,100,30);
        searchButton.setBackground(new Color(73, 126, 100));
        searchButton.setFocusPainted(false);
        searchButton.setRolloverEnabled(false);
        searchButton.setForeground(Color.white);

        JButton cancel = new JButton("Cancel");
        JButton checkOut = new JButton("Check Out ");

        cancel.setBounds(950,650,120,40);
        cancel.setRolloverEnabled(false);
        cancel.setFocusPainted(false);
        cancel.setBackground(new Color(232, 89, 89));
        cancel.setForeground(Color.WHITE);

        checkOut.setBounds(950,715,120,40);
        checkOut.setRolloverEnabled(false);
        checkOut.setFocusPainted(false);
        checkOut.setForeground(Color.WHITE);
        checkOut.setBackground(new Color(73, 126, 100));


        String [] payOptions ={"Cash","Card"};

        JComboBox <String> payMethods = new JComboBox<>(payOptions);
        payMethods.setBounds(570,650,250,30);


        this.add(idPane);
        this.add(cart);
        this.add(totalPane);
        this.add(search);
        this.add(itemSearch);
        this.add(searchBar);
        this.add(searchButton);
        this.add(cancel);
        this.add(checkOut);
        this.add(payMethods);


    }
}
