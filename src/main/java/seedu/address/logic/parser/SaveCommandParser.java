package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_AMOUNT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SAVING;

import seedu.address.commons.core.amount.Amount;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SaveCommand object.
 */
public class SaveCommandParser implements Parser<SaveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SaveCommand
     * and returns an SaveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected FORMAT
     */
    @Override
    public SaveCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SAVING);

        // new

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }

        String[] arguments = trimmedArgs.split(" ");

        if (arguments.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(arguments[0]);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
        }

        Amount amount;
        try {
            amount = new Amount(arguments[1]);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(String.format(MESSAGE_INVALID_AMOUNT, arguments[1]));
        }

        return new SaveCommand(index, amount);
    }

    /**
     * Returns true if the savings prefix does not contain empty {@code Optional} value in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isSavingsCommandPresent(ArgumentMultimap argumentMultiMap) {
        return argumentMultiMap.getValue(PREFIX_SAVING).isPresent();
    }
}
