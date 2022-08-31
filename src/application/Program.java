package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
	
		Scanner sc = new Scanner(System.in);

		System.out.print("Entre com o caminho do arquivo: ");
		String path = sc.nextLine();
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			List<Sale> list = new ArrayList<>();
			String line = br.readLine();
			while(line != null) {
				String[] fields = line.split(",");
				list.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2], Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			}
			
			Map<String, Double> sales = new HashMap<>();
			for(Sale sale: list) {
				sales.put(sale.getSeller(), list.stream()
						.filter(s -> s.getSeller().equals(sale.getSeller()))
						.map(s -> s.getTotal())
						.reduce(0.0, Double::sum));
			}
			
			System.out.println("Total de vendas por vendedor:");
			for(String  sell : sales.keySet()) {
				System.out.println("Vendedor: " + sell + " - R$ " + String.format("%.2f", sales.get(sell)));
			}
					
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		sc.close();
	}
}