package view.usershares;

import controller.ServerController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.LinkedList;

import static view.Typo.df_cashEuro;
import static view.Typo.fontBoldMedium;

public class BuyAction extends JPanel {

    private JComboBox<String> jcUsers;
    private JButton jbsearch;
    private LinkedList<String> users;
    private JTable jtCompanies;
    private DefaultTableModel model;


    public BuyAction(LinkedList<String> users){
        this.users = users;
        this.jcUsers = new JComboBox<>();

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20,30,30,30));
        setBackground(Color.WHITE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));

        JLabel jltitle = new JLabel("Bought shares by:");
        jltitle.setBorder(new EmptyBorder(0,0,0,5));
        jltitle.setFont(fontBoldMedium);
        header.add(jltitle, BorderLayout.WEST);

        JPanel jpUserSelect = new JPanel();
        jpUserSelect.setBackground(Color.WHITE);
        jpUserSelect.setBorder(new EmptyBorder(0,100,0,0));
        for (String user : users) {
            jcUsers.addItem(user);
        }
        jcUsers.setBackground(Color.BLACK);
        jcUsers.setForeground(Color.WHITE);
        jpUserSelect.add(jcUsers, BorderLayout.CENTER);

        jbsearch = new JButton("Select");
        jbsearch.setForeground(Color.WHITE);
        jbsearch.setBackground(Color.BLACK);
        jpUserSelect.add(jbsearch, BorderLayout.EAST);
        header.add(jpUserSelect, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        model = new DefaultTableModel();
        jtCompanies = new JTable(model);
        jtCompanies.setBackground(Color.WHITE);
        jtCompanies.setRowHeight(30);
        jtCompanies.setFont(fontBoldMedium);
        JTableHeader tableHeader = jtCompanies.getTableHeader();
        tableHeader.setBackground(Color.BLACK);
        tableHeader.setForeground(Color.white);
        tableHeader.setFont(fontBoldMedium);
        jtCompanies.setColumnSelectionAllowed(false);
        jtCompanies.setRowSelectionAllowed(false);
        model.addColumn("Company");
        model.addColumn("Shares");
        model.addColumn("Share Price");
        model.addColumn("Total Value");
        JScrollPane spCompanies = new JScrollPane(jtCompanies);
        spCompanies.setBackground(Color.WHITE);
        add(spCompanies, BorderLayout.SOUTH);

        setVisible(true);


    }

    public void registerController(ServerController sc) {
        jbsearch.setActionCommand(ServerController.SEARCH_COMPANY);
        jbsearch.addActionListener(sc);
    }


    public void createTable(LinkedList<String> companies){
        if (jtCompanies.getRowCount()!=0){
            model.setRowCount(0);
        }
        for (int i = 0; i < companies.size(); i++) {
            float d = Integer.parseInt(companies.get(i+1))*Float.parseFloat(companies.get(i+2));
            model.addRow(new Object[]{companies.get(i),companies.get(i+1),companies.get(i+2),d});
            i=i+2;
        }
        setVisible(true);
    }

    public Object getComboBoxItem(){return jcUsers.getSelectedItem();}

    /**
     * Refresh and updates share value and total value of company shares of a user regarding the updated information
     * @param companyName name of company row to be updaed
     * @param shareValue updated share value
     */
    public void updateCompanySharePrice(String companyName, float shareValue) {
        for (int i = 0; i < model.getRowCount(); i++) {
            String value = jtCompanies.getModel().getValueAt(i, 0).toString();
            if (value.equals(companyName)){
                int share = Integer.parseInt(model.getValueAt(i,1).toString());
                float totalValue = shareValue*share;
                model.setValueAt(shareValue,i,2);
                model.setValueAt(totalValue,i,3);
            }
        }
    }

    /**
     * Refresh and updates table of company shares of a user regarding the updated information
     * @param companyName name of company row to be updated
     * @param share updated number of shares
     * @param shareValue updated share value
     */
    public void updateCompanyNumberShare(String companyName, int share, float shareValue) {
        boolean found = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            String value = jtCompanies.getModel().getValueAt(i, 0).toString();
            if (value.equals(companyName)){
                found = true;
                if (share > 0) {
                    model.setValueAt(share, i, 1);
                }
                else {
                    model.removeRow(i);

                }
            }
        }
        if (!found) {
            float totalValue = share * shareValue;
            model.addRow(new Object[]{companyName,String.valueOf(share),String.valueOf(shareValue),String.valueOf(totalValue)});
        }

    }
}
