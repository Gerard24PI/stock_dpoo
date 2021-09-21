package view.main.companydetails;

import model.Company;

import javax.swing.*;
import java.awt.*;

import static view.Typo.*;

public class CompanyInfoPanel extends JPanel {
    String companyName;
    private float shareValue, companyValue;
    private int numShares, userShares;
    private JLabel jlUserShares, jlCompanyValue, jlShareValue;

    public CompanyInfoPanel(Company company, int userShares) {
        companyName = company.getName();
        shareValue = company.getShareValue();
        numShares = company.getnShares();
        this.userShares = userShares;
        companyValue = numShares * shareValue;

        setLayout(new GridLayout(3, 1));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        //Company Value
        JPanel jpCompanyValue = new JPanel(new BorderLayout());
        jpCompanyValue.setBackground(Color.WHITE);
        JLabel jlCompanyValueTitle = new JLabel("TOTAL VALUE:");
        jlCompanyValueTitle.setFont(fontBoldMedium);
        jlCompanyValueTitle.setHorizontalAlignment(SwingConstants.LEFT);
        jpCompanyValue.add(jlCompanyValueTitle, BorderLayout.NORTH);
        jlCompanyValue = new JLabel(df_cashEuro.format(companyValue));
        jlCompanyValue.setFont(fontBoldLarge);
        jlCompanyValue.setHorizontalAlignment(SwingConstants.RIGHT);
        jpCompanyValue.add(jlCompanyValue, BorderLayout.CENTER);
        add(jpCompanyValue);

        //User shares
        JPanel jpUserShares = new JPanel(new BorderLayout());
        jpUserShares.setBackground(Color.WHITE);
        JLabel jlUserSharesTitle = new JLabel("OWNED SHARES:");
        jlUserSharesTitle.setFont(fontBoldMedium);
        jlUserSharesTitle.setHorizontalAlignment(SwingConstants.LEFT);
        jpUserShares.add(jlUserSharesTitle, BorderLayout.NORTH);
        jlUserShares = new JLabel(Integer.toString(userShares));
        jlUserShares.setFont(fontBoldLarge);
        jlUserShares.setHorizontalAlignment(SwingConstants.RIGHT);
        jpUserShares.add(jlUserShares, BorderLayout.CENTER);
        add(jpUserShares);

        //Share value
        JPanel jpShareValue = new JPanel(new BorderLayout());
        jpShareValue.setBackground(Color.WHITE);
        JLabel jlShareValueTitle = new JLabel("SHARE VALUE:");
        jlShareValueTitle.setFont(fontBoldMedium);
        jlShareValueTitle.setHorizontalAlignment(SwingConstants.LEFT);
        jpShareValue.add(jlShareValueTitle, BorderLayout.NORTH);
        jlShareValue = new JLabel(df_cashEuro.format(company.getShareValue()));
        jlShareValue.setFont(fontBoldLarge);
        jlShareValue.setHorizontalAlignment(SwingConstants.RIGHT);
        jpShareValue.add(jlShareValue, BorderLayout.CENTER);
        add(jpShareValue);
    }

    /**
     * Refresh company values with updated share value
     * @param newShareValue updated share value
     */
    public void updateShareValues(float newShareValue) {
        shareValue = newShareValue;
        jlCompanyValue.setText(df_cashEuro.format(shareValue * numShares));
        jlShareValue.setText(df_cashEuro.format(shareValue));
        setVisible(false);
        setVisible(true);
    }

    /**
     * Refresh user shares number with updated number of shares
     * @param numShares updated number of shares
     */
    public void updateUserSharesNumber(int numShares) {
        userShares = numShares;
        jlUserShares.setText(Integer.toString(numShares));
        setVisible(false);
        setVisible(true);
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getUserShares() {
        return userShares;
    }
}
