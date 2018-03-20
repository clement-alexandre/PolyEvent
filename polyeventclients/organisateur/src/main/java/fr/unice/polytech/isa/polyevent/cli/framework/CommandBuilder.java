package fr.unice.polytech.isa.polyevent.cli.framework;

public interface CommandBuilder<T extends Command>
{
    String identifier();

    default boolean match(String keyword)
    {
        return identifier().equals(keyword);
    }

    String describe();
    public T build();
}
