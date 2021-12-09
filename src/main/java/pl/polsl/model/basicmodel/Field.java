package pl.polsl.model.basicmodel;

import java.util.Vector;

/**
 * Class that represents single field of dataset.
 *
 * @param <T> type held by class
 *
 * @author Jakub Czajka
 * @version 1.8
 */
class Field<T> {
    /**
     * Name of the field.
     */
    private String name;
    /**
     * Vector containing all elements of the field.
     */
    private Vector<T> values;

    /**
     * Simple class constructor, that creates empty field with given name.
     *
     * @param name Name of created field.
     */
    public Field(String name) {
        if(name != null)
            this.name = name;
        else
            this.name = "null";
        this.values = new Vector<T>();
    }

    /**
     * Simple class constructor, that creates filled field with given name.
     *
     * @param name Name of created field.
     * @param values Reference to Vector, that will be stored internally.
     */
    public Field(String name, Vector<T> values) {
        this(name);
        if(values != null)
            this.values = values;
        else
            this.values = new Vector<T>();
    }

    /**
     * Getter of the field's name.
     *
     * @return name of the field.
     */
    public String getName() {
        return name;
    }

    /**
     * Method that creates copy of field values.
     *
     * @return Vector containing copies of all field elements
     */
    public Vector<T> copyValues() {
        Vector<T> copy = new Vector<T>();
        for (T value : values)
            copy.add(value);
        return copy;
    }

    /**
     * Method adding new element to field.
     *
     * @param newValue Value that will be added to a field. No action taken if null
     */
    void addValue(T newValue) {
        if(newValue != null)
            values.add(newValue);
    }

    /**
     * Method that returns number of field elements.
     *
     * @return number of field elements
     */
    int getSize() {
        return values.size();
    }

    /**
     * Method that returns Element at given position.
     *
     * @param index position of the requested element.
     * @return element at given position, or null if there's no such position.
     */
    T at(int index) {
        if (index < 0 || index >= values.size())
            return null;
        return values.elementAt(index);
    }
}
