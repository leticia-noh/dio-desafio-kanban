package dio.kanban.ui;

import dio.kanban.entity.Board;
import dio.kanban.entity.BoardColumn;
import dio.kanban.entity.BoardColumnKindEnum;
import dio.kanban.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);
    private BoardService service;

    @Autowired
    public MainMenu(BoardService service) {
        this.service = service;
    }

    public void execute() {
        System.out.println("Bem vindo(a) ao genrenciador do Kanban, escolha a opção desejada!");

        int option = -1;

        while (true) {
            System.out.println("1 - Criar um novo kanban");
            System.out.println("2 - Selecionar um kanban existente");
            System.out.println("3 - Excluir um kanban");
            System.out.println("4 - Sair");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> createBoard();
                case 2 -> selectBoard();
                case 3 -> deleteBoard();
                case 4 -> System.exit(0);
                default -> System.out.println("Opção inválida, informe uma opção do menu\n");
            }
        }
    }

    private void createBoard() {
        Board entity = new Board();

        System.out.print("Informe o nome do Kanban: ");
        entity.setName(scanner.nextLine());

        System.out.print("O modelo padrão de kanban possui 4 colunas\nSeu kanban terá um número personalizado de colunas (s/n)? ");
        String option = scanner.next();

        while (!option.equalsIgnoreCase("s") && !option.equalsIgnoreCase("n")) {
            System.out.print("Digite uma resposta válida: ");
            option = scanner.next();
        }

        int additionalColumns = 0;

        if  ("s".equalsIgnoreCase(option)) {
            System.out.print("Quantas colunas a mais você deseja adicionar? ");
            additionalColumns = scanner.nextInt();
        }

        List<BoardColumn> columns = new ArrayList<>();

        System.out.print("Informe o nome da coluna inicial do kanban: ");
        String initialColumnName= scanner.nextLine();
        BoardColumn initialColumn = createBoardColumn(initialColumnName, BoardColumnKindEnum.INITIAL, 0);
        columns.add(initialColumn);

        String pendingColumnName = null;
        if (additionalColumns > 0) {
            for (int i = 0; i <= additionalColumns; i++) {
                System.out.print("Informe o nome da coluna " + (i + 1) + " de tarefas pendentes do kanban: ");
                pendingColumnName = scanner.nextLine();
                BoardColumn pendingColumn = createBoardColumn(pendingColumnName, BoardColumnKindEnum.PENDING, i + 1);
                columns.add(pendingColumn);
            }
        }
        else {
            System.out.print("Informe o nome da coluna de tarefas pendentes do kanban: ");
            pendingColumnName = scanner.nextLine();
            BoardColumn pendingColumn = createBoardColumn(pendingColumnName, BoardColumnKindEnum.PENDING, 1);
            columns.add(pendingColumn);
        }

        System.out.print("Informe o nome da coluna final do kanban: ");
        String finalColumnName = scanner.nextLine();
        BoardColumn finalColumn = createBoardColumn(finalColumnName, BoardColumnKindEnum.FINAL, additionalColumns + 2);
        columns.add(finalColumn);

        System.out.print("Informe o nome da coluna de tarefas canceladas do kanban: ");
        String cancelColumnName = scanner.nextLine();
        BoardColumn cancelColumn = createBoardColumn(cancelColumnName, BoardColumnKindEnum.CANCEL, additionalColumns + 3);
        columns.add(cancelColumn);

        entity.setBoardColumns(columns);
        service.insert(entity);
        System.out.println("O kanban foi criado com sucesso!\n");
    }

    private void selectBoard() {
        System.out.print("Informe o ID do kanban a ser exibido: ");
        Long boardId = scanner.nextLong();

        Board board = service.findById(boardId);

        if (board == null) {
            System.out.println("Não foi encontrado um kanban com esse ID!\n");
        }
        else {
            BoardMenu boardMenu = new BoardMenu(board);
            boardMenu.execute();
        }

    }

    private void deleteBoard() {
        System.out.print("Informe o ID do kanban a ser excluído: ");
        Long boardId = scanner.nextLong();

        while (!service.exists(boardId)) {
            System.out.print("Digite um ID válido: ");
            boardId = scanner.nextLong();
        }

        if (service.delete(boardId)) {
            System.out.println("O kanban foi excluído com sucesso!\n");
        }
        else {
            System.out.println("Não foi possível deletar o board!\n");
        }
    }

    private BoardColumn createBoardColumn(String name, BoardColumnKindEnum kind, int order) {
        var boardColumn = new BoardColumn();
        boardColumn.setName(name);
        boardColumn.setKind(kind);
        boardColumn.setOrder(order);

        return boardColumn;
    }
}
