package jug.istanbul;

import jug.istanbul.legacy.Author;
import jug.istanbul.legacy.AuthorData;
import jug.istanbul.legacy.NameCalculation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorTest {
    
    private Author createAuthorObject(String firstName, String lastName, int books) {
        return new Author(firstName, lastName, books);
    }
    
    @Test
    void testFullName() {
        var author = createAuthorObject("Isaac", "Asimov", 500);
        assertEquals("Isaac Asimov", author.fullName());
    }
    
    @Test
    void testDataOrientedFullName() {
        var data = new AuthorData("Isaac", "Asimov", 500);
        assertEquals("Isaac Asimov", NameCalculation.fullName(data));
    }
}
