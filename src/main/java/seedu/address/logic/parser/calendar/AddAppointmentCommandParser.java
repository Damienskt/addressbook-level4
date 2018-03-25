package seedu.address.logic.parser.calendar;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CELEBRITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.calendar.AddAppointmentCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import sun.net.www.ParseUtil;

/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommand
     * and returns an AddAppointmentCommand object for execution
     * @throws ParseException if the user input does not comform to the expected format
     */
    @Override
    public AddAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_START_TIME,
                PREFIX_START_DATE,  PREFIX_LOCATION, PREFIX_END_TIME, PREFIX_END_DATE, PREFIX_CELEBRITY);

        if (!arePrefixesPresent(argMultiMap, PREFIX_NAME)
                || !argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        try {
            String appointmentName = ParserUtil.parseGeneralName(argMultiMap.getValue(PREFIX_NAME)).get();
            Optional<LocalTime> startTimeInput = ParserUtil.parseTime(argMultiMap.getValue(PREFIX_START_TIME));
            Optional<LocalDate> startDateInput = ParserUtil.parseDate(argMultiMap.getValue(PREFIX_START_DATE));
            Optional<LocalTime> endTimeInput = ParserUtil.parseTime(argMultiMap.getValue(PREFIX_END_TIME));
            Optional<LocalDate> endDateInput = ParserUtil.parseDate(argMultiMap.getValue(PREFIX_END_DATE));
            Optional<String> locationInput = ParserUtil.parseGeneralName(argMultiMap.getValue(PREFIX_LOCATION));
            Set<Index> celebrityIndices = ParserUtil.parseIndices(argMultiMap.getAllValues(PREFIX_CELEBRITY));

            String location = null;
            LocalTime startTime = LocalTime.now();
            LocalDate startDate = LocalDate.now();
            LocalTime endTime = LocalTime.now();
            LocalDate endDate = LocalDate.now();

            if (startTimeInput.isPresent()) {
                startTime = startTimeInput.get();
                endTime = startTimeInput.get();
            }
            if (endTimeInput.isPresent()) {
                endTime = endTimeInput.get();
            }
            if (startDateInput.isPresent()) {
                startDate = startDateInput.get();
                endDate = startDateInput.get();
            }
            if (endDateInput.isPresent()) {
                endDate = endDateInput.get();
            }
            if (locationInput.isPresent()) {
                location = locationInput.get();
            }

            Appointment appt = new Appointment(appointmentName, startTime, startDate, location, endTime, endDate);
            return new AddAppointmentCommand(appt, celebrityIndices);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
