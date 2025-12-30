package dio.kanban.ui;

import dio.kanban.entity.Board;
import dio.kanban.entity.BoardColumn;
import dio.kanban.service.BoardMenuService;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
@Component
public class BoardMenu {

    @Setter
    private Board board;

    private final Scanner scanner = new Scanner(System.in);

    private BoardMenuService service;

    @Autowired
    public BoardMenu(BoardMenuService service) {
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
            String answer = scanner.nextLine();
            if (answer != null && answer.matches("-?\\d+")) {
                option = Integer.parseInt(answer);
            }
            else {
                option = -1;
            }

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
        System.out.print("Informe o título do novo card: ");
        String title = scanner.nextLine();

        System.out.print("Dê uma descrição para o card: ");
        String description = scanner.nextLine();

        service.insertCard(title, description, board.getId());
    }

    private void moveCard() {
        System.out.print("Informe o ID do card a ser movido para a próxima coluna: ");
        long id = Long.parseLong(scanner.nextLine());
        service.moveCard(id, board.getBoardColumns());
    }

    private void blockCard() {

    }

    private void unblockCard() {

    }

    private void cancelCard() {
        System.out.print("Informe o ID do card a ser cancelado: ");
        long id = Long.parseLong(scanner.nextLine());

        BoardColumn cancelColumn = board.getCancelColumn();
        service.cancelCard(id, cancelColumn);
    }

    private void showKanban() {
        service.showBoardDetails(board.getId());
    }

    protected void showColumn() {
        System.out.println("Selecione uma coluna do kanban pelo ID:");
        board.getBoardColumns().forEach(c -> System.out.printf("%s - %s [%s]\n", c.getId(), c.getName(), c.getKind()));
        long selectedId = Long.parseLong(scanner.nextLine());

        List<Long> ids = board.getBoardColumns().stream().map(BoardColumn::getId).toList();

        while  (!ids.contains(selectedId)) {
            System.out.print("Selecione um ID válido: ");
            board.getBoardColumns().forEach(c -> System.out.printf("%s - %s [%s]\n", c.getId(), c.getName(), c.getKind()));
            selectedId = Long.parseLong(scanner.nextLine());
        }
        service.showColumn(selectedId);
    }

    private void showCard() {
        System.out.print("Informe o ID do card a ser consultado: ");
        long id = Long.parseLong(scanner.nextLine());
        service.showCard(id);
    }
}
