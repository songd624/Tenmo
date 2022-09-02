package com.techelevator.dao;



import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.DuplicateKeyException;
import java.util.List;
import static org.springframework.test.util.AssertionErrors.fail;

public class JdbcUserDaoTests extends BaseDaoTests {

    private JdbcUserDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserDao(jdbcTemplate);
    }

    @Test
    public void createNewUserTwice_ExpectFail() {
        Boolean userCreated = sut.create("TEST_USER", "test_password");
        Assert.assertTrue(userCreated);
        // thrown.expect(DuplicateKeyException.class);
        try {
            Boolean userCreated1 = sut.create("TEST_USER", "test_password");
            fail("User was created twice.");

        } catch (DuplicateKeyException e) {
        }
    }

    @Test
    public void listAllUsernames_ShouldListAllUsernames() {
        List<String> expected = List.of("bob", "user", "Terrance", "Daniel", "Mike");
        List<String> actual = sut.listAllUsernames();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByUsername_ShouldFindUsername(){
        String expected = "bob";
        User actual = sut.findByUsername("bob");
        Assert.assertEquals(expected, actual.getUsername());

    }
}