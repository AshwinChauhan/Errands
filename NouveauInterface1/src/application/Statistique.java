package application;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

public class Statistique 
{
	@FXML
	private BarChart<String, Integer> barChart;
	
	@FXML
	private CategoryAxis xAxis;
	
	private ObservableList<String> intervalDist=FXCollections.observableArrayList();
	
	@FXML
	private void initialize()
	{
		intervalDist.add("0-20");
		intervalDist.add("20-40");
		intervalDist.add("40-60");
		intervalDist.add("60-80");
		intervalDist.add("80-100");
		intervalDist.add("100-120");
		xAxis.setCategories(intervalDist);
	}
	
	public void SetStats(List<Errands> errands)
	{
		//Compter les errands appartenant a la meme tranche d'age
		
		int[] DisCounter = new int[6]; // Tableau pour les nombres des tranches dde distance
		
		for(Errands e : errands)
		{
			double age = e.getDist();
			
			if(age<=10)
				DisCounter[0]++;
			else
				if(age<=20)
					DisCounter[1]++;
				else
					if(age<=30)
						DisCounter[2]++;
					else
						if(age<=40)
							DisCounter[3]++;
						else
							if(age<=50)
								DisCounter[4]++;
							else
								DisCounter[5]++;
			
		}
		XYChart.Series<String, Integer> series = new XYChart.Series<>();
		series.setName("Distance"); // Légende pour le graphique
		//Création du graphique
		for(int i=0; i<DisCounter.length;i++)
		{
			series.getData().add(new XYChart.Data<>(intervalDist.get(i), DisCounter[i]));
			
		}
		barChart.getData().add(series);
	}

}
