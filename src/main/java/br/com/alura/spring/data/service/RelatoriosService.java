package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatoriosService {

	private final FuncionarioRepository funcionarioRepository;
	
	private Boolean system = true;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public RelatoriosService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	} 
	
	public void inicial(Scanner scanner) {
		while(system) {
			System.out.println("Qual ação de cargo deseja executar?");
			System.out.println("0 - Sair");
			System.out.println("1 - Busca funcionário por nome");
			System.out.println("2 - Busca funcionário por nome, salário e Data");
			System.out.println("3 - Busca funcionário data");
			System.out.println("4 - Pesquisa funcionário salário");
			
			int action = scanner.nextInt();
			
			switch (action) {
			case 1:
				buscaFuncionarioPorNome(scanner);
				break;
			case 2:
				buscaFuncionarioPorNomeSalarioData(scanner);
				break;
			case 3:
				buscaFuncionarioData(scanner);
				break;
			case 4:
				pesquisaFuncionarioSalario();
				break;
			default:
				system = false;
				break;
			}
		}
	}
	
	private void buscaFuncionarioData(Scanner scanner) {
		System.out.println("Qual é a data que deseja pesquisar?");
		String data = scanner.next();
		
		List<Funcionario> funcionarios = funcionarioRepository.findDataContratacaoMaior(LocalDate.parse(data, formatter));
		funcionarios.forEach(System.out::println);
	}

	private void buscaFuncionarioPorNomeSalarioData(Scanner scanner) {
		System.out.println("Qual é o nome que deseja pesquisar?");
		String nome = scanner.next();
		System.out.println("Qual é o salário que deseja pesquisar?");
		Double salario = scanner.nextDouble();
		System.out.println("Qual é a data que deseja pesquisar?");
		String data = scanner.next();
		
		List<Funcionario> funcionarios = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salario, LocalDate.parse(data, formatter));
		funcionarios.forEach(System.out::println);
	}

	private void buscaFuncionarioPorNome(Scanner scanner) {
		System.out.println("Qual é o nome que deseja pesquisar?");
		String nome = scanner.next();
		List<Funcionario> funcionarios = funcionarioRepository.findByNome(nome);
		funcionarios.forEach(System.out::println);
	}
	
	private void pesquisaFuncionarioSalario() {
		List<FuncionarioProjecao> list = funcionarioRepository.findFuncionarioSalario();
		list.forEach(f -> System.out.println("Funcionario id: " + f.getId() + 
				" | nome: " + f.getNome() +
				" | salário: " + f.getSalario()));
	}
}
