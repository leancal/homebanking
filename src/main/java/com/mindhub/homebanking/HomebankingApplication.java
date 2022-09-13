package com.mindhub.homebanking;

import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDateTime;
import java.util.List;
import static com.mindhub.homebanking.Models.TransactionType.CREDIT;
import static com.mindhub.homebanking.Models.TransactionType.DEBIT;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.stream.Stream;

@SpringBootApplication //en esto hay que generar un @Bean (una semilla), que lo que hace es generar un initData y en este lugar en especifico quiero este dato (clientes)
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);

	}





	@Bean //se le pasa los repositorios y luego los guarda en la memoria de la aplicacion
	public CommandLineRunner initData(ClientServices  clientServices, AccountServices accountServices, TransactionServices transactionServices, LoanServices loanServices,
									  ClientLoanServices clientLoanServices, CardServices cardServices) { //initData es un metodo que se ejecuta cuando se inicia la aplicacion
		return (args) -> {

			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com",passwordEncoder.encode("123") );
			Client client2 = new Client("Juan", "Perez", "juanp@minhub.com", passwordEncoder.encode("123"));
			Client client3 = new Client("Admin", "admin", "admin@admin.com", passwordEncoder.encode("admin"));


			Account account1 = new Account("VIN001", LocalDateTime.now(), 3000.00, client1, AccountType.SAVINGS,true);
			Account account2 = new Account("VIN002", LocalDateTime.now().plusDays(1), 2000.00, client1, AccountType.CURRENT,true);
			Account account3 = new Account("VIN003", LocalDateTime.now().plusDays(2), 1000.00, client2, AccountType.SAVINGS,true);


			Transaction transaction1 = new Transaction(account1, DEBIT, 100.00, "Services", LocalDateTime.now());
			Transaction transaction2 = new Transaction(account2, CREDIT, 200.00, "Salary", LocalDateTime.now());

			Loan loan1 = new Loan("Mortgage", 5000000.00, List.of(6, 12, 24, 36, 48, 60));
			Loan loan2 = new Loan("Personal", 1000000.00, List.of(6, 12, 24));
			Loan loan3 = new Loan("Vehicle", 300000.00, List.of(6, 12, 24, 36));

			ClientLoan clientLoan1 = new ClientLoan(400000.00, 60, client1, loan1);
			ClientLoan clientLoan2 = new ClientLoan(50000.00, 12, client1, loan2);
			ClientLoan clientLoan3 = new ClientLoan(100000.00, 24, client2, loan2);
			ClientLoan clientLoan4 = new ClientLoan(200000.00, 36, client2, loan3);

			Card card1 = new Card(client1, account1, (client1.getNombre() + " " + client1.getApellido()),"9876 5432 1515 4951", 252, LocalDateTime.now(), LocalDateTime.now(), CardType.DEBIT, CardColor.GOLD, true);
			Card card2 = new Card(client1,account2,(client1.getNombre() + " " + client1.getApellido()), "1234 5678 9126 5325", 233, LocalDateTime.now(), LocalDateTime.now().plusYears(5), CardType.CREDIT, CardColor.TITANIUM, true);
			Card card3 = new Card(client2,account3,(client2.getNombre() + " " + client2.getApellido()), "5151 6515 6124 8953", 422, LocalDateTime.now(), LocalDateTime.now().plusYears(5), CardType.DEBIT, CardColor.SILVER, true);


			account1.addTransaction(transaction1);
			account2.addTransaction(transaction2);

			clientServices.saveClient(client1);
			clientServices.saveClient(client2);
			clientServices.saveClient(client3);

			accountServices.saveAccount(account1);
			accountServices.saveAccount(account2);
			accountServices.saveAccount(account3);

			transactionServices.saveTransaction(transaction1);
			transactionServices.saveTransaction(transaction2);

			loanServices.saveLoan(loan1);
			loanServices.saveLoan(loan2);
			loanServices.saveLoan(loan3);

			clientLoanServices.saveClientLoan(clientLoan1);
			clientLoanServices.saveClientLoan(clientLoan2);
			clientLoanServices.saveClientLoan(clientLoan3);
			clientLoanServices.saveClientLoan(clientLoan4);

			cardServices.saveCard(card1);
			cardServices.saveCard(card2);
			cardServices.saveCard(card3);




			};
	}

		@Autowired
		private PasswordEncoder passwordEncoder;

		public PasswordEncoder getPasswordEncoder() {
			return passwordEncoder;
		}

		public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
			this.passwordEncoder = passwordEncoder;
		}
}

	//en la carpeta resources.static se generan los archivos estaticos (html, css, js, etc)



