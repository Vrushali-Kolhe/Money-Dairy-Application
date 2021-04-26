package com.firstapp.moneydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {
    private PieChart report;

    DatabaseHelper db;
    String title = "title";
    String desc = "desc";
    String income = "income";
    String health = "health";
    String food = "food";
    String enter = "entertainment";
    String shop = "shopping";
    String edu = "education";
    String travel = "travel";
    String other = "others";
    String date = "Wed Mar 27 08:22:02 IST 2015";

    TransactionModel a = new TransactionModel(1,title,date,desc,700,income);
    TransactionModel b = new TransactionModel(2,title,date,desc,100,health);
    TransactionModel c = new TransactionModel(3,title,date,desc,100,food);
    TransactionModel d = new TransactionModel(4,title,date,desc,100,enter);
    TransactionModel e = new TransactionModel(5,title,date,desc,100,shop);
    TransactionModel f = new TransactionModel(6,title,date,desc,100,edu);
    TransactionModel g = new TransactionModel(7,title,date,desc,100,travel);
    TransactionModel h = new TransactionModel(8,title,date,desc,100,other);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        report = (PieChart) findViewById(R.id.piechart_report);
        setUpPieChart();
        loadPieChartData();
    }

    private void setUpPieChart(){
        report.setDrawHoleEnabled(true);
        report.setUsePercentValues(true);
        report.setEntryLabelTextSize(12);
        report.setEntryLabelColor(Color.BLACK);
        report.setCenterText("Spending by Category");
        report.setCenterTextSize(24);
        report.getDescription().setEnabled(false);

        Legend l = report.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartData(){
        Float Income = 0.0f, Health = 0.0f,Food = 0.0f,Shopping = 0.0f,Entertainment = 0.0f,Education = 0.0f,Travel = 0.0f,Others = 0.0f;
        Float Total = 0.0f;

        ArrayList<TransactionModel> t = new ArrayList<TransactionModel>();
        t.add(a);
        t.add(b);
        t.add(c);
        t.add(d);
        t.add(e);
        t.add(f);
        t.add(g);
        t.add(h);

        Total = Total + Health + Food + Shopping + Entertainment + Education + Travel + Others;
        Health = Health/Total;
        Food = Food/Total;
        Shopping = Shopping/Total;
        Entertainment = Entertainment/Total;
        Education = Education/Total;
        Travel = Travel/Total;
        Others = Others/Total;


        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(Health, "Health"));
        entries.add(new PieEntry(Food, "Food"));
        entries.add(new PieEntry(Shopping, "Shopping"));
        entries.add(new PieEntry(Entertainment, "Entertainment"));
        entries.add(new PieEntry(Education, "Education"));
        entries.add(new PieEntry(Travel, "Travel"));
        entries.add(new PieEntry(Others, "Others"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(report));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        report.setData(data);
        report.invalidate();

        report.animateY(1400, Easing.EaseInOutQuad);
    }
}

/*        ArrayList<TransactionModel> t = new ArrayList<TransactionModel>();
        t.add(a);
        t.add(b);
        t.add(c);
        t.add(d);
        t.add(e);
        t.add(f);
        t.add(g);
        t.add(h);
*/

/*        db = new DatabaseHelper(this);

        ArrayList<TransactionModel> t = db.getAllData();

        for (int count = 0; count < t.size(); count++){
            //Toast.makeText(ReportActivity.this,(int) t.get(count).getAmount(), Toast.LENGTH_SHORT).show();
            if(t.get(count).getCategory()=="Income")
                Income = Income +  t.get(count).getAmount();
            if(t.get(count).getCategory()=="Health")
                Health = Health + t.get(count).getAmount();
            if(t.get(count).getCategory()=="Food")
                Food = Food + t.get(count).getAmount();
            if(t.get(count).getCategory()=="Shopping")
                Shopping = Shopping + t.get(count).getAmount();
            if(t.get(count).getCategory()=="Entertainment")
                Entertainment = Entertainment + t.get(count).getAmount();
            if(t.get(count).getCategory()=="Education")
                Education = Education + t.get(count).getAmount();
            if(t.get(count).getCategory()=="Travel")
                Travel = Travel + t.get(count).getAmount();
            if(t.get(count).getCategory()=="Others")
                Others = Others + t.get(count).getAmount();
        }

*/