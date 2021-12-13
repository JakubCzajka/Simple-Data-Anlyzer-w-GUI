package pl.polsl.model.basicmodel;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class containing unit tests for Field class
 *
 * @author Jakub Czajka
 * @version 1.1
 */
public class FieldTest {
    /**
     * Tested instance of field, that will be filled up with a couple of numbers before each test.
     */
    Field<Double> field;
    /**
     * Tested instance of field, that will be empty.
     */
    Field<Double> emptyField;
    /**
     * Tested instance of field, that receives nulls in constructor.
     */
    Field<Double> improperField;

    /**
     * Method that fills field with the following values: 1.0,1.5,3.5,4.0, emptyField only with name, and improper fields with two nulls.
     */
    @BeforeEach
    void setUp() {
        Vector<Double> values = new Vector<>();
        values.add(1.0);
        values.add(1.5);
        values.add(3.5);
        values.add(4.0);
        field = new Field<>("Tested Field", values);
        emptyField = new Field<>("Empty field");
        improperField = new Field<>(null, null);
    }

    /**
     * Test of getSize method of Field class.
     */
    @Test
    public void testGettingSize() {
        assertEquals(field.getSize(), 4, "Field.getSize doesn't work for non-empty field.");

        assertEquals(emptyField.getSize(), 0, "Field.getSize doesn't work for empty field.");

        try {
            assertEquals(improperField.getSize(), 0, "Field.getSize doesn't work for improper field.");
        } catch (Exception e) {
            fail("Field.getSize doesn't work for improper field.");
        }
    }

    /**
     * Test of at method of Field class.
     */
    @Test
    public void testGettingSingleValue() {
        assertEquals(field.at(0), 1.0, 0.01, "Incorrect value at 0th position.");
        assertEquals(field.at(1), 1.5, 0.01, "Incorrect value at 1st position.");
        assertEquals(field.at(3), 4.0, 0.01, "Incorrect value at 3rd position.");
        assertNull(field.at(-1), "Method didn't return null after receiving negative index.");
        assertNull(field.at(4), "Method didn't return null after receiving too big index.");
        assertNull(emptyField.at(0), "Method didn't return null for empty field.");
        assertNull(improperField.at(0), "Method didn't return null for improper field.");
    }

    /**
     * Test of addValue method of Field class.
     */
    @Test
    public void testAddingValue() {
        field.addValue(5.0);
        assertEquals(field.at(4), 5.0, 0.01, "Incorrect value at new position.");
        assertEquals(field.getSize(), 5, "Size didn't change after adding a proper value.");

        field.addValue(null);
        assertEquals(field.getSize(), 5, "Size changed after adding null value.");

        emptyField.addValue(1.0);
        assertEquals(emptyField.at(0), 1.0, 0.01, "Incorrect value at new position - empty field.");
        assertEquals(emptyField.getSize(), 1, "Size didn't change after adding a proper value - empty field.");

        improperField.addValue(1.0);
        assertEquals(improperField.at(0), 1.0, 0.01, "Incorrect value at new position - improper field.");
        assertEquals(improperField.getSize(), 1, "Size didn't change after adding a proper value - improper field.");

    }

    /**
     * Test of copyValues method of Field class.
     */
    @Test
    public void testCopyingValues() {
        Vector<Double> values = new Vector<>();
        values.add(1.0);
        values.add(1.5);
        values.add(3.5);
        values.add(4.0);
        Field<Double> fieldToBeCopied = new Field<>("Tested Field", values);

        Vector<Double> copiedValues = fieldToBeCopied.copyValues();
        assertNotSame(values, copiedValues, "No copy was made, copyValues references to the same object as original values.");
        assertEquals(copiedValues.size(),fieldToBeCopied.getSize(), "Copied values have different size than original field.");

        for (int i = 0; i < copiedValues.size(); i++) {
            assertEquals(copiedValues.elementAt(i), fieldToBeCopied.at(i),0.01,  "Created copy has different contents than field.");
        }


    }

}
