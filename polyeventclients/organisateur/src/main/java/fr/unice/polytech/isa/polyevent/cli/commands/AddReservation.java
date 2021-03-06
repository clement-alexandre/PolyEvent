package fr.unice.polytech.isa.polyevent.cli.commands;

import fr.unice.polytech.isa.polyevent.cli.framework.Command;
import fr.unice.polytech.isa.polyevent.cli.framework.CommandBuilder;
import fr.unice.polytech.isa.polyevent.cli.framework.Context;
import fr.unice.polytech.isa.polyevent.stubs.DemandeReservationSalle;
import fr.unice.polytech.isa.polyevent.stubs.TypeSalle;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AddReservation implements Command
{
    private static final Identifier IDENTIFIER = Identifier.ADD_RESERVATION;
    private final List<DemandeReservationSalle> demandeReservations;
    private TypeSalle typeSalle;
    private XMLGregorianCalendar dateDebut;
    private XMLGregorianCalendar dateFin;

    private AddReservation(List<DemandeReservationSalle> demandeReservations)
    {
        this.demandeReservations = demandeReservations;
    }

    private static String availableRoomType()
    {
        return Arrays.stream(TypeSalle.values()).map(Enum::name).collect(Collectors.joining(", "));
    }

    @Override
    public void load(List<String> args) throws Exception
    {
        if (args.size() < 3)
        {
            String message = String.format("%s expects 3 arguments: %s TYPE_ROOM START_DATE END_DATE", IDENTIFIER, IDENTIFIER);
            throw new IllegalArgumentException(message);
        }
        String value = args.get(0);
        try
        {
            typeSalle = TypeSalle.valueOf(value);
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException(String.format("The room type \"%s\" does not exist.%n" +
                            "Choose one type from the following list: %s.%nType help %s for more details.",
                    value, availableRoomType(), IDENTIFIER));
        }
        try
        {
            DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
            dateDebut = datatypeFactory.newXMLGregorianCalendar(args.get(1));
            dateFin = datatypeFactory.newXMLGregorianCalendar(args.get(2));
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException(String.format("Illegal date format: %s", e.getMessage()));
        }
    }

    @Override
    public void execute()
    {
        DemandeReservationSalle demandeReservationSalle = new DemandeReservationSalle();
        demandeReservationSalle.setTypeSalle(typeSalle);
        demandeReservationSalle.setDateDebut(dateDebut);
        demandeReservationSalle.setDateFin(dateFin);
        demandeReservations.add(demandeReservationSalle);
    }

    public static class Builder implements CommandBuilder<AddReservation>
    {
        private final List<DemandeReservationSalle> demandeReservations;

        Builder(List<DemandeReservationSalle> demandeReservations)
        {
            this.demandeReservations = demandeReservations;
        }

        @Override
        public String identifier()
        {
            return IDENTIFIER.keyword;
        }

        @Override
        public String describe()
        {
            return IDENTIFIER.description;
        }

        @Override
        public String help()
        {
            return String.format("Usage: %s TYPE_ROOM START_DATE END_DATE%n" +
                            "Select a type room from the following list: %s%n" +
                            "Example: %s AMPHI 2018-12-03T16:00:00 2018-12-03T18:00:00",
                    IDENTIFIER, availableRoomType(), IDENTIFIER);
        }

        @Override
        public AddReservation build(Context context)
        {
            return new AddReservation(demandeReservations);
        }
    }
}
