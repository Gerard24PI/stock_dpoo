package view.topten;

import model.Company;
import model.db.dao.CompanyDAO;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

//https://stackoverflow.com/questions/48775857/displaying-axes-and-labels-bar-chart-java

public class TopChart extends JPanel{

    private static ArrayList<Company> topCompanies;
    private BarPanel jpChart;

    public TopChart(){
        topCompanies = revertList(CompanyDAO.getTopTen());

        setPreferredSize(new Dimension(750, 500));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        setBackground(Color.WHITE);

        jpChart = new BarPanel(topCompanies);
        jpChart.setBackground(Color.WHITE);
        jpChart.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(jpChart, BorderLayout.CENTER);
    }

    private static ArrayList<Company> revertList(ArrayList<Company> comps){
        ArrayList<Company> revArrayList = new ArrayList<>();
        for (int i = comps.size() - 1; i >= 0; i--) {
            revArrayList.add(comps.get(i));
        }
        return revArrayList;
    }

    public void setTopInfo(){
        topCompanies = revertList(CompanyDAO.getTopTen());
        jpChart.updateChart(topCompanies);
        repaint();
    }
}

