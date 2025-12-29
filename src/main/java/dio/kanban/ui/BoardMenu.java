package dio.kanban.ui;

import dio.kanban.entity.Board;
import dio.kanban.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@AllArgsConstructor
@Component
public class BoardMenu {

    private Board board;

    private final Scanner scanner = new Scanner(System.in);

    private BoardService service;

    public BoardMenu(Board board) {
        this.board = board;
    }

    @Autowired
    public BoardMenu(BoardService service) {
        this.service = service;
    }

    public void execute() {
        System.out.println("\n-------------------------------------------------------------------------");
        System.out.printf("\nBem vindo(a) ao Kanban %s (ID: %s), selecione a operação desejada:\n", board.getName(), board.getId());

        int option = -1;

        while (option != 9) {
            System.out.println("1 - Criar um novo card");
            System.out.println("2 - Mover um card");
            System.out.println("3 - Bloquear um card");
            System.out.println("4 - Desbloquear um card");
            System.out.println("5 - Cancelar um card");
            System.out.println("6 - Visualizar o kanban");
            System.out.println("7 - Visualizar uma coluna");
            System.out.println("8 - Visualizar um card");
            System.out.println("9 - Retornar ao menu anterior");
            System.out.println("10 - Sair");
            option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> createCard();
                case 2 -> moveCard();
                case 3 -> blockCard();
                case 4 -> unblockCard();
                case 5 -> cancelCard();
                case 6 -> showKanban();
                case 7 -> showColumn();
                case 8 -> showCard();
                case 9 -> System.out.println("\nRetornando ao menu anterior...\n");
                case 10 -> System.exit(0);
                default -> System.out.println("Opção inválida, informe uma opção do menu\n");
            }
        }

    }

    private void createCard() {

    }

    private void moveCard() {
        
    }

    private void blockCard() {
        
    }

    private void unblockCard() {
        
    }

    private void cancelCard() {
        
    }

    private void showKanban() {
        service.showBoardDetails(board.getId());
    }

    private void showColumn() {
        
    }

    private void showCard() {
        
    }
}
