package by.bsuir.bank.controller.command;

import by.bsuir.bank.controller.command.client.AddClientCommand;
import by.bsuir.bank.controller.command.client.DeleteClientCommand;
import by.bsuir.bank.controller.command.client.ShowClientsCommand;
import by.bsuir.bank.controller.command.client.UpdateClientCommand;
import by.bsuir.bank.exception.IllegalResourceException;
/**
 * Class is a part of GoF command pattern. Is a provider of existing command
 * objects. Act as strategy.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class CommandProvider {
    /**
     * Returns necessary command by string, that describe name of command.
     *
     * @param command string, that describe name of command.
     * @return Command target command object.
     * @throws IllegalResourceException if name of command is not defined.
     */
    public Command getCommand(String command) throws IllegalResourceException {
        switch (command) {
            case "add-client":
                return new AddClientCommand();

            case "clients-show-all":
                return new ShowClientsCommand();

            case "client-delete":
                return new DeleteClientCommand();

            case "client-update":
                return new UpdateClientCommand();

            default:
                throw new IllegalResourceException("Undefined command");
        }
    }
}
