package seedu.address.logic.commands.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.calendar.CelebCalendar;

/**
 * Adds an appointment to a calendar.
 */
public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "addAppointment";
    public static final String COMMAND_ALIAS = "aa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to the celebrity calendar. "
            + "Parameters: "
            + PREFIX_NAME + "APPOINTMENT NAME "
            + PREFIX_START_TIME + "START TIME "
            + PREFIX_START_DATE + "START DATE "
            + PREFIX_LOCATION + "LOCATION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Oscars 2018 "
            + PREFIX_START_TIME + "18:00 "
            + PREFIX_START_DATE + "23/04/2018 "
            + PREFIX_LOCATION + "Hollywood";

    public static final String MESSAGE_SUCCESS = "Added appointment successfully";

    private final int calendarIndex;
    private final Appointment appt;

    /**
     * Creates an AddAppointmentCommand with the following parameters
     * @param appt The created appointment
     * @param calendarIndex The index of the calendar we want to add to
     */
    public AddAppointmentCommand(Appointment appt, int calendarIndex) {
        requireNonNull(appt);
        this.calendarIndex = calendarIndex; // just use first calendar for now since we only created one calendar
        this.appt = appt;
    }


    @Override
    public CommandResult execute() throws CommandException {
        CelebCalendar cal = (CelebCalendar) model.getCelebCalendars().get(calendarIndex);
        cal.addEntry(appt);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddAppointmentCommand
                && appt.equals(((AddAppointmentCommand) other).appt));
    }
}
